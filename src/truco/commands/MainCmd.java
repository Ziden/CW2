/*

 */
package truco.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MainCmd extends Command {

    private CommandExecutor exe = null;
    ExecutorType tipo;
    public String permission = null;
    protected List<CardListener> subs = new ArrayList<CardListener>();

    public MainCmd(String name, ExecutorType c) {
        super(name);
        tipo = c;
    }

    public void showSubCommands(CommandSender cs) {
        cs.sendMessage(ChatColor.YELLOW + ".________________oO " + getName() + " Oo_____________");
        cs.sendMessage(ChatColor.YELLOW + "|");
        for (CardListener cmd : subs) {
            if (cmd.type != ExecutorType.OP) {
                cs.sendMessage(ChatColor.YELLOW + "|" + ChatColor.GREEN + " - /" + getName() + " " + cmd.cmd);
            } else if (cs.isOp()) {
                cs.sendMessage(ChatColor.YELLOW + "|" + ChatColor.BLUE + " - /" + getName() + " " + cmd.cmd + " (Op)");
            }
        }
        cs.sendMessage(ChatColor.YELLOW + "|_______________________________________");
    }

    public void addSubcommand(String cmd, ExecutorType type, CmdExecutor executor) {
        this.subs.add(new CardListener(cmd, ExecutorType.OP) {
            @Override
            public void execute(CommandSender cs, String[] args) {
                executor.execute((Player) cs, args);
            }
        });
    }

    public void usouComando(CommandSender cs, String[] args) {
        usouComandoBase(cs, args);
    }

    protected void usouComandoBase(CommandSender cs, String[] args) {
        if (args.length >= 1) {
            for (CardListener args0 : subs) {
                if (args0.cmd.equalsIgnoreCase(args[0])) {
                    args0.executeSubCmd(cs, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        } else {
            showSubCommands(cs);
        }
    }

    @Override
    public boolean execute(CommandSender cs, String commandLabel, String[] strings) {
        if (exe != null) {

            if (commandLabel.equalsIgnoreCase(getName()) || getAliases().contains(commandLabel)) {

                if (tipo == ExecutorType.CONSOLE && cs instanceof Player) {
                    cs.sendMessage("§aComando só pode ser executado no console!");
                    return true;
                }

                if ((tipo == ExecutorType.OP || tipo == ExecutorType.PLAYER) && !(cs instanceof Player)) {
                    cs.sendMessage("Comando só pode ser executado em jogo");
                    return false;
                }

                if (tipo == ExecutorType.PERMISSION) {
                    if (permission != null && !cs.hasPermission(permission)) {
                        cs.sendMessage("§cVocê não tem permissão para esse comando!");
                        return false;
                    }
                }

                if (tipo == ExecutorType.OPCONSOLE) {
                    if (cs instanceof Player) {
                        if (!cs.isOp()) {
                            cs.sendMessage("§cVocê não tem permissão para esse comando!");
                            return true;
                        }
                    }

                }

                if (tipo == ExecutorType.OP && !((Player) cs).isOp()) {
                    cs.sendMessage("§cVocê não tem permissão para esse comando!");
                    return false;
                }

                usouComando(cs, strings);

            }
            exe.onCommand(cs, this, commandLabel, strings);
        }
        return false;
    }

    public void setExecutor(CommandExecutor exe) {
        this.exe = exe;
    }

}
