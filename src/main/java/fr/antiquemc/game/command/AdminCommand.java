package fr.antiquemc.game.command;

import fr.antiquemc.game.Game;
import fr.antiquemc.game.command.framework.Command;
import fr.antiquemc.game.command.framework.CommandArgs;
import fr.antiquemc.game.task.runnable.HubRunnable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand {

    public static String message = new String();
    private Game INSTANCE;
    public AdminCommand(Game INSTANCE) {
        this.INSTANCE = INSTANCE;
    }

    @Command(name = "bug", aliases = {"bugs", "bugreport"})
    public void onStart(CommandArgs args) {
        CommandSender sender = args.getSender();
        Player player = (Player) sender;

        if(args.length() != 1){
            player.sendMessage("§cVeuillez saisir un message !");
        } else if (args.getArgs(0).equals("start")) {
            player.sendMessage("§ala partie commence !");
            HubRunnable.timer = 10;
        }
    }
}