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

import me.marcusslover.resourcepacker.core.object.block.RPState;
import me.marcusslover.resourcepacker.util.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BlockListener implements Listener {

    /*Just in case this ever changes*/
    private static final Material CUSTOM_BLOCK = Material.NOTE_BLOCK;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        ItemStack i = e.getItemInHand();

        if (i.getType() != CUSTOM_BLOCK) return;
        ItemMeta meta = i.getItemMeta();
        if (meta == null) return;
        int customModelData = meta.getCustomModelData();

        RPState state = BlockUtil.craftBlock(customModelData);
        Block block = e.getBlock();
        List<RPState.Element> list = state.list();

        String id = "minecraft:" + CUSTOM_BLOCK.toString().toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (RPState.Element element : list) {
            builder.append(element.key).append("=").append(element.value).append(",");
        }
        String states = builder.toString();
        String data = id + "[" + states.substring(0, states.length() - 1) + "]";
        BlockData blockData = Bukkit.createBlockData(data);
        block.setBlockData(blockData);
    }
}
