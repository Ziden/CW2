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


public class GetCardCmd extends SubCmd {

    public GetCardCmd() {
        super("pegar", ExecutorType.OP);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            Card c = CardwarsPlugin.storage.getCardDebug(args[1]);
            if(c == null) {
                p.sendMessage("Carta inexistente");
                return;
            }
            p.getInventory().addItem(c.getItemStack());
        }
    }

}
