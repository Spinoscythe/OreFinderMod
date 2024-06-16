package io.github.spinoscythe.orefindermod;

import io.github.spinoscythe.orefindermod.item.OreFinderItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(OreFinderMod.MODID)
public final class OreFinderMod {
    public static final String MODID = "orefindermod";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<OreFinderItem> ORE_FINDER = ITEMS.registerItem("ore_finder", OreFinderItem::new);

    public OreFinderMod(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener((final BuildCreativeModeTabContentsEvent event) -> {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) event.accept(ORE_FINDER);
        });
        NeoForge.EVENT_BUS.register(this);
    }
}
