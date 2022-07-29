package fr.antiquemc.game.task.runnable;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.listener.PlayerIngGameListener;
import fr.antiquemc.game.listener.PlayerJoinListener;
import fr.antiquemc.game.manager.GManager;
import fr.antiquemc.game.task.GameStatus;
import fr.antiquemc.game.utils.itembuilder.C;
import fr.antiquemc.game.utils.itembuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HubRunnable extends BukkitRunnable {
    public static int timer = 30;
    public static boolean start = false;

    @Override
    public void run() {
        if (!(GameStatus.isGameStatus(GameStatus.LOBBY))) {
            timer = 30;
            start = false;
            this.cancel();
            return;
        }

        if (GManager.alivePlayers.size() <= 7) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ANVIL_BREAK, 1f, 1f);
            }
            Bukkit.broadcastMessage(C.Gladiator + "il n'y a pas assez de joueur pour demarrer [" + Bukkit.getOnlinePlayers().size() + "/12] \n\n§c! 8 joueurs requis");
            timer = 30;
            start = false;
            this.cancel();
            return;
        }

        if (timer < 5 || (timer % 5 == 0 && timer <= 15)) {
            Bukkit.broadcastMessage(C.Gladiator + "Debut de la partie dans §a" + timer + "§r secondes");
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
            }
        }

        if (timer == 0) {
            Bukkit.broadcastMessage("Size of alive players : " + GManager.alivePlayers.size());
            start = true;
            GameStatus.setGameStatus(GameStatus.GAME);
            Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> {
                GManager.teleportPlayersToModules(true);
                setKit();
                Bukkit.broadcastMessage(C.Gladiator + "§aDébut du du jeu #HubRunnable");
                new GameRunnable().runTaskTimer(Game.getInstance(), 0, 20L);
            }, 20L);
            this.cancel();
        }
        setLevel();
        timer--;
    }

    public void kit1(Player player) {
        Inventory inventory = player.getInventory();

        player.getInventory().setArmorContents(new ItemStack[]{
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
        });

        inventory.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .toItemStack());
        inventory.setItem(1, new ItemBuilder(Material.BOW).toItemStack());
        inventory.setItem(2, new ItemBuilder(Material.ARROW, 4).toItemStack());
        inventory.setItem(3, new ItemBuilder(Material.GOLDEN_APPLE, 3).toItemStack());
        inventory.setItem(4, new ItemBuilder(Material.FISHING_ROD, 1).toItemStack());
        inventory.setItem(5, new ItemBuilder(Material.COOKED_BEEF, 32).toItemStack());
    }

    public void kit2(Player player) {
        Inventory inventory = player.getInventory();

        player.getInventory().setArmorContents(new ItemStack[]{
                new ItemBuilder(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),

        });

        inventory.setItem(0, new ItemBuilder(Material.IRON_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .addEnchant(Enchantment.KNOCKBACK, 1)
                .toItemStack());
        inventory.setItem(1, new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,1).toItemStack());
        inventory.setItem(2, new ItemBuilder(Material.ARROW, 12).toItemStack());
        inventory.setItem(3, new ItemBuilder(Material.GOLDEN_APPLE, 4).toItemStack());
        inventory.setItem(4, new ItemBuilder(Material.FISHING_ROD, 1).toItemStack());
        inventory.setItem(5, new ItemBuilder(Material.COOKED_BEEF, 32).toItemStack());
    }

    public void kit3(Player player) {
        Inventory inventory = player.getInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

        player.getInventory().setArmorContents(new ItemStack[]{
                new ItemBuilder(Material.GOLD_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
        });

        inventory.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 2)
                .toItemStack());
        inventory.setItem(3, new ItemBuilder(Material.GOLDEN_APPLE, 6).toItemStack());
        inventory.setItem(4, new ItemBuilder(Material.FISHING_ROD, 1).toItemStack());
        inventory.setItem(5, new ItemBuilder(Material.COOKED_BEEF, 32).toItemStack());
    }

    public void setKit() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            if (PlayerJoinListener.kit1_players.contains(player)) {
                kit1(player);
            }

            if (PlayerJoinListener.kit2_players.contains(player)) {
                kit2(player);
            }

            if (PlayerJoinListener.kit3_players.contains(player)) {
                kit3(player);
            }
        }
    }

    public static void setLevel() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(timer);
        }
    }
}
