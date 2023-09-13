package com.closeplanet2.xglowcore.XGLow.listeners;

import com.closeplanet2.pandaspigotcore.EVENTS.PandaEvent;
import com.closeplanet2.xglowcore.XGLow.data.glow.manager.GlowsManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@PandaEvent
public class EntityDeathListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            return;
        }

        GlowsManager.getInstance().removeGlowFrom(entity);
    }
}
