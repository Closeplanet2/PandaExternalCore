package com.closeplanet2.placeholdercore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderManager extends PlaceholderExpansion {

    public List<PlaceholderInterface> placeholderInterfaces = new ArrayList<>();

    public void RegisterInterface(PlaceholderInterface placeholderInterface){
        placeholderInterfaces.add(placeholderInterface);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pandaapi";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PandaAPI";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(player == null){ return ""; }

        for(var interf : placeholderInterfaces){
            if(params.equals(interf.ReturnKey())){
                return interf.ReturnData(player);
            }
        }

        return null;
    }

}
