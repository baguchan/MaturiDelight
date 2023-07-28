package baguchan.maturidelight.advancement;

import baguchan.maturidelight.MaturiDelight;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class PoisonTrigger extends SimpleCriterionTrigger<PoisonTrigger.Instance> {

    public static final ResourceLocation ID = MaturiDelight.prefix("poison");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext ctx) {
        return new Instance(player);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ContextAwarePredicate player) {
            super(PoisonTrigger.ID, player);
        }

        public static Instance killThemAll() {
            return new Instance(ContextAwarePredicate.ANY);
        }
    }
}