package twilightforest.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.item.KoboldGummy;

import javax.annotation.Nullable;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen  {

    @Shadow private Component deathScore;
    @Shadow @Final private Component causeOfDeath;
    @Shadow @Nullable protected abstract Style getClickedComponentStyleAt(int p_95918_);

    protected DeathScreenMixin(Component pTitle) {
        super(pTitle);
    }

    /**
     * @author jodi
     * @reason lodi
     */
    @Overwrite
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.fillGradient(pPoseStack, 0, 0, this.width, this.height, 1615855616, -1602211792);
        pPoseStack.pushPose();
        pPoseStack.scale(2.0F, 2.0F, 2.0F);
        drawCenteredString(pPoseStack, this.font, KoboldGummy.gumm ? Component.literal("APRIL FOOLS") : this.title, this.width / 2 / 2, 30, 16777215);
        pPoseStack.popPose();
        if (KoboldGummy.gumm) {
            drawCenteredString(pPoseStack, this.font, Component.literal("Get pranked, lol"), this.width / 2, 85, 16777215);
        } else  if (this.causeOfDeath != null) {
            drawCenteredString(pPoseStack, this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
        }

        drawCenteredString(pPoseStack, this.font, this.deathScore, this.width / 2, 100, 16777215);
        if (this.causeOfDeath != null && pMouseY > 85 && pMouseY < 85 + 9) {
            Style style = this.getClickedComponentStyleAt(pMouseX);
            this.renderComponentHoverEffect(pPoseStack, style, pMouseX, pMouseY);
        }

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
