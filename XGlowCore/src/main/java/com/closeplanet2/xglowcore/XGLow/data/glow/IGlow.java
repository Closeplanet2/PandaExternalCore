package com.closeplanet2.xglowcore.XGLow.data.glow;

import com.closeplanet2.xglowcore.XGLow.data.players.IPlayerViewable;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;

public interface IGlow extends IPlayerViewable {
    Set<Entity> getHolders();

    ChatColor getColor();

    String getName();

    default boolean hasHolders() {
        return !this.getHolders().isEmpty();
    }

    default boolean hasHolder(Entity entity) {
        return entity != null && this.getHolders().contains(entity);
    }

    void setColor(ChatColor color);

    void addHolders(Entity... entities);

    void removeHolders(Entity... entities);

    void render(Player... players);

    default void broadcast() {
        this.display(this.getViewers());
    }

    void destroy();
}
