package cardwars.commands.list;

import cardwars.CardwarsPlugin;
import cardwars.cards.Card;
import cardwars.commands.MainCmd;
import cardwars.commands.ExecutorType;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CardsCmd extends MainCmd {

    public CardsCmd() {
        super("cartas", ExecutorType.OP);

        this.addSubcommand("ver", ExecutorType.OP, (p, args) -> {
            p.sendMessage(CardwarsPlugin.storage.getAllCards().stream()
                    .map(n -> n.getName()).collect(Collectors.joining(" | ")));
        });

        this.addSubcommand("pegar", ExecutorType.OP, (p, args) -> {
            Card c = CardwarsPlugin.storage.getCard(String.join(" ", args));
            if (c != null) {
                p.getInventory().addItem(c.toItemStack());
            } else {
                p.sendMessage("Carta inexistente");
            }
        });
    }

}
