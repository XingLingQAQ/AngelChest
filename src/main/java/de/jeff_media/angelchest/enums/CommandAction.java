package de.jeff_media.angelchest.enums;

import de.jeff_media.angelchest.AngelChestMain;
import de.jeff_media.angelchest.config.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a command related action (e.g. List, TP, Fetch, ...)
 */
public enum CommandAction {

    TELEPORT_TO_CHEST(Permissions.TP, "AngelChest TP", "actp"),
    FETCH_CHEST(Permissions.FETCH, "AngelChest Fetch", "acfetch"),
    UNLOCK_CHEST(Permissions.PROTECT, "", "acunlock"),
    LIST_CHESTS(Permissions.USE, "", "aclist");

    private static final AngelChestMain main = AngelChestMain.getInstance();

    private final String command;
    private final String economyReason;
    private final String permission;

    CommandAction(final String permission, final String economyReason, final String command) {
        this.permission = permission;
        this.economyReason = economyReason;
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getEconomyReason() {
        return economyReason;
    }

    public String getPermission() {
        return permission;
    }

    @Nullable public ItemStack getPriceItem(final CommandSender player) {
        String node = null;
        if(this == TELEPORT_TO_CHEST) node = "price-teleport";
        if(this == FETCH_CHEST) node = "price-fetch";
        if(node == null) return null;
        return main.getItemManager().getItem(main.getConfig().getString(node));
    }

    public double getPrice(final CommandSender player) {
        final AngelChestMain main = AngelChestMain.getInstance();
        if (this == TELEPORT_TO_CHEST) {
            return main.groupManager.getTeleportPricePerPlayer(player);
        } else if (this == FETCH_CHEST) {
            return main.groupManager.getFetchPricePerPlayer(player);
        } else {
            return 0.0D;
        }
    }

}
