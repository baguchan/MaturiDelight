package baguchan.maturidelight.register;

import baguchan.maturidelight.MaturiDelight;
import baguchan.maturidelight.item.SuspiciousItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MaturiDelight.MODID);
    public static final RegistryObject<Item> DOUGH_RAW = ITEMS.register("dough_raw", () -> new Item((new Item.Properties()).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> TAKOYAKI_RAW = ITEMS.register("takoyaki_raw", () -> new SuspiciousItem((new Item.Properties()).food(ModFoods.TAKOYAKI_RAW)));
    public static final RegistryObject<Item> TAKOYAKI = ITEMS.register("takoyaki", () -> new SuspiciousItem((new Item.Properties()).food(ModFoods.TAKOYAKI)));
    public static final RegistryObject<Item> TAKOYAKI_SOYSAUCE = ITEMS.register("takoyaki_soysauce", () -> new SuspiciousItem((new Item.Properties()).food(ModFoods.TAKOYAKI_SOYSAUCE)));
    public static final RegistryObject<Item> OKONOMIYAKI_RAW = ITEMS.register("okonomiyaki_raw", () -> new SuspiciousItem((new Item.Properties()).food(ModFoods.OKONOMIYAKI_RAW)));
    public static final RegistryObject<Item> OKONOMIYAKI = ITEMS.register("okonomiyaki", () -> new SuspiciousItem((new Item.Properties()).food(ModFoods.OKONOMIYAKI)));
    public static final RegistryObject<Item> OKONOMIYAKI_SOYSAUCE = ITEMS.register("okonomiyaki_soysauce", () -> new SuspiciousItem((new Item.Properties()).food(ModFoods.OKONOMIYAKI_SOYSAUCE)));

}
