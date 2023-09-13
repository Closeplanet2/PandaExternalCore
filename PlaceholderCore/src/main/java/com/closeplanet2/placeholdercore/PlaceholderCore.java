package com.closeplanet2.placeholdercore;

import com.closeplanet2.pandaspigotcore.JAVA_CLASS.JavaClassAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaceholderCore extends JavaPlugin {
    //Placeholder
    public static PlaceholderManager placeholderManager;

    @Override
    public void onEnable() {
        if(JavaClassAPI.IsPluginInstalled("PlaceholderAPI")){
            placeholderManager = new PlaceholderManager();
            placeholderManager.register();
        }
    }
}
