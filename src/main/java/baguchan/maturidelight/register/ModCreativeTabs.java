package baguchan.maturidelight.register;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    public static final CreativeModeTab MATURI_DELIGHT = new CreativeModeTab("maturidelight") {
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TAKOYAKI.get());
        }
    };
}
