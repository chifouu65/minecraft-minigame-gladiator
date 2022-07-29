package fr.antiquemc.game.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private final Location pos1;
    private final Location pos2;
    private final List<UUID> players;

    public Arena(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.players = new ArrayList<>();
    }



    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        if(players.size() == 1) {
            player.teleport(pos1);
            System.out.println(player.getName() + " " + pos1);
        } else {
            player.teleport(pos2);
            System.out.println(player.getName() + " " + pos2);
        }
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void reset(List<UUID> playersTpToGame) {
        playersTpToGame.addAll(players);
        players.clear();
    }
}
