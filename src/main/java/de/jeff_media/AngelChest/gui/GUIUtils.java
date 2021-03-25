package de.jeff_media.AngelChest.gui;

import de.jeff_media.AngelChest.data.AngelChest;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class GUIUtils {

    /**
     * Checks whether the clicked slot belongs to the spaces reserved for the player's items
     * @param slot slotnumber
     * @return true if it belongs to the player inventory, false if it belongs to the GUI
     */
    public static boolean isLootableInPreview(int slot) {
        return
                (slot >= GUI.PREVIEW_ARMOR_SIZE && slot < GUI.PREVIEW_ARMOR_SIZE + GUI.PREVIEW_ARMOR_SIZE)
                || (slot >= GUI.SLOT_PREVIEW_OFFHAND && slot < GUI.SLOT_PREVIEW_OFFHAND + GUI.PREVIEW_OFFHAND_SIZE)
                || (slot >= GUI.SLOT_PREVIEW_STORAGE_START && slot < GUI.SLOT_PREVIEW_STORAGE_START + GUI.PREVIEW_STORAGE_SIZE)
                || (slot >= GUI.SLOT_PREVIEW_HOTBAR_START && slot < GUI.SLOT_PREVIEW_HOTBAR_START + GUI.PREVIEW_HOTBAR_SIZE)
                || slot == GUI.SLOT_PREVIEW_XP;

    }

    public static void savePreviewInventoryToChest(Inventory inventory, AngelChest angelChest) {
        Objects.requireNonNull(angelChest,"AngelChest is null!");
        angelChest.armorInv = new ItemStack[GUI.PREVIEW_ARMOR_SIZE];
        angelChest.extraInv = new ItemStack[GUI.PREVIEW_OFFHAND_SIZE];
        angelChest.storageInv = new ItemStack[GUI.PREVIEW_STORAGE_SIZE + GUI.PREVIEW_HOTBAR_SIZE];
        /*for(int i = GUI.SLOT_PREVIEW_ARMOR_START; i < GUI.PREVIEW_ARMOR_SIZE; i++) {
            angelChest.armorInv[i - GUI.SLOT_PREVIEW_ARMOR_START] = inventory.getItem(i);
        }
        for(int i = GUI.SLOT_PREVIEW_OFFHAND; i < GUI.PREVIEW_OFFHAND_SIZE; i++) {
            angelChest.extraInv[i - GUI.SLOT_PREVIEW_OFFHAND] = inventory.getItem(i);
        }
        for(int i = GUI.SLOT_PREVIEW_HOTBAR_START; i < GUI.PREVIEW_HOTBAR_SIZE; i++) {
            angelChest.storageInv[i - GUI.SLOT_PREVIEW_HOTBAR_START] = inventory.getItem(i);
        }
        for(int i = GUI.SLOT_PREVIEW_STORAGE_START; i < GUI.PREVIEW_STORAGE_SIZE; i++) {
            angelChest.storageInv[i - GUI.SLOT_PREVIEW_STORAGE_START] = inventory.getItem(i);
        }*/
        for(int i = 0; i < GUI.PREVIEW_ARMOR_SIZE; i++) {
            angelChest.armorInv[i] = inventory.getItem(i+GUI.SLOT_PREVIEW_ARMOR_START);
        }
        for(int i = 0; i < GUI.PREVIEW_OFFHAND_SIZE; i++) {
            angelChest.extraInv[i] = inventory.getItem(i+GUI.SLOT_PREVIEW_OFFHAND);
        }
        for(int i = 0; i < GUI.PREVIEW_HOTBAR_SIZE; i++) {
            angelChest.storageInv[i] = inventory.getItem(i+GUI.SLOT_PREVIEW_HOTBAR_START);
        }
        for(int i = 0; i < GUI.PREVIEW_STORAGE_SIZE; i++) {
            angelChest.storageInv[i + GUI.PREVIEW_HOTBAR_SIZE] = inventory.getItem(i+GUI.SLOT_PREVIEW_STORAGE_START);
        }
    }

    public static void loadChestIntoPreviewInventory(AngelChest angelChest, Inventory inventory) {

        ItemStack[] itemStacks = new ItemStack[54];
        for(int i = 0; i < 54; i++) {
            itemStacks[i] = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        }

        System.arraycopy(angelChest.armorInv, 0, itemStacks, 2, GUI.PREVIEW_ARMOR_SIZE);
        System.arraycopy(angelChest.extraInv, 0, itemStacks, 7, GUI.PREVIEW_OFFHAND_SIZE);
        System.arraycopy(angelChest.storageInv, 0, itemStacks, 45, GUI.PREVIEW_HOTBAR_SIZE);
        System.arraycopy(angelChest.storageInv, 9, itemStacks, 9, GUI.PREVIEW_STORAGE_SIZE);
        for(int i = 0; i < 54; i++) {
            inventory.setItem(i,itemStacks[i]);
        }
    }

}