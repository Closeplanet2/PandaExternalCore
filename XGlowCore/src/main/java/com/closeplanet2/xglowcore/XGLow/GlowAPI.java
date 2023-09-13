package com.closeplanet2.xglowcore.XGLow;

import com.closeplanet2.xglowcore.XGLow.data.glow.manager.GlowsManager;
import com.closeplanet2.xglowcore.XGLow.data.glow.processor.GlowProcessor;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GlowAPI {
    private final Plugin plugin;

    public GlowAPI(Plugin plugin) {
        this.plugin = plugin;

        PluginManager pluginManager = Bukkit.getPluginManager();

        if (pluginManager.getPlugin("ProtocolLib") == null) {
            plugin.getLogger().warning("[XGlow] No access to ProtocolLib! Is it installed?");
            plugin.getLogger().warning("[XGlow] Plugin has been disabled!");

            pluginManager.disablePlugin(plugin);
            return;
        }

        this.registerPacketListener();
    }

    private void registerPacketListener() {
        PacketAdapter adapter = new PacketAdapter(this.plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Entity entity = packet.getEntityModifier(event).read(0);

                GlowsManager.getInstance().getGlowByEntity(entity).ifPresent((glow) -> {
                    final List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();
                    GlowProcessor.getInstance().createDataWatcher(entity, glow.sees(event.getPlayer()))
                            .getWatchableObjects().stream().filter(Objects::nonNull).forEach(entry -> {
                        final WrappedDataWatcher.WrappedDataWatcherObject dataWatcherObject = entry.getWatcherObject();
                        wrappedDataValueList.add(new WrappedDataValue(dataWatcherObject.getIndex(), dataWatcherObject.getSerializer(), entry.getRawValue()));
                    });
                    packet.getDataValueCollectionModifier().write(0, wrappedDataValueList);

                    event.setPacket(packet);
                });
            }
        };

        ProtocolLibrary.getProtocolManager().addPacketListener(adapter);
    }
}
