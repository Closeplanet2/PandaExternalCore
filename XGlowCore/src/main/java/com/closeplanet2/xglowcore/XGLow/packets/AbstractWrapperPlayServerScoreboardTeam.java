package com.closeplanet2.xglowcore.XGLow.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractWrapperPlayServerScoreboardTeam
        extends AbstractPacket
        implements IWrapperPlayServerScoreboardTeam {
    public static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_TEAM;

    public AbstractWrapperPlayServerScoreboardTeam() {
        super(new PacketContainer(TYPE), TYPE);

        this.handle.getModifier().writeDefaults();
    }

    @Override
    public AbstractPacket getPacket() {
        return this;
    }

    @Override
    public String getTeamName() {
        return this.handle.getStrings().read(0);
    }

    @Override
    public void setTeamName(String value) {
        this.handle.getStrings().write(0, value);
    }

    @Override
    public Mode getMode() {
        return Mode.values()[this.handle.getIntegers().read(0)];
    }

    @Override
    public void setMode(Mode value) {
        this.handle.getIntegers().write(0, value.ordinal());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getPlayers() {
        return (List<String>) this.handle.getSpecificModifier(Collection.class)
                .read(0);
    }

    @Override
    public void setPlayers(List<String> value) {
        this.handle.getSpecificModifier(Collection.class)
                .write(0, value);
    }

    public enum Mode {
        TEAM_CREATED,
        TEAM_REMOVED,
        TEAM_UPDATED,
        PLAYERS_ADDED,
        PLAYERS_REMOVED
    }

    @Getter
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public enum NameTagVisibility {
        ALWAYS("always"),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam"),
        NEVER("never");

        String identifier;

        public static Optional<NameTagVisibility> getByIdentifier(String identifier) {
            return Arrays.stream(values())
                    .filter((visibility) -> visibility.getIdentifier().equalsIgnoreCase(identifier))
                    .findFirst();
        }
    }

    @Getter
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public enum CollisionRule {
        ALWAYS("always"),
        PUSH_OTHER_TEAMS("pushOtherTeams"),
        PUSH_OWN_TEAM("pushOwnTeam"),
        NEVER("never");

        String identifier;

        public static Optional<CollisionRule> getByIdentifier(String identifier) {
            return Arrays.stream(values())
                    .filter((rule) -> rule.getIdentifier().equalsIgnoreCase(identifier))
                    .findFirst();
        }
    }
}
