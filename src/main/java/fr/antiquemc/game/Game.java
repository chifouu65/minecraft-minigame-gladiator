package fr.antiquemc.game;

import fr.antiquemc.game.listener.PlayerJoinListener;
import fr.antiquemc.game.manager.CommandsManager;
import fr.antiquemc.game.manager.EventManager;
import fr.antiquemc.game.manager.GManager;
import fr.antiquemc.game.task.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

public final class Game extends JavaPlugin {

    public static Game instance;
    private EventManager eventManager;
    private CommandsManager commandsManager;
    public static List<UUID> spec;
    public static Random random;

    @Override
    public void onEnable() {
        GManager.initMAP();
        instance = this;
        this.eventManager = new EventManager(this);
        CommandsManager.setupCommandsSystem(this);
        spec = new ArrayList<>();
        random = new Random();
        GameStatus.setGameStatus(GameStatus.LOBBY);
    }
    public static Game getInstance() {
        return instance;
    }
    public Random getRandom() {
        return random;
    }

    @Override
    public void onDisable() {
    }

}
