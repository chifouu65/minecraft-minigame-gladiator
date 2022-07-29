package fr.antiquemc.game.manager;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.listener.PlayerEvent;
import fr.antiquemc.game.listener.PlayerIngGameListener;
import fr.antiquemc.game.listener.PlayerJoinListener;
import fr.antiquemc.game.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    private Game instance;
    private final PluginManager pluginManager;

    public EventManager(Game instance) {
        this.instance = instance;
        this.pluginManager = Bukkit.getPluginManager();
        registerEvents();
    }

    public void registerEvents() {
        addNewEventListener(new PlayerJoinListener());
        addNewEventListener(new PlayerQuitListener());
        addNewEventListener(new PlayerEvent());
        addNewEventListener(new PlayerIngGameListener());
    }

    public void addNewEventListener(Listener listener) {
        pluginManager.registerEvents(listener, instance);
    }

}
