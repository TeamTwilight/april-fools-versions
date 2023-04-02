package twilightforest.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFPaintings;

public class PaintingTagGenerator extends PaintingVariantTagsProvider {

    public PaintingTagGenerator(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, TwilightForestMod.ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(PaintingVariantTags.PLACEABLE).add(
                TFPaintings.KOBOLD.get(),
                TFPaintings.MASON.get(),
                TFPaintings.BUST.get(),
                TFPaintings.KOBOLD_SCENE.get(),
                TFPaintings.KOBOLD_AND_NO_ROSES.get(),
                TFPaintings.WATER_KOBOLD.get(),
                TFPaintings.BURNING_KOBOLD.get(),
                TFPaintings.FAQ.get()
        );
    }

    @Override
    public String getName() {
        return "Twilight Forest Painting Variant Tags";
    }
}
