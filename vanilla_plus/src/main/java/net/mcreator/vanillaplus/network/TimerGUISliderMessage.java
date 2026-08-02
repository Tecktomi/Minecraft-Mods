package net.mcreator.vanillaplus.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;

import net.mcreator.vanillaplus.procedures.TimerGUISliderProcedure;
import net.mcreator.vanillaplus.VanillaPlusMod;

@EventBusSubscriber
public record TimerGUISliderMessage(int sliderID, int x, int y, int z, double value) implements CustomPacketPayload {
	public static final Type<TimerGUISliderMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(VanillaPlusMod.MODID, "timer_gui_sliders"));
	public static final StreamCodec<RegistryFriendlyByteBuf, TimerGUISliderMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, TimerGUISliderMessage message) -> {
		buffer.writeInt(message.sliderID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		buffer.writeDouble(message.value);
	}, (RegistryFriendlyByteBuf buffer) -> new TimerGUISliderMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readDouble()));

	@Override
	public Type<TimerGUISliderMessage> type() {
		return TYPE;
	}

	public static void handleData(final TimerGUISliderMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleSliderAction(context.player(), message.sliderID, message.x, message.y, message.z, message.value)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleSliderAction(Player entity, int sliderID, int x, int y, int z, double value) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (sliderID == 0) {

			TimerGUISliderProcedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		VanillaPlusMod.addNetworkMessage(TimerGUISliderMessage.TYPE, TimerGUISliderMessage.STREAM_CODEC, TimerGUISliderMessage::handleData);
	}
}