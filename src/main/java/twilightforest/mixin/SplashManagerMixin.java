package twilightforest.mixin;

import net.minecraft.client.resources.SplashManager;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(SplashManager.class)
@ParametersAreNonnullByDefault
public abstract class SplashManagerMixin {
    @Shadow @Final private static RandomSource RANDOM;
    private static final String[] splashes = {
            "Why do you make me do these things?",
            "Now with extra grease!",
            "?",
            "Who the hell is Steve Jobs?",
            "Minecrat",
            "Oops! All Kobolds!",
            "Best time of year!",
            "!",
            "Merry X-mas!",
            "Happy new year!",
            "OOoooOOOoooo! Spooky!"};

    @Inject(method = "getSplash", at = @At(value = "HEAD"), cancellable = true)
    private void getSplash(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(splashes[RANDOM.nextInt(splashes.length)]);
    }
}
