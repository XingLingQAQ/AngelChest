package de.jeff_media.AngelChestPlus.utils;

import de.jeff_media.AngelChestPlus.config.Config;
import de.jeff_media.AngelChestPlus.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class GroupUtils {

    final Main main;
    YamlConfiguration yaml;
    LinkedHashMap<String,Group> groups;
    public GroupUtils(Main main, File yamlFile) {
        this.main=main;
        if(!yamlFile.exists()) {
            main.getLogger().info("groups.yml does not exist, skipping custom group settings.");
            return;
        }
        this.yaml=YamlConfiguration.loadConfiguration(yamlFile);
        groups = new LinkedHashMap<>();

        for(String groupName : yaml.getKeys(false)) {
            int angelchestDuration = yaml.getInt(groupName+".angelchest-duration",-1);
            int chestsPerPlayer = yaml.getInt(groupName+".max-allowed-angelchests",-1);
            double priceSpawn = yaml.getDouble(groupName+".price-spawn",-1);
            double priceFetch = yaml.getDouble(groupName+".price-fetch",-1);
            double priceTeleport = yaml.getDouble(groupName+".price-teleport",-1);
            double xpPercentage = yaml.getDouble(groupName+".xp-percentage",-1);
            main.debug("Registering group "+groupName);
            Group group = new Group(angelchestDuration,chestsPerPlayer,priceSpawn,priceTeleport,priceFetch, xpPercentage);
            groups.put(groupName, group);

        }
    }

    public double getXPPercentagePerPlayer(Player p) {
        if(yaml==null) return main.getConfig().getInt(Config.XP_PERCENTAGE);
        Iterator<String> it = groups.keySet().iterator();
        double bestValueFound = -1;
        while(it.hasNext()) {
            String group = it.next();
            if(!p.hasPermission("angelchest.group."+group)) continue;
            //System.out.println(" Player is in group "+group);
            double xpPercentage = groups.get(group).xpPercentage;
            bestValueFound = Math.max((double)xpPercentage, (double)bestValueFound);
            //System.out.println("best value found: "+bestValueFound);
        }
        return bestValueFound == -1 ? main.getConfig().getDouble(Config.XP_PERCENTAGE) : bestValueFound;
    }

    public int getDurationPerPlayer(Player p) {
        if(yaml==null) return main.getConfig().getInt(Config.ANGELCHEST_DURATION);
        if(main.getConfig().getInt(Config.ANGELCHEST_DURATION)==0) return 0;
        Iterator<String> it = groups.keySet().iterator();
        int bestValueFound = -1;
        while(it.hasNext()) {
            String group = it.next();
            if(!p.hasPermission("angelchest.group."+group)) continue;
            //System.out.println(" Player is in group "+group);
            int angelchestDuration = groups.get(group).angelchestDuration;
            if(angelchestDuration==0) return 0;
            bestValueFound = Math.max(angelchestDuration, bestValueFound);
            //System.out.println("best value found: "+bestValueFound);
        }
        return bestValueFound == -1 ? main.getConfig().getInt(Config.ANGELCHEST_DURATION) : bestValueFound;
    }

    public int getChestsPerPlayer(Player p) {
        if(yaml==null) return main.getConfig().getInt(Config.MAX_ALLOWED_ANGELCHESTS);
        Iterator<String> it = groups.keySet().iterator();
        int bestValueFound = -1;
        while(it.hasNext()) {
            String group = it.next();
            if(!p.hasPermission("angelchest.group."+group)) continue;
            //System.out.println(" Player is in group "+group);
            int chestsPerPlayer = groups.get(group).chestsPerPlayer;
            bestValueFound = Math.max(chestsPerPlayer, bestValueFound);
            //System.out.println("best value found: "+bestValueFound);
        }
        return bestValueFound == -1 ? main.getConfig().getInt(Config.MAX_ALLOWED_ANGELCHESTS) : bestValueFound;
    }

    public double getSpawnPricePerPlayer(Player p) {
        if(yaml==null) return main.getConfig().getDouble(Config.PRICE);
        Iterator<String> it = groups.keySet().iterator();
        double bestValueFound = -1;
        while(it.hasNext()) {
            String group = it.next();
            if(!p.hasPermission("angelchest.group."+group)) continue;
            double pricePerPlayer = groups.get(group).priceSpawn;
            bestValueFound = Math.min(pricePerPlayer, bestValueFound);
        }
        return bestValueFound == -1 ? main.getConfig().getDouble(Config.PRICE) : bestValueFound;
    }

    public double getFetchPricePerPlayer(Player p) {
        if(yaml==null) return main.getConfig().getDouble(Config.PRICE_FETCH);
        Iterator<String> it = groups.keySet().iterator();
        double bestValueFound = -1;
        while(it.hasNext()) {
            String group = it.next();
            if(!p.hasPermission("angelchest.group."+group)) continue;
            double pricePerPlayer = groups.get(group).priceFetch;
            bestValueFound = Math.min(pricePerPlayer, bestValueFound);
        }
        return bestValueFound == -1 ? main.getConfig().getDouble(Config.PRICE_FETCH) : bestValueFound;
    }

    public double getTeleportPricePerPlayer(Player p) {
        if(yaml==null) return main.getConfig().getDouble(Config.PRICE_TELEPORT);
        Iterator<String> it = groups.keySet().iterator();
        double bestValueFound = -1;
        while(it.hasNext()) {
            String group = it.next();
            if(!p.hasPermission("angelchest.group."+group)) continue;
            double pricePerPlayer = groups.get(group).priceTeleport;
            bestValueFound = Math.min(pricePerPlayer, bestValueFound);
        }
        return bestValueFound == -1 ? main.getConfig().getDouble(Config.PRICE_TELEPORT) : bestValueFound;
    }

    static class Group {
        final int angelchestDuration;
        final int chestsPerPlayer;
        final double priceSpawn;
        final double priceTeleport;
        final double priceFetch;
        final double xpPercentage;

        Group(int angelchestDuration, int chestsPerPlayer, double priceSpawn, double priceTeleport, double priceFetch, double xpPercentage) {

            this.angelchestDuration = angelchestDuration;
            this.chestsPerPlayer = chestsPerPlayer;
            this.priceSpawn=priceSpawn;
            this.priceTeleport=priceTeleport;
            this.priceFetch=priceFetch;
            this.xpPercentage = xpPercentage;

            Main.getInstance().debug("XP Percentage: "+xpPercentage);
        }
    }

}
