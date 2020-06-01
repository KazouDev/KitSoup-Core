package fr.kazoudev.kitsoup.managers;

import cn.nukkit.Player;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.SoupPlayer;

public enum Language {
    IMPOSSIBLE("§cImpossible", "§cImpossible"),
    CHOOSE_KIT("Choisi ton kit", "Choose your kit"),
    SELECTED_KIT("Kit sélectionné: [kit]", "Selectionned kit: [kit]"),
    SETTINGS("Paramètre", "Settings"),
    SHOP("Boutique", "Shop"),
    BUY_KITS("Achetez un kit", "Buy a kit"),
    BACK("Retour", "Back"),
    CLOSE("Fermer", "Close"),
    PREFIX("Prefixe","Prefix"),
    PETS("Pets", "Pets"),
    KIT_BOX("Caisse de Kit", "Kit Box"),
    YES("Oui", "Yes"),
    NO("Non", "No"),
    ERROR_WITH_KIT("Visiblement, il y as un problème avec ce kit !", "Ups, there are a problem with this kit !"),
    BUY_BOX("Achetez 1 caisse", "Buy 1 box"),
    BUY_FIVE_BOX("Achetez 5 caisse", "Buy 5 box"),
    PREFIX_SHOP("Boutique de Prefix", "Prefix Shop"),
    NO_PERM("Tu n'a pas la permission", "§cYou haven't this permission"),
    SET_TIME("Moment de la journée défini sur", "Your time is set to"),
    STATS_OF("Stats de", "Stats of"),
    YOUR_STATS("Vos stats", "Your stats"),
    CHAT_OFF("Le chat est désactivé par un membre du staff.", "The chat is disable by the staff."),
    CHAT_SET("Chat défini sur ", "Chat set on "),
    CHAT_ALREADY("Le chat est déjà ", "The chat is already "),
    CHAT_COOLDOWN_SET("Slowmod défini sur [time]", "The slowmod is set to [time]."),
    TELEPORT_AT("Téléporté sur ", "Teleport at "),
    VANISH_REM("Mode vanish désactivé", "Vanish mod is off"),
    VANISH_SET("Mode vanish activé", "Vanish mod is on"),
    WHISPER_ON("Whisper Activé", "Whisper Enable"),
    WHISPER_OFF("Whisper Désactivé", "Whisper Disable"),
    CHAT_COOLDOWN("Attend encore [time]s !", "Don't spam ! Wait [time] second's"),
    EVENT_ALREADY("Un event est déjà en cours.", "An event already started."),
    EVENT_COOLDOWN("Un event à déjà eu lieu il y a peu de temps.", "An event was started recently.");

    public final String french;
    public final String english;

    Language(final String french, final String english){
        this.french = french;
        this.english = english;
    }

    public final String getLang(Player p){
        if(Main.getSPlayer(p).getLang().equalsIgnoreCase("FR")) {
            return this.french;
        }
        return this.english;
    }

}
