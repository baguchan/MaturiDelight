package baguchan.maturidelight.register;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class ModFoods {
    public static final FoodProperties TAKOYAKI_RAW = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).fast().build();

    public static final FoodProperties TAKOYAKI = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1F).fast().build();
    public static final FoodProperties TAKOYAKI_SOYSAUCE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.32F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1F).fast().build();

    public static final FoodProperties OKONOMIYAKI_RAW = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.15F).build();

    public static final FoodProperties OKONOMIYAKI = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.65F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1F).build();
    public static final FoodProperties OKONOMIYAKI_SOYSAUCE = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.7F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1F).build();
    public static final FoodProperties YAKISOBA = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.65F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1F).build();
    public static final FoodProperties YAKISOBA_SOYSAUCE = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.7F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1F).build();

}
