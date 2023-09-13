package com.closeplanet2.xglowcore.XGLow.packets.versions;

import com.closeplanet2.xglowcore.XGLow.packets.AbstractWrapperPlayServerScoreboardTeam;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;

import java.util.Optional;

public class WrapperPlayServerScoreboardTeam_v17_19 extends AbstractWrapperPlayServerScoreboardTeam {
    @Override
    public Optional<WrappedChatComponent> getDisplayName() {
        return this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().read(0));
    }

    @Override
    public void setDisplayName(WrappedChatComponent value) {
        this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().write(0, value));
    }

    @Override
    public Optional<WrappedChatComponent> getPrefix() {
        return this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().read(1));
    }

    @Override
    public void setPrefix(WrappedChatComponent value) {
        this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().write(1, value));
    }

    @Override
    public Optional<WrappedChatComponent> getSuffix() {
        return this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().read(2));
    }

    @Override
    public void setSuffix(WrappedChatComponent value) {
        this.handle.getOptionalStructures().read(0)
                .map((structure) -> structure.getChatComponents().write(2, value));
    }

    @Override
    public NameTagVisibility getNameTagVisibility() {
        return this.handle.getOptionalStructures().read(0)
                .flatMap((structure) -> NameTagVisibility.getByIdentifier(
                        structure.getStrings().read(0)))
                .orElse(NameTagVisibility.NEVER);
    }

    @Override
    public void setNameTagVisibility(NameTagVisibility value) {
        this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getStrings().write(0, value.getIdentifier()));
    }

    @Override
    public Optional<ChatColor> getColor() {
        return this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getEnumModifier(ChatColor.class,
                        MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                        .read(0));
    }

    @Override
    public void setColor(ChatColor value) {
        this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getEnumModifier(ChatColor.class,
                        MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                        .write(0, value));
    }

    @Override
    public CollisionRule getCollisionRule() {
        return this.handle.getOptionalStructures().read(0)
                .flatMap((structure) -> CollisionRule.getByIdentifier(
                        structure.getStrings().read(1)))
                .orElse(CollisionRule.NEVER);
    }

    @Override
    public void setCollisionRule(CollisionRule value) {
        this.handle.getOptionalStructures().read(0).map((structure) ->
                structure.getStrings().write(1, value.getIdentifier()));
    }
}
