package twilightforest.init;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;

public class TFPaintings {//burning_kobold
    public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, TwilightForestMod.ID);

    public static final RegistryObject<PaintingVariant> KOBOLD = REGISTRY.register("kobold", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> MASON = REGISTRY.register("mason", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> BUST = REGISTRY.register("bust", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> KOBOLD_SCENE = REGISTRY.register("koboldscene", () -> new PaintingVariant(64, 64));
    public static final RegistryObject<PaintingVariant> KOBOLD_AND_NO_ROSES = REGISTRY.register("kobold_and_no_roses", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> WATER_KOBOLD = REGISTRY.register("shape_of_kobold", () -> new PaintingVariant(64, 64));
    public static final RegistryObject<PaintingVariant> BURNING_KOBOLD = REGISTRY.register("burning_kobold", () -> new PaintingVariant(64, 64));
    public static final RegistryObject<PaintingVariant> FAQ = REGISTRY.register("faq", () -> new PaintingVariant(64, 48));
}
