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

import net.mcreator.vanillaplus.procedures.SecuenceGUIPush1Procedure;
import net.mcreator.vanillaplus.procedures.SecuenceGUIPush0Procedure;
import net.mcreator.vanillaplus.procedures.SecuenceGUIPopProcedure;
import net.mcreator.vanillaplus.VanillaPlusMod;

@EventBusSubscriber
public record SecuenceGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<SecuenceGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(VanillaPlusMod.MODID, "secuence_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SecuenceGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SecuenceGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new SecuenceGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<SecuenceGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final SecuenceGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (buttonID == 0) {

			SecuenceGUIPush0Procedure.execute(world, x, y, z);
		}
		if (buttonID == 1) {

			SecuenceGUIPush1Procedure.execute(world, x, y, z);
		}
		if (buttonID == 2) {

			SecuenceGUIPopProcedure.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		VanillaPlusMod.addNetworkMessage(SecuenceGUIButtonMessage.TYPE, SecuenceGUIButtonMessage.STREAM_CODEC, SecuenceGUIButtonMessage::handleData);
	}
}