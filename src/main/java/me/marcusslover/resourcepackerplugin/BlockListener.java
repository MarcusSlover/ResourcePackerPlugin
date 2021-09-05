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
