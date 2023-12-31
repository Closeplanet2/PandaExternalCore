package com.closeplanet2.xglowcore.XGLow.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WrapperPlayServerEntityMetadata extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_METADATA;

    public WrapperPlayServerEntityMetadata() {
        super(new PacketContainer(TYPE), TYPE);

        this.handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerEntityMetadata(List<WrappedWatchableObject> metadata, int entityId) {
        this();

        this.setMetadata(metadata);
        this.setEntityId(entityId);
    }

    public int getEntityId() {
        return this.handle.getIntegers().read(0);
    }

    public void setEntityId(int value) {
        this.handle.getIntegers().write(0, value);
    }

    public Entity getEntity(World world) {
        return this.handle.getEntityModifier(world).read(0);
    }

    public Entity getEntity(PacketEvent event) {
        return this.getEntity(event.getPlayer().getWorld());
    }

    public List<WrappedWatchableObject> getMetadata() {
        return this.handle.getWatchableCollectionModifier().read(0);
    }

    public void setMetadata(List<WrappedWatchableObject> value) {
        final List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();
        value.stream().filter(Objects::nonNull).forEach(entry -> {
            final WrappedDataWatcher.WrappedDataWatcherObject dataWatcherObject = entry.getWatcherObject();
            wrappedDataValueList.add(new WrappedDataValue(dataWatcherObject.getIndex(), dataWatcherObject.getSerializer(), entry.getRawValue()));
        });
        handle.getDataValueCollectionModifier().write(0, wrappedDataValueList);
    }
}
