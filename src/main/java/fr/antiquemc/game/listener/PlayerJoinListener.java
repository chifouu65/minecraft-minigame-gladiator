package fr.antiquemc.game.listener;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.manager.GManager;
import fr.antiquemc.game.module.ModulePos;
import fr.antiquemc.game.task.GameStatus;
import fr.antiquemc.game.task.runnable.HubRunnable;
import fr.antiquemc.game.utils.itembuilder.C;
import fr.antiquemc.game.utils.itembuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener implements Listener {
    public static int rounds = 0;

    public static List<Player> kit1_players = new ArrayList<>();
    public static List<Player> kit2_players = new ArrayList<>();
    public static List<Player> kit3_players = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createScoreboard(player);
        if (GameStatus.isGameStatus(GameStatus.LOBBY)) {
            PlayerEvent.healthPlayer(player);
            player.teleport(ModulePos.LOBBY.getLocation());
            player.getActivePotionEffects().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().clear();
            player.setLevel(0);
            kit1_players.add(player);
            player.getInventory().setItem(0, new ItemBuilder(Material.PAPER).setName(C.Gladiator + "Kits").toItemStack());
            GManager.alivePlayers.add(player.getUniqueId());
            event.setJoinMessage(C.Gladiator + "le joueur §e" + player.getName() + "§r à rejoint la partie §a");
            if (!HubRunnable.start) {
                new HubRunnable().runTaskTimer(Game.getInstance(), 0L, 20L);
            } else {
                PlayerIngGameListener.eleminate(player);
            }
        }
    }

    public Inventory getInv() {
        Inventory inv = Bukkit.createInventory(null, 9, "§aKits");
        inv.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD)
                .setName("§aKit 1")
                        .addLoreLine("Kit Pvp")
                        .addLoreLine("§edefault kit")
                .toItemStack());
        inv.setItem(1, new ItemBuilder(Material.IRON_SWORD)
                .setName("§aKit 2")
                .addLoreLine("Kit Archer")
                .toItemStack());
        inv.setItem(2, new ItemBuilder(Material.GOLD_SWORD)
                .setName("§aKit 3")
                .addLoreLine("Kit Potions")
                .toItemStack());
        return inv;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getItem() != null) {
            if (e.getItem().getType() == Material.PAPER) {
                player.openInventory(getInv());
            }
        }
    }

    @EventHandler
    public void onMenu(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!event.getInventory().getTitle().contains("Kits")) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aKit 1")) {
            if (kit1_players.contains(player)) {
                player.sendMessage(C.Gladiator + "Kit désélectionner");
                kit1_players.remove(player);
                kit2_players.remove(player);
                kit3_players.remove(player);
            } else {
                player.sendMessage(C.Gladiator + "Kit sélectionner");
                kit1_players.add(player);
                kit2_players.remove(player);
                kit3_players.remove(player);
            }
            System.out.println(kit1_players.size() + " / " + kit2_players.size() + " / " + kit3_players.size());
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aKit 2")) {
            if (kit2_players.contains(player)) {
                player.sendMessage(C.Gladiator + "Kit désélectionner");
                kit1_players.remove(player);
                kit2_players.remove(player);
                kit3_players.remove(player);
            } else {
                player.sendMessage(C.Gladiator + "Kit sélectionner");
                kit2_players.add(player);
                kit1_players.remove(player);
                kit3_players.remove(player);
            }
            System.out.println(kit1_players.size() + " / " + kit2_players.size() + " / " + kit3_players.size());
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aKit 3")) {
            if (kit3_players.contains(player)) {
                player.sendMessage(C.Gladiator + "Kit désélectionner");
                kit1_players.remove(player);
                kit2_players.remove(player);
                kit3_players.remove(player);
            } else {
                player.sendMessage(C.Gladiator + "Kit sélectionner");
                kit3_players.add(player);
                kit2_players.remove(player);
                kit1_players.remove(player);
            }
            System.out.println(kit1_players.size() + " / " + kit2_players.size() + " / " + kit3_players.size());
        }
        event.setCancelled(true);
    }

    public static void createScoreboard(Player player) {
        ScoreboardManager scoreboardMananager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardMananager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("gladiator", "dummy");
        objective.setDisplayName("§l§e*§r -= Gladiator =- §l§e*");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score playerInG = objective.getScore("§l§aPlayer : " + player.getName());
        playerInG.setScore(1);
        player.setScoreboard(scoreboard);
    }

}
