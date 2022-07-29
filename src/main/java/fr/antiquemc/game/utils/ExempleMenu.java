package fr.antiquemc.game.utils;

import fr.antiquemc.game.utils.itembuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExempleMenu extends Menu{

    public ExempleMenu() {
        super(9, "Exemple", "noah");
        setItem(0, new ItemBuilder(Material.BARRIER).toItemStack(), (player, item) -> {
            player.closeInventory();
        });
        setItem(1, new ItemBuilder(Material.STONE).toItemStack());
        setItem(2, new ItemBuilder(Material.STONE).toItemStack());
        setItem(3, new ItemBuilder(Material.ANVIL).toItemStack(), (player, item) -> {
            ItemStack ib = item.getCurrentItem();
            if(ib.getType() == Material.STONE) ib = new ItemBuilder(Material.RED_ROSE).toItemStack();
            else ib = new ItemBuilder(Material.ANVIL).toItemStack();

            //permet que si je change l'item dans le menu il change dans le menu de tout les joueurs
        for (Player viewer : getViewers()) {
                Menu m = Menu.getMenu(viewer);
                if(m == null) continue;
                //si c'est le joueur qui change le menu /item
                if(viewer.equals(player)) {
                    m.setItem(item.getRawSlot(), ib);
                }
               return;
            }
            close(player);
        });

        setItem(7, new ItemBuilder(Material.SKULL_ITEM).toItemStack());
        setItem(8, new ItemBuilder(Material.BOOK).toItemStack());

        setOpenAction(p -> p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f));
        setCloseAction(p -> p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f));


    }

}
