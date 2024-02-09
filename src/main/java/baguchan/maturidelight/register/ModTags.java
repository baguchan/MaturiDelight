package baguchan.maturidelight.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> SALT = forgeTag("salt");
        public static final TagKey<Item> DUST_SALT = forgeTag("dust/salt");
        public static final TagKey<Item> RICE = forgeTag("crops/rice");
        public static final TagKey<Item> EGGS = forgeTag("eggs");
        public static final TagKey<Item> MISO = forgeTag("miso");
        public static final TagKey<Item> SOYSAUCE = forgeTag("soysauce");

        public Items() {
        }


        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
