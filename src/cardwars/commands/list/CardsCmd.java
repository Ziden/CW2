package cardwars.commands.list;

import cardwars.commands.Comando;
import cardwars.commands.ExecutorType;
import cardwars.commands.list.cardscmd.GetCardCmd;
import org.bukkit.command.CommandSender;


public class CardsCmd extends Comando {

    public CardsCmd() {
        super("cw", ExecutorType.OP);
        subs.add(new GetCardCmd());
    }

    @Override
    public void usouComando(CommandSender cs, String[] args) {
        usouComandoBase(cs, args);
    }
}
