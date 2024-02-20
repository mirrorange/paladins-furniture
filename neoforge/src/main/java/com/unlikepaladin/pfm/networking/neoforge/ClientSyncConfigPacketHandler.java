package com.unlikepaladin.pfm.networking.neoforge;

import com.unlikepaladin.pfm.PaladinFurnitureMod;
import com.unlikepaladin.pfm.client.screens.PFMConfigScreen;
import com.unlikepaladin.pfm.config.option.Side;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientSyncConfigPacketHandler {
    public static void handlePacket(SyncConfigPacket msg, PlayPayloadContext ctx) {
        PFMConfigScreen.isOnServer = true;
        msg.configOptions.forEach((title, configOption) -> {
            if (configOption.getSide() == Side.SERVER) {
                LeaveEventHandlerNeoForge.originalConfigValues.put(title, PaladinFurnitureMod.getPFMConfig().options.get(title).getValue());
                PaladinFurnitureMod.getPFMConfig().options.get(title).setValue(configOption.getValue());
            }
        });
    }
}
