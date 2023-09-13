package com.closeplanet2.xglowcore.XGLow.listeners;

import com.closeplanet2.pandaspigotcore.EVENTS.PandaEvent;
import com.closeplanet2.xglowcore.XGLow.data.glow.manager.GlowsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@PandaEvent
public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        GlowsManager.getInstance().removeGlowFrom(player);
        GlowsManager.getInstance().removeViewer(player);
    }
}