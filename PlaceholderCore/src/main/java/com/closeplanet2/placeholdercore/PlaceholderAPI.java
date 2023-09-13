package com.closeplanet2.placeholdercore;

import com.closeplanet2.pandaspigotcore.ENCHANTMENT.PandaEnchant;
import com.closeplanet2.pandaspigotcore.EVENTS.PandaEvent;
import com.closeplanet2.pandaspigotcore.JAVA_CLASS.JavaClassAPI;
import com.closeplanet2.pandaspigotcore.LOOPS.PandaLoop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

public class PlaceholderAPI {
    public static boolean Register(JavaPlugin javaPlugin, String path) throws Exception {
        if(!JavaClassAPI.IsPluginInstalled("PlaceholderAPI")) return false;
        var full_data = ReturnAllClasses(javaPlugin, path);
        for(var panda_interface : full_data.keySet()){
            if(panda_interface == PandaPlaceholder.class) RegisterPlaceholder(full_data.get(panda_interface), javaPlugin);
        }
        return true;
    }

    private static HashMap<Class<?>, List<Class<?>>> ReturnAllClasses(JavaPlugin javaPlugin, String path) throws URISyntaxException, IOException {
        var information = new HashMap<Class<?>, List<Class<?>>>();
        information.put(PandaPlaceholder.class, new ArrayList<>());

        for(var class_name : ReturnClassNames(javaPlugin, path)){
            try {
                var found_class = Class.forName(class_name);
                if(found_class.isAnnotationPresent(PandaPlaceholder.class)) information.get(PandaPlaceholder.class).add(found_class);
            } catch (ClassNotFoundException e) { e.printStackTrace(); }
        }

        return information;
    }

    private static List<String> ReturnClassNames(JavaPlugin javaPlugin, String path) throws URISyntaxException, IOException{
        var fileDir = new File(javaPlugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        var zip = new ZipInputStream(new FileInputStream(fileDir));
        var classNames = new ArrayList<String>();
        var entry = zip.getNextEntry();
        while(entry != null){
            if(!entry.isDirectory() && entry.getName().endsWith(".class") && !entry.getName().contains("$")){
                var className = entry.getName().replace('/', '.').replace(".class", "");
                if(className.contains(path)){
                    classNames.add(className);
                }
            }
            entry = zip.getNextEntry();
        }
        return classNames;
    }

    private static void RegisterPlaceholder(List<Class<?>> classes, JavaPlugin plugin){
        for(var clazz : classes){
            PlaceholderInterface listener;
            try {
                listener = (PlaceholderInterface) clazz.getConstructor(plugin.getClass()).newInstance(plugin);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                try {
                    listener = (PlaceholderInterface) clazz.getConstructor(JavaPlugin.class).newInstance(plugin);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ev1) {
                    try {
                        listener = (PlaceholderInterface) clazz.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ev) {
                        ev.printStackTrace();
                        return;
                    }
                }
            }

            PlaceholderCore.placeholderManager.RegisterInterface(listener);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + plugin.getDescription().getFullName() + ": Registered placeholder " + listener.getClass().getName());
        }
    }
}
