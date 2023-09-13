package com.closeplanet2.xglowcore.XGLow.data.glow.processor;

import com.closeplanet2.xglowcore.XGLow.packets.AbstractPacket;
import com.closeplanet2.xglowcore.XGLow.packets.AbstractWrapperPlayServerScoreboardTeam;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Map;

public interface IGlowProcessor {
    AbstractPacket createTeamPacket(Map<String, Boolean> holders, ChatColor color, String teamName,
                                    AbstractWrapperPlayServerScoreboardTeam.Mode mode);

    List<AbstractPacket> createGlowPackets(Map<String, Boolean> holders, boolean glow);

    AbstractPacket createGlowPacket(Entity entity, boolean enableGlow);

    WrappedDataWatcher createDataWatcher(Entity entity, boolean enableGlow);
}