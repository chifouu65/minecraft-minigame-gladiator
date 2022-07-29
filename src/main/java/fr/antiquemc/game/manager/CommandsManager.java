package fr.antiquemc.game.manager;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.command.AdminCommand;
import fr.antiquemc.game.command.framework.CommandFramework;

public class CommandsManager {

    private static CommandFramework commandFramework;

    public static void setupCommandsSystem(Game instance) {
        commandFramework = new CommandFramework(instance);
        registerNewCommand(new AdminCommand(instance));
    }

    public static void registerNewCommand(Object object) {
        commandFramework.registerCommands(object);
    }
}
