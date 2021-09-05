/*
 * MIT License
 *
 * Copyright (c) 2021 MarcusSlover
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
