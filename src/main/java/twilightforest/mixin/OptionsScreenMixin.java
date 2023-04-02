package twilightforest.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@Mixin(OptionsScreen.class)
@ParametersAreNonnullByDefault
public abstract class OptionsScreenMixin {
    @Inject(method = "createDifficultyButton", at = @At(value = "HEAD"), cancellable = true)
    private static void createDifficultyButton(int pAmount, int pWidth, int pHeight, String pTranslatableKey, Minecraft pMinecraft, CallbackInfoReturnable<CycleButton<Difficulty>> cir) {
        cir.setReturnValue(CycleButton.builder(Difficulty::getDisplayName).withValues(Difficulty.HARD, Difficulty.HARD, Difficulty.HARD).withInitialValue(Difficulty.HARD).create(pWidth / 2 - 155 + pAmount % 2 * 160, pHeight / 6 - 12 + 24 * (pAmount >> 1), 150, 20, Component.translatable(pTranslatableKey), (button, packet) -> {
            Objects.requireNonNull(pMinecraft.getConnection()).send(new ServerboundChangeDifficultyPacket(packet));
        }));
    }
}
