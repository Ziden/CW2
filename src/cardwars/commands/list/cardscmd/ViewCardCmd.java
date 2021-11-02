package cardwars.commands.list.cardscmd;

import cardwars.CardwarsPlugin;
import cardwars.cards.Card;
import cardwars.commands.ExecutorType;
import cardwars.commands.SubCmd;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class ViewCardCmd extends SubCmd {

    public ViewCardCmd() {
        super("ver", ExecutorType.OP);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            String s = "";
            for(Card c : CardwarsPlugin.storage.getAllCards())
                s+= c.getName() +" || ";
            p.sendMessage(s);
        }
    }
}
