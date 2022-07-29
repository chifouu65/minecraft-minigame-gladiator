package fr.antiquemc.game.task.runnable;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.arena.Arena;
import fr.antiquemc.game.manager.GManager;
import fr.antiquemc.game.utils.itembuilder.C;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GameRunnable extends BukkitRunnable {
    private static List<Arena> modules = new ArrayList<>();
    public static int timer = 60;
    @Override
    public void run() {
        if (GManager.alivePlayers.size() == 2) { // si il reste 2 joueur
            if (timer == 60) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.WITHER_SPAWN, 1f, 1f);
                    Bukkit.broadcastMessage(C.Gladiator + "La final va commencer dans " + timer + " secondes");
                }
                timer = 59;
                this.cancel();
            }
        } else if (GManager.alivePlayers.size() == 1) { // si il reste un seul joueur
            if (timer == 60) {
                Bukkit.broadcastMessage("§c§l" + Bukkit.getPlayer(GManager.alivePlayers.get(0)) + "§c a gagné !");
            }
        }

        if (timer == 1 || timer == 2 || timer == 3 || timer == 4 || timer == 5 || timer == 15 || timer == 30 || timer == 45 || timer == 60) { //timer
            Bukkit.broadcastMessage(C.Switch + "Switch de module dans §a" + timer +"§r secondes");
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
            }
        }

        if (timer == 0) {
            clearAllPlayer();
            Bukkit.broadcastMessage("Size of alive players : #GameRunnable " + GManager.alivePlayers.size());
            Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> {
                GManager.teleportPlayersToModules(true);
                Bukkit.getScheduler().runTaskLater(Game.getInstance(), GameRunnable::clearAllPlayer, 20L*2);
                new GameRunnable().runTaskTimer(Game.getInstance(), 0, 20L);
            }, 20L);
            timer = 60;
            this.cancel();
        }
        setLevel();
        timer--;
    }

    public static void clearAllPlayer() {
        GManager.alivePlayers.clear();
        Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players.getGameMode().equals(GameMode.SURVIVAL)) {
                    if (!(GManager.alivePlayers.contains(players.getUniqueId()))) {
                        GManager.alivePlayers.add(players.getUniqueId());
                    }
                } else if (players.getGameMode().equals(GameMode.SPECTATOR)) {
                    GManager.alivePlayers.remove(players.getUniqueId());
                }
            }
        }, 10L);
    }

    public static void setLevel() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(timer);
        }
    }
}
