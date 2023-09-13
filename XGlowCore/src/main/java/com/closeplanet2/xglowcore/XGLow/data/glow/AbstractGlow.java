package com.closeplanet2.xglowcore.XGLow.data.glow;

import com.closeplanet2.xglowcore.XGLow.data.glow.manager.GlowsManager;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class AbstractGlow implements IGlow {
    // <player name (false) or entity uuid (true)>
    Map<String, Boolean> holders = new ConcurrentHashMap<> ();

    Set<Player> viewers = ConcurrentHashMap.newKeySet();

    @Getter
    @NonFinal ChatColor color;

    @Getter
    String name;

    public AbstractGlow(ChatColor color, String name) {
        this.color = color;
        this.name = name;

        GlowsManager.getInstance().addGlow(this);
    }

    @Override
    public void setColor(ChatColor color) {
        this.color = color;
        this.broadcast();
    }

    @Override
    public Set<Entity> getHolders() {
        return Collections.unmodifiableSet(getHoldersStream(this.holders)
                .collect(Collectors.toSet()));
    }

    @Override
    public Set<Player> getViewers() {
        return Collections.unmodifiableSet(this.viewers);
    }

    public static Stream<Entity> getHoldersStream(Map<String, Boolean> holders) {
        return holders.entrySet().stream().map((entry) -> {
            String key = entry.getKey();

            return entry.getValue() ? Bukkit.getEntity(UUID.fromString(key)) :
                    Bukkit.getPlayer(key);
        }).filter(Objects::nonNull);
    }
}
