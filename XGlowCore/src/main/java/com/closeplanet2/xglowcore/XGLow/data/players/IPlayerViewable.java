package com.closeplanet2.xglowcore.XGLow.data.players;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public interface IPlayerViewable {
    Set<Player> getViewers();

    default boolean hasViewers() {
        return !this.getViewers().isEmpty();
    }

    default boolean sees(Player... possibleViewers) {
        return Arrays.stream(possibleViewers).allMatch(this.getViewers()::contains);
    }

    default void display(Iterable<? extends Player> viewers) {
        viewers.forEach(this::display);
    }

    void display(Player... viewers);

    default void display(UUID... uuids) {
        for (UUID uuid : uuids) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                continue;
            }

            this.display(player);
        }
    }

    default void hideFrom(Iterable<? extends Player> viewers) {
        viewers.forEach(this::hideFrom);
    }

    void hideFrom(Player... viewers);

    default void hideFrom(UUID... uuids) {
        for (UUID uuid : uuids) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                continue;
            }

            this.hideFrom(player);
        }
    }

    default void hideFromEveryone() {
        this.getViewers().forEach((viewer) -> {
            if (viewer == null || !viewer.isOnline()) {
                return;
            }

            this.hideFrom(viewer);
        });
    }
}
