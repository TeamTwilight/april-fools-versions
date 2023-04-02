package twilightforest.mixin;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(AbstractSoundInstance.class)
@ParametersAreNonnullByDefault
public abstract class AbstractSoundInstanceMixin {

    @Inject(method = "getVolume", at = @At(value = "RETURN"), cancellable = true, remap = true)
    private void setVolume(CallbackInfoReturnable<Float> cir) {
        if (false) {
            cir.setReturnValue(cir.getReturnValue() * 0.75F);
        }
    }

    @Inject(method = "getPitch", at = @At(value = "RETURN"), cancellable = true, remap = true)
    private void setPitch(CallbackInfoReturnable<Float> cir) {
        if (false) {
            cir.setReturnValue(cir.getReturnValue() * 0.25F);
        }
    }
}