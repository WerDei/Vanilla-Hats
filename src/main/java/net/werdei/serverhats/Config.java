package net.werdei.serverhats;

import net.werdei.configloader.ConfigLoader;

public class Config
{
    public static boolean shiftClickEquipping = false;

    public static boolean dispenserEquipping = true;

    public static boolean enchanting = true;

    public static boolean allowAllItems = false;

    public static String[] allowedItems = new String[]{
            "#banners",
            "#beds",
            "feather",
            "end_rod",
            "lightning_rod",
            "spyglass",
            "cod",
            "lead",
            "bone",
            "conduit",

            "amethyst_cluster",
            "large_amethyst_bud",
            "medium_amethyst_bud",
            "small_amethyst_bud",

            "acacia_fence_gate",
            "birch_fence_gate",
            "dark_oak_fence_gate",
            "jungle_fence_gate",
            "oak_fence_gate",
            "spruce_fence_gate",
            "crimson_fence_gate",
            "warped_fence_gate",


            "azalea",
            "flowering_azalea",
            "scaffolding",
            "big_dripleaf",
            "slime_block",
            "honey_block",
            "composter",

            "glass",
            "white_stained_glass",
            "orange_stained_glass",
            "magenta_stained_glass",
            "light_blue_stained_glass",
            "yellow_stained_glass",
            "lime_stained_glass",
            "pink_stained_glass",
            "gray_stained_glass",
            "light_gray_stained_glass",
            "cyan_stained_glass",
            "purple_stained_glass",
            "blue_stained_glass",
            "brown_stained_glass",
            "green_stained_glass",
            "red_stained_glass",
            "black_stained_glass",
            "tinted_glass",
    };


    // Saving and loading

    public static void load()
    {
        ConfigLoader.load(Config.class, getFileName());
    }

    public static void save()
    {
        ConfigLoader.save(Config.class, getFileName());
    }

    private static String getFileName()
    {
        return "serverhats.json";
    }
}
