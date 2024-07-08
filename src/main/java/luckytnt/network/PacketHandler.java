package luckytnt.network;

import luckytnt.LuckyTNTMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.Channel.VersionTest;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler {

	public static final int PROTOCOL_VERSION = 1;
	
	public static final SimpleChannel CHANNEL = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(LuckyTNTMod.MODID, "main")).networkProtocolVersion(PROTOCOL_VERSION).acceptedVersions(VersionTest.exact(PROTOCOL_VERSION)).simpleChannel();
	
	private PacketHandler() {	
	}
	
	public static void register() {
		int index = 0;
		CHANNEL.messageBuilder(ClientboundStringNBTPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(ClientboundStringNBTPacket::encode).decoder(ClientboundStringNBTPacket::new).consumerNetworkThread(ClientboundStringNBTPacket::handle).add();
		CHANNEL.messageBuilder(ClientboundIntNBTPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(ClientboundIntNBTPacket::encode).decoder(ClientboundIntNBTPacket::new).consumerNetworkThread(ClientboundIntNBTPacket::handle).add();
		CHANNEL.messageBuilder(ClientboundLevelVariablesPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(ClientboundLevelVariablesPacket::encode).decoder(ClientboundLevelVariablesPacket::new).consumerNetworkThread(ClientboundLevelVariablesPacket::handle).add();
		CHANNEL.messageBuilder(ClientboundToxicCloudPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(ClientboundToxicCloudPacket::encode).decoder(ClientboundToxicCloudPacket::new).consumerNetworkThread(ClientboundToxicCloudPacket::handle).add();
		CHANNEL.messageBuilder(ClientboundFreezeNBTPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(ClientboundFreezeNBTPacket::encode).decoder(ClientboundFreezeNBTPacket::new).consumerNetworkThread(ClientboundFreezeNBTPacket::handle).add();
		CHANNEL.messageBuilder(ClientboundBooleanNBTPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(ClientboundBooleanNBTPacket::encode).decoder(ClientboundBooleanNBTPacket::new).consumerNetworkThread(ClientboundBooleanNBTPacket::handle).add();
		CHANNEL.messageBuilder(ClientboundHydrogenBombPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(ClientboundHydrogenBombPacket::encode).decoder(ClientboundHydrogenBombPacket::new).consumerNetworkThread(ClientboundHydrogenBombPacket::handle).add();
	}
}
