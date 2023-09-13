package com.closeplanet2.xglowcore.XGLow.data.glow;

import com.closeplanet2.xglowcore.XGLow.data.glow.manager.GlowsManager;
import com.closeplanet2.xglowcore.XGLow.data.glow.processor.GlowProcessor;
import com.closeplanet2.xglowcore.XGLow.packets.AbstractPacket;
import com.closeplanet2.xglowcore.XGLow.packets.AbstractWrapperPlayServerScoreboardTeam;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Glow extends AbstractGlow {
    public Glow(ChatColor color, String name) {
        super(color, name);
    }

    @Override
    public void addHolders(Entity... entities) {
        this.processHolder(true, entities);
    }

    @Override
    public void removeHolders(Entity... entities) {
        this.processHolder(false, entities);
    }

    private void processHolder(boolean add, Entity... entities) {
        List<AbstractPacket> packets = new ArrayList<> ();

        for (Entity entity : entities) {
            boolean player = entity instanceof Player;
            boolean changed;

            String value = player ? entity.getName() : entity.getUniqueId().toString();

            if (add && !this.holders.containsKey(value)) {
                changed = this.holders.put(value, !player) == null;
            } else {
                changed = this.holders.remove(value) != null;
            }

            if (!changed) {
                continue;
            }

            packets.add(GlowProcessor.getInstance().createGlowPacket(entity, add));
        }

        if (packets.isEmpty()) {
            return;
        }

        packets.add(GlowProcessor.getInstance()
                .createTeamPacket(this.holders, this.color, this.name,
                        add ? AbstractWrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED :
                                AbstractWrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED));

        packets.forEach((packet) -> packet.sendPacket(this.viewers));
    }

    @Override
    public void display(Player... viewers) {
        for (Player viewer : viewers) {
            if (this.viewers.contains(viewer)) {
                continue;
            }

            this.render(viewer);
        }

        if (this.holders.isEmpty()) {
            return;
        }

        List<AbstractPacket> packets = GlowProcessor.getInstance()
                .createGlowPackets(this.holders, true);

        packets.add(GlowProcessor.getInstance()
                .createTeamPacket(this.holders, this.color, this.name,
                        AbstractWrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED));

        packets.forEach((packet) -> packet.sendPacket(viewers));
    }

    @Override
    public void hideFrom(Player... viewers) {
        this.processView(false, viewers);
    }

    @Override
    public void render(Player... viewers) {
        this.processView(true, viewers);
    }

    private void processView(boolean display, Player... viewers) {
        for (Player viewer : viewers) {
            if (display == this.viewers.contains(viewer)) {
                continue;
            }

            if (display) {
                this.viewers.add(viewer);
                continue;
            }

            this.viewers.remove(viewer);
        }

        List<AbstractPacket> packets = this.holders.isEmpty() ? new ArrayList<> () :
                GlowProcessor.getInstance().createGlowPackets(this.holders, display);

        packets.add(GlowProcessor.getInstance()
                .createTeamPacket(this.holders, this.color, this.name,
                        display ? AbstractWrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED :
                                AbstractWrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED));

        packets.forEach((packet) -> packet.sendPacket(viewers));
    }

    @Override
    public void destroy() {
        this.hideFromEveryone();
        this.getHolders().forEach(this::removeHolders);

        GlowsManager.getInstance().removeGlow(this);
    }

    public static GlowBuilder builder() {
        return new GlowBuilder();
    }

    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class GlowBuilder {
        ChatColor color;
        String name;

        public GlowBuilder color(ChatColor color) {
            this.color = color;
            return this;
        }

        public GlowBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Glow build() {
            return new Glow(this.color, this.name);
        }
    }
}
