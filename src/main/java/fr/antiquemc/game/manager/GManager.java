package fr.antiquemc.game.manager;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.arena.Arena;
import fr.antiquemc.game.module.ModulePos;
import fr.antiquemc.game.task.GameStatus;
import fr.antiquemc.game.utils.itembuilder.C;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GManager {
    private static List<Arena> modules = new ArrayList<>();
    public static List<UUID> alivePlayers = modules.stream().flatMap(a -> a.getPlayers().stream()).collect(Collectors.toList());

    public static void removePlayer(Player player) {
        if (GameStatus.isGameStatus(GameStatus.GAME)) {
            for (Arena arena : modules) {
                arena.getPlayers().remove(player.getUniqueId());
            }

            if (alivePlayers.size() == 1) {
                Player winner = Bukkit.getPlayer(alivePlayers.get(0));
                winner.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1f);
                Bukkit.broadcastMessage(C.Gladiator +"Le joueur " + winner.getName() + " à gagné le jeu");
                Bukkit.getScheduler().runTaskLater(Game.getInstance(), Bukkit::shutdown, 20 * 10);
            }
        }
    }

    public static void teleportPlayersToModules(boolean reset) {
        if (reset) {
            for (Arena arena : modules) {
                arena.reset(alivePlayers);
            }
        }
        try {
            for (Arena arena : modules) {
                Player player = Bukkit.getPlayer(new ArrayList<>(alivePlayers).get(0));
                if (modules.size() >= 2) {
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        arena.addPlayer(Bukkit.getPlayer(alivePlayers.remove(0)));
                        arena.addPlayer(Bukkit.getPlayer(alivePlayers.remove(0)));
                    }
                } else if (modules.size() == 1) {
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        arena.addPlayer(Bukkit.getPlayer(alivePlayers.remove(0)));
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
           System.out.println("IndexOutOfBoundsException");
        }
    }

    public static void initMAP() {
        modules.add(new Arena(ModulePos.MODULE_ONE_POS1.getLocation(), ModulePos.MODULE_ONE_POS2.getLocation()));
        modules.add(new Arena(ModulePos.MODULE_TWO_POS1.getLocation(), ModulePos.MODULE_TWO_POS2.getLocation()));
        modules.add(new Arena(ModulePos.MODULE_TREE_POS1.getLocation(), ModulePos.MODULE_TREE_POS2.getLocation()));
        modules.add(new Arena(ModulePos.MODULE_FOUR_POS1.getLocation(), ModulePos.MODULE_FOUR_POS2.getLocation()));
        modules.add(new Arena(ModulePos.MODULE_FIVE_POS1.getLocation(), ModulePos.MODULE_FIVE_POS2.getLocation()));
        modules.add(new Arena(ModulePos.MODULE_SIX_POS1.getLocation(), ModulePos.MODULE_SIX_POS2.getLocation()));
    }
}
