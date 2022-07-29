package fr.antiquemc.game.listener;

import fr.antiquemc.game.utils.itembuilder.C;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(C.Gladiator + "- 1 " + event.getPlayer().getName());
        PlayerIngGameListener.eleminate(event.getPlayer());
    }
}
