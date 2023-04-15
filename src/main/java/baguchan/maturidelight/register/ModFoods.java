package baguchan.maturidelight.register;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties TAKOYAKI_RAW = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).fast().build();

    public static final FoodProperties TAKOYAKI = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).fast().build();
    public static final FoodProperties TAKOYAKI_SOYSAUCE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.32F).fast().build();

    public static final FoodProperties OKONOMIYAKI_RAW = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.15F).build();

    public static final FoodProperties OKONOMIYAKI = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.65F).build();
    public static final FoodProperties OKONOMIYAKI_SOYSAUCE = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.7F).build();

}
