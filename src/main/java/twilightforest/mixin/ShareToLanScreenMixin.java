package twilightforest.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(ShareToLanScreen.class)
public abstract class ShareToLanScreenMixin extends Screen  {
    @Shadow private GameType gameMode;
    @Shadow private boolean commands;
    @Shadow @Final private Screen lastScreen;
    @Shadow @Final private static Component GAME_MODE_LABEL;

    protected ShareToLanScreenMixin(Component pTitle) {
        super(pTitle);
    }

    /**
     * @author jodlodi
     * @reason tee hee exs dee
     */
    @Overwrite
    protected void init() {
        if (this.minecraft == null) return;

        this.addRenderableWidget(CycleButton.builder(GameType::getShortDisplayName).withValues(GameType.SURVIVAL, GameType.ADVENTURE).withInitialValue(this.gameMode).create(this.width / 2 - 102, 100, 204, 20, GAME_MODE_LABEL, (button, type) -> {
            this.gameMode = type;
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 155, this.height - 28, 150, 20, Component.translatable("lanServer.start"), (button) -> {
            this.minecraft.setScreen(null);
            int i = HttpUtil.getAvailablePort();
            Component component;
            if (Objects.requireNonNull(this.minecraft.getSingleplayerServer()).publishServer(this.gameMode, this.commands, i)) {
                component = Component.translatable("commands.publish.started", i);
            } else {
                component = Component.translatable("commands.publish.failed");
            }

            this.minecraft.gui.getChat().addMessage(component);
            this.minecraft.updateTitle();
        }));

        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height - 28, 150, 20, CommonComponents.GUI_CANCEL, (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }));
    }
}
