package fr.kazoudev.kitsoup.kits;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;

import java.util.*;
import java.util.stream.Collectors;

public enum KitEnum {
    // ITEM FORMAT = ITEM, WEAPON, ARMOR
    PVP(0, "PvP", "§cPvP","Kit de base, composé d'une épée et de soupe.", true, false, Item.get(267)),
    TANK(1, "Tank", "§6Tank", "Resistance mais Lenteur", true, false, Item.get(311)),
    GHAST(2, "Ghast", "§9Ghast","Contient une larme de ghast qui permet de lancé des boules de feu", false, false, Item.get(370)),
    STOMPER(3, "Stomper", "Stomper","Ce kit permet de prend moi de dégat de chut et des les infligés au joueurs à proximité de la chut.", false, false, Item.get(279)),
    FREEZER(4, "Freezer", "§bFreezer","Ce kit permet de gelé une adversaire grâce a un block de glace.", false, false, Item.get(370)),
    GRANDPA(5, "GrandPa", "§cGrandPa","Ce kit possède un baton KB 6", false, false, Item.get(370)),
    VIPER(6, "Viper", "§3Viper", "Possède un taux de chance d'empoisoné son adversaire.", false, true, Item.get(370)),
    VOYANT(7, "Voyant", "§9Voyant", "Voie le kit du joueur proche", false, true);

    private int id;
    private String name;
    private String colorname;
    private String desc;
    private boolean isFree;
    private boolean isPro;
    private Item[] items;

    private static Map<Integer, KitEnum> ID_MAP = new HashMap<>();
    KitEnum(int id, String name, String colorname, String desc, boolean isFree, boolean isPro, Item... items) {
        this.id = id;
        this.name = name;
        this.colorname = colorname;
        this.desc = desc;
        this.isFree = isFree;
        this.isPro = isPro;
        this.items = items;
    }

    static {
        for (KitEnum kit : values()) {
            ID_MAP.put(kit.id, kit);
        }
    }

    public static KitEnum getFromId(int id){return ID_MAP.get(id);}

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getColorName(){
        return colorname;
    }

    public String getDesc(){
        return desc;
    }

    public List<Item> getItems(){
        return Arrays.asList(items);
    }

    public static boolean kitExist(String string){
        try {
            Enum.valueOf(KitEnum.class, string);
            return true;
        } catch (IllegalArgumentException ex){
            return false;
        }
    }

    public boolean isFree(){return isFree;}

    public boolean isPro() {return isPro;}
}
