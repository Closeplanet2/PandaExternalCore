package com.closeplanet2.xglowcore;

import com.closeplanet2.xglowcore.XGLow.GlowAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class XGlowCore extends JavaPlugin {
    public static GlowAPI GlowAPI;

    @Override
    public void onEnable() {
        GlowAPI = new GlowAPI(this);
    }
}
