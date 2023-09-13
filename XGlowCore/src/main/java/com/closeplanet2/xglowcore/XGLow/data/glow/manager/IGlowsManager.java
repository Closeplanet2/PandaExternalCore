package com.closeplanet2.xglowcore.XGLow.data.glow.manager;

import com.closeplanet2.xglowcore.XGLow.data.glow.IGlow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public interface IGlowsManager {
    Set<IGlow> getGlows();

    default Optional<IGlow> getGlowByEntity(Entity entity) {
        return this.getGlows()
                .stream()
                .filter((glow) -> glow.hasHolder(entity))
                .findFirst();
    }

    default Optional<IGlow> getGlowByName(String name) {
        return this.getGlows()
                .stream()
                .filter((glow) -> glow.getName().equals(name))
                .findFirst();
    }

    void addGlow(IGlow glow);

    void removeGlow(IGlow glow);

    default void removeGlowFrom(Entity entity) {
        this.getGlows().forEach((glow) -> glow.removeHolders(entity));
    }

    default void removeViewer(Player viewer) {
        this.getGlows().forEach((glow) -> glow.hideFrom(viewer));
    }

    default void clear() {
        this.getGlows().forEach(this::removeGlow);
    }
}
