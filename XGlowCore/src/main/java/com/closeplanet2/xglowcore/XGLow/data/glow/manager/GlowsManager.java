package com.closeplanet2.xglowcore.XGLow.data.glow.manager;

import com.closeplanet2.xglowcore.XGLow.data.glow.IGlow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class GlowsManager implements IGlowsManager {
    @NonFinal static GlowsManager instance = new GlowsManager();

    Set<IGlow> glows = ConcurrentHashMap.newKeySet();

    @Override
    public void addGlow(IGlow glow) {
        this.glows.add(glow);
    }

    @Override
    public void removeGlow(IGlow glow) {
        if (glow.hasViewers() || glow.hasHolders()) {
            glow.destroy();
        }

        this.glows.remove(glow);
    }

    @Override
    public Set<IGlow> getGlows() {
        return Collections.unmodifiableSet(this.glows);
    }

    public static GlowsManager getInstance() {
        return instance;
    }
}