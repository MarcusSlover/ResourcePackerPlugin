package me.marcusslover.resourcepackerplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResourcePackerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        loadCommand("itemdata", new ItemDataCommand());
    }

    private void loadCommand(@NotNull String label, @Nullable CommandExecutor executor) {
        if (executor == null) return;
        PluginCommand command = getCommand(label);
        if (command == null) return;
        command.setExecutor(executor);
    }
}
