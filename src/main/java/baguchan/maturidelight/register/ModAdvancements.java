package baguchan.maturidelight.register;

import baguchan.maturidelight.advancement.ExtraToppingTrigger;
import baguchan.maturidelight.advancement.MakingFoodTrigger;
import baguchan.maturidelight.advancement.PoisonTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModAdvancements {
    public static final PoisonTrigger POISON = CriteriaTriggers.register(new PoisonTrigger());
    public static final ExtraToppingTrigger EXTRA_TOPPING = CriteriaTriggers.register(new ExtraToppingTrigger());

    public static final MakingFoodTrigger MAKING_FOOD = CriteriaTriggers.register(new MakingFoodTrigger());

    public static void init() {
    }
}