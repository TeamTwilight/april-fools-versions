package twilightforest.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import twilightforest.TwilightForestMod;

@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent {
    private static final ResourceLocation KOBOLD_LOCATION = TwilightForestMod.getGuiTexture("icons.png");

    @Shadow protected abstract Player getCameraPlayer();
    @Shadow protected int screenWidth;
    @Shadow protected int screenHeight;

    @Shadow @Final protected Minecraft minecraft;

    @Shadow public abstract Font getFont();

    @Inject(method = "renderExperienceBar", at = @At(value = "HEAD"))
    private void renderExperienceBar(PoseStack pPoseStack, int xPos, CallbackInfo ci) {
        if (this.minecraft.player != null) {
            RenderSystem.setShaderTexture(0, KOBOLD_LOCATION);
            long time = this.minecraft.player.level.getGameTime();
            final long tenMinutes = 12000L;
            long untilNext = time % tenMinutes;
            float percent = (float) untilNext / (float) tenMinutes;

            int k = (int) (percent * 183.0F);
            int yPos = this.screenHeight - 50 + 3;
            this.blit(pPoseStack, xPos, yPos, 0, 64, 182, 5);
            if (k > 0) {
                this.blit(pPoseStack, xPos, yPos, 0, 69, k, 5);
            }

            int lvl = (int) (time / tenMinutes);
            String s = "" + lvl;
            int i1 = (this.screenWidth - this.getFont().width(s)) / 2;
            int j1 = this.screenHeight - 49 - 4;
            this.getFont().draw(pPoseStack, s, (float) (i1 + 1), (float) j1, 0);
            this.getFont().draw(pPoseStack, s, (float) (i1 - 1), (float) j1, 0);
            this.getFont().draw(pPoseStack, s, (float) i1, (float) (j1 + 1), 0);
            this.getFont().draw(pPoseStack, s, (float) i1, (float) (j1 - 1), 0);
            this.getFont().draw(pPoseStack, s, (float) i1, (float) j1, 10428671);
        }
    }
}
