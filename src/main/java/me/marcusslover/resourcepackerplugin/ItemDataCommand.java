package me.marcusslover.resourcepackerplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemDataCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission("rpp.itemdata")) {
                if (args.length == 1) {
                    final int d;
                    try {
                        d = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        p.sendMessage(ChatColor.RED + "Invalid custom model data integer!");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
                        return true;
                    }
                    PlayerInventory inv = p.getInventory();
                    ItemStack i = inv.getItemInMainHand();

                    if (i.getType() != Material.AIR) {
                        ItemMeta itemMeta = i.getItemMeta();
                        if (itemMeta != null) {
                            if (d != 0) {
                                itemMeta.setCustomModelData(d);
                                p.sendMessage(ChatColor.GREEN + "The custom model data was assigned to your item!");
                                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_FRAME_ADD_ITEM, 1.0f, 2.0f);
                            } else {
                                itemMeta.setCustomModelData(null);
                                p.sendMessage(ChatColor.GREEN + "The custom model data of your item was reset!");
                                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, 1.0f, 2.0f);
                            }
                            i.setItemMeta(itemMeta);
                            inv.setItemInMainHand(i);
                        } else {
                            p.sendMessage(ChatColor.YELLOW + "The custom model data didn't seem to update!");
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "You aren't holding any item in your main hand!");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Correct usage: /id <custom model data>!");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
                }
            } else {
                p.sendMessage(ChatColor.RED + "No permission!");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only player can use this command!");
        }
        return true;
    }
}
