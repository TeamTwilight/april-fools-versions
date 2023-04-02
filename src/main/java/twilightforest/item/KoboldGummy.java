package twilightforest.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class KoboldGummy extends Item {
    public static boolean gumm = false;

    public KoboldGummy(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        gumm = false;
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pStack = pLivingEntity.eat(pLevel, pStack);
        pLivingEntity.kill();
        gumm = true;
        return pStack;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 64;
    }
}
