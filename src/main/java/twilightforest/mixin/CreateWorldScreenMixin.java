package twilightforest.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import twilightforest.item.KoboldGummy;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {
    protected CreateWorldScreenMixin(Component pTitle) {
        super(pTitle);
    }

    @Shadow private CycleButton<Difficulty> difficultyButton;
    @Shadow private Difficulty difficulty;
    @Shadow protected abstract void onCreate();

    @Shadow private CycleButton<CreateWorldScreen.SelectedGameMode> modeButton;

    @Shadow @Final private static Component GAME_MODEL_LABEL;

    @Shadow private Component gameModeHelp1;

    @Shadow private Component gameModeHelp2;

    @Shadow private CreateWorldScreen.SelectedGameMode gameMode;

    @Shadow private boolean commandsChanged;

    @Shadow private boolean commands;

    @Shadow private CycleButton<Boolean> commandsButton;

    @Shadow public boolean hardCore;

    @Shadow @Final public WorldGenSettingsComponent worldGenSettingsComponent;

    @Shadow protected abstract void updateGameModeHelp();

    @Inject(method = "init", at = @At(value = "TAIL"))
    private void init(CallbackInfo ci) {
        KoboldGummy.gumm = false;
        int i = this.width / 2 - 155;
        int j = this.width / 2 + 5;

        this.renderables.removeIf(widget -> widget instanceof CycleButton<?> && widget != this.commandsButton);

        this.modeButton = this.addRenderableWidget(CycleButton.builder(CreateWorldScreen.SelectedGameMode::getDisplayName).withValues(CreateWorldScreen.SelectedGameMode.HARDCORE, CreateWorldScreen.SelectedGameMode.HARDCORE).withInitialValue(CreateWorldScreen.SelectedGameMode.HARDCORE).withCustomNarration((button) ->
                AbstractWidget.wrapDefaultNarrationMessage(button.getMessage()).append(CommonComponents.NARRATION_SEPARATOR).append(this.gameModeHelp1).append(" ").append(this.gameModeHelp2)).create(i, 100, 150, 20, GAME_MODEL_LABEL, (button, gameMode) ->
                this.setGameMode(gameMode)));

        this.difficultyButton = this.addRenderableWidget(CycleButton.builder(Difficulty::getDisplayName).withValues(Difficulty.HARD, Difficulty.HARD, Difficulty.HARD).withInitialValue(Difficulty.HARD).create(j, 100, 150, 20, Component.translatable("options.difficulty"), (button, difficulty) ->
                this.difficulty = Difficulty.HARD));

        this.difficulty = Difficulty.HARD;
        this.setGameMode(CreateWorldScreen.SelectedGameMode.SURVIVAL);
        this.difficultyButton.active = false;
        this.commandsButton.active = false;
    }

    /**
     * @author jodlodi
     * @reason yes
     */
    @Overwrite
    private Difficulty getEffectiveDifficulty() {
        return Difficulty.HARD;
    }

    /**
     * @author jodlodi
     * @reason yes
     */
    @Overwrite
    private void setGameMode(CreateWorldScreen.SelectedGameMode pGameMode) {
        pGameMode = CreateWorldScreen.SelectedGameMode.HARDCORE;
        if (!this.commandsChanged) {
            this.commands = false;
            this.commandsButton.setValue(false);
        }

        this.hardCore = true;
        this.worldGenSettingsComponent.switchToHardcore();
        this.commandsButton.active = false;
        this.commandsButton.setValue(false);
        this.difficultyButton.setValue(Difficulty.HARD);
        this.difficultyButton.active = false;
        this.modeButton.setValue(pGameMode);


        this.gameMode = pGameMode;
        this.updateGameModeHelp();
    }
}
