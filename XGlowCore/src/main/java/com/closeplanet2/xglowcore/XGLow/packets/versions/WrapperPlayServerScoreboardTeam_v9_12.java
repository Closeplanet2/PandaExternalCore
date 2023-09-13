package com.closeplanet2.xglowcore.XGLow.packets.versions;

import com.closeplanet2.xglowcore.XGLow.packets.AbstractWrapperPlayServerScoreboardTeam;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;

import java.util.Optional;

public class WrapperPlayServerScoreboardTeam_v9_12
        extends AbstractWrapperPlayServerScoreboardTeam {
    @Override
    public Mode getMode() {
        return Mode.values()[this.handle.getIntegers().read(1)];
    }

    @Override
    public void setMode(Mode value) {
        this.handle.getIntegers().write(1, value.ordinal());
    }

    @Override
    public Optional<WrappedChatComponent> getDisplayName() {
        return Optional.of(WrappedChatComponent.fromJson(this.handle.getStrings().read(1)));
    }

    @Override
    public void setDisplayName(WrappedChatComponent value) {
        this.handle.getStrings().write(0, value.getJson());
    }

    @Override
    public Optional<WrappedChatComponent> getPrefix() {
        return Optional.of(WrappedChatComponent.fromJson(this.handle.getStrings().read(2)));
    }

    @Override
    public void setPrefix(WrappedChatComponent value) {
        this.handle.getStrings().write(2, value.getJson());
    }

    @Override
    public Optional<WrappedChatComponent> getSuffix() {
        return Optional.of(WrappedChatComponent.fromJson(this.handle.getStrings().read(3)));
    }

    @Override
    public void setSuffix(WrappedChatComponent value) {
        this.handle.getStrings().write(3, value.getJson());
    }

    @Override
    public NameTagVisibility getNameTagVisibility() {
        return NameTagVisibility.getByIdentifier(this.handle.getStrings().read(4))
                .orElse(NameTagVisibility.NEVER);
    }

    @Override
    public void setNameTagVisibility(AbstractWrapperPlayServerScoreboardTeam.NameTagVisibility nameTagVisibility) {
        this.handle.getStrings().write(4, nameTagVisibility.getIdentifier());
    }

    @Override
    public Optional<ChatColor> getColor() {
        int value = this.handle.getIntegers().read(0);

        if (value == -1) {
            return Optional.empty();
        }

        return Optional.of(ChatColor.values()[value]);
    }

    @Override
    public void setColor(ChatColor color) {
        int value = color.ordinal() > 15 ? 0 : color.ordinal();

        this.setPrefix(WrappedChatComponent.fromText(ChatColor.values()[value] + ""));
        this.handle.getIntegers().write(0, value);
    }

    @Override
    public CollisionRule getCollisionRule() {
        return CollisionRule.getByIdentifier(this.handle.getStrings().read(5))
                .orElse(CollisionRule.NEVER);
    }

    @Override
    public void setCollisionRule(CollisionRule value) {
        this.handle.getStrings().write(5, value.getIdentifier());
    }
}
