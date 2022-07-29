package fr.antiquemc.game.listener;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.manager.GManager;
import fr.antiquemc.game.task.GameStatus;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerIngGameListener implements Listener {

    @EventHandler
    public void inGameDamage(EntityDamageByEntityEvent event) {
        if (!(GameStatus.isGameStatus(GameStatus.GAME))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(GameStatus.isGameStatus(GameStatus.GAME))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if(!(GameStatus.isGameStatus(GameStatus.GAME))) {
            event.setCancelled(true);
        }
    }

    public static void eleminate(Player player) {
        GManager.alivePlayers.remove(player.getUniqueId());
        Game.spec.add(player.getUniqueId());
        player.setGameMode(GameMode.SPECTATOR);
    }

}
