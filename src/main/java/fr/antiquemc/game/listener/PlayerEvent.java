package fr.antiquemc.game.listener;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.manager.GManager;
import fr.antiquemc.game.task.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerEvent implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(player.getGameMode().equals(GameMode.SPECTATOR)){
            event.setCancelled(true);
            player.sendMessage("§cVous êtes en mode spectateur vous ne pouvez pas parler ");
        }
    }

    public static void healthPlayer(Player player) {
        player.setLevel(0);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(19);
        player.setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onTp(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> {
            if (GameStatus.isGameStatus(GameStatus.GAME)) {
                if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    PlayerIngGameListener.eleminate(player);
                    System.out.println("Player " + player.getName() + " is removed");
                }
            }

            if (GameStatus.isGameStatus(GameStatus.GAME)) {
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                    GManager.alivePlayers.add(player.getUniqueId());
                    System.out.println("Player " + player.getName() + " is alive");
                }               if (GManager.alivePlayers.contains(player.getUniqueId())) {
                        System.out.println("Player " + player.getName() + " is already in list");
                    } else {

                }
            }

        }, 20L * 4);
      /*
        if (!(GameStatus.isGameStatus(GameStatus.LOBBY))) {
            Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> {
                if(event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                     if (!(GManager.alivePlayers.contains(event.getPlayer().getUniqueId()))) {
                        Bukkit.broadcastMessage("§a" + event.getPlayer().getName() + "§r n'est pas dans la list AlivePlayers il est add" + GManager.alivePlayers.size());
                        GManager.alivePlayers.add(event.getPlayer().getUniqueId());
                    }
                } else {
                    Bukkit.broadcastMessage("§c" + event.getPlayer().getName() + "§r est en mode spectateur");
                    GManager.alivePlayers.remove(event.getPlayer().getUniqueId());
                }
            }, 20L * 4);
        }*/
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();
        PlayerIngGameListener.eleminate(target);

        event.getDrops().clear();

        if(GManager.alivePlayers.size() <= 1) {
            Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> {
                event.getDrops().clear();
                Bukkit.broadcastMessage("§a" + target.getName() + "§r à gagné la partie");
                Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> {
                    Bukkit.broadcastMessage("le serveur va stop dans 10 secondes");
                    Game.getInstance().getServer().shutdown();
                }, 20L * 10);
            }, 20L * 3);
        }

    }
}
