package fr.antiquemc.game.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.*;

public class Menu {

    private static final Map<UUID, Menu> openMenu = new HashMap<>();
    private static final Map<String, Set<UUID>> viewMenu = new HashMap<>();

    private final Map<Integer, MenuClick> menuClickActions = new HashMap<>();

    private MenuClick generalClickAction;
    private MenuClick generalInvClickAction;
    private MenuDrag generalDragAction;
    private MenuOpen openAction;
    private MenuClose closeAction;

    public final UUID uuid;
    private final Inventory inventory;
    private final String viewerID;

    public Menu(int size, Component name) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, size, String.valueOf(name));
        viewerID = null;
    }

    public Menu(int size, String name, String viewerID) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, size, String.valueOf(name));
        this.viewerID = viewerID;
    }

    public static Menu getMenu(Player player) {
        return openMenu.getOrDefault(player.getUniqueId(), null);
    }

    public void open(Player player) {
        player.openInventory(inventory);
        openMenu.put(player.getUniqueId(), this);
        if (viewerID != null) addViewer(player);
        if (openAction != null) openAction.onOpen(player);
    }

    public void remove() {
        openMenu.entrySet().removeIf(entry -> {
                    if (entry.getValue().uuid.equals(uuid)) {
                        Player player = Bukkit.getPlayer(entry.getKey());
                        if (player != null) {
                            if (viewerID != null) removeViewer(player);
                            if (closeAction != null) closeAction.onClose(player);
                        }
                        return true;
                    }
                    return false;
                }
        );
    }

    public void close(Player player) {
        player.closeInventory();
        openMenu.entrySet().removeIf(entry -> {
            if (entry.getKey().equals(player.getUniqueId())) {
                if (viewerID != null) removeViewer(player);
                if (closeAction != null) closeAction.onClose(player);
                return true;
            }
            return false;
        });
        if (viewerID != null) removeViewer(player);
        if (closeAction != null) closeAction.onClose(player);
    }

    public void addViewer(Player player) {
        if (viewerID == null) return;
        Set<UUID> list = viewMenu.getOrDefault(viewerID, new HashSet<>());
        list.add(player.getUniqueId());
        viewMenu.put(viewerID, list);
    }

    public void removeViewer(Player player) {
        if (viewerID == null) return;
        Set<UUID> list = viewMenu.getOrDefault(viewerID, null);
        if (list == null) return;
        list.remove(player.getUniqueId());
        if (list.isEmpty()) viewMenu.remove(viewerID);
        else viewMenu.put(viewerID, list);
    }

    public Set<Player> getViewers() {
        if (viewerID == null) return new HashSet<>();
        Set<Player> viewerList = new HashSet<>();
        for (UUID uuid : viewMenu.getOrDefault(viewerID, new HashSet<>())) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) continue;
            viewerList.add(player);
        }
        return viewerList;
    }

    public MenuClick getAction(int index) {
        return menuClickActions.getOrDefault(index, null);
    }

    public MenuClick getGeneralClickAction() {
        return generalClickAction;
    }

    protected void setGeneralClickAction(MenuClick generalClickAction) {
        this.generalClickAction = generalClickAction;
    }

    public MenuClick getGeneralInvClickAction() {
        return generalInvClickAction;
    }

    protected void setGeneralInvClickAction(MenuClick generalInvClickAction) {
        this.generalInvClickAction = generalInvClickAction;
    }

    public MenuDrag getGeneralDragAction() {
        return generalDragAction;
    }

    protected void setGeneralDragAction(MenuDrag generalDragAction) {
        this.generalDragAction = generalDragAction;
    }

    public MenuOpen getOpenAction() {
        return openAction;
    }

    protected void setOpenAction(MenuOpen openAction) {
        this.openAction = openAction;
    }

    public MenuClose getCloseAction() {
        return closeAction;
    }

    protected void setCloseAction(MenuClose closeAction) {
        this.closeAction = closeAction;
    }

    public interface MenuClick {
        void onClick(Player p, InventoryClickEvent event);
    }

    public interface MenuDrag {
        void onDrag(Player p, InventoryDragEvent event);
    }

    public interface MenuOpen {
        void onOpen(Player p);
    }

    public interface MenuClose {
        void onClose(Player p);
    }

    public void setItem(int index, ItemStack item) {
        inventory.setItem(index, item);
    }

    public void setItem(int index, ItemStack item, MenuClick click) {
        inventory.setItem(index, item);
        if (click == null)
            menuClickActions.remove(index);
        else menuClickActions.put(index, click);
    }
}
