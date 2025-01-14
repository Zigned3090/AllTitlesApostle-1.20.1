package z1gned.goetyrevelation.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class ModConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PLAYER_INVULNERABLE = BUILDER.comment("Determines whether the player will be invincible when there is an Obsidian monolith, Default: true").define("player_invincible", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_PLAYER_AMBIENT = BUILDER.comment("Enable player ambient sound, Default: true").define("player_ambient_sound", true);
    public static final ForgeConfigSpec.ConfigValue<Double> HELLFIRE_DAMAGE_AMOUNT = BUILDER.comment("Sets the damage percentage Hellfire deals each time，Default: 0.0444").define("hellfire_damage_amount", 0.0444D);
    public static final ForgeConfigSpec.ConfigValue<Double> HELLCLOUD_DAMAGE_AMOUNT = BUILDER.comment("Sets the damage percentage Hellcloud deals each time，Default: 0.001").define("hellcloud_damage_amount", 0.001D);
    public static final ForgeConfigSpec.ConfigValue<Double> APOLLYON_HEALTH = BUILDER.comment("Sets the apollyon's health，Default: 560").define("apollyon_health", 560.0D);
    public static final ForgeConfigSpec.ConfigValue<Double> APOLLYON_HURT_LIMIT = BUILDER.comment("Limit the amount of damage Apollyon takes each time. Default: 20.0").define("apollyon_hurt_limit", 20.0D);
    public static final ForgeConfigSpec.ConfigValue<Integer> SUMMON_APOLLYON_RANDOM_VALUE = BUILDER.comment("The probability of summoning Apollyon during the ritual of summoning an apostle (Chance: 1 / value),").defineInRange("summon_apollyon_random_value", 100, 1, Integer.MAX_VALUE);

    public static void loadConfig(ForgeConfigSpec config, String path) {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
    static {
        BUILDER.push("General");
        SPEC = BUILDER.build();
    }

}
