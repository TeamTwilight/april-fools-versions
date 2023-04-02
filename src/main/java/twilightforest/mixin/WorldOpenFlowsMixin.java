package twilightforest.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DatapackLoadFailureScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.item.KoboldGummy;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@Mixin(WorldOpenFlows.class)
@ParametersAreNonnullByDefault
public abstract class WorldOpenFlowsMixin {
    @Shadow @Nullable protected abstract LevelStorageSource.LevelStorageAccess createWorldAccess(String p_233156_);
    @Shadow protected abstract WorldStem loadWorldStem(LevelStorageSource.LevelStorageAccess p_233123_, boolean p_233124_, PackRepository p_233125_) throws Exception;
    @Shadow @Final private static Logger LOGGER;
    @Shadow @Final private Minecraft minecraft;
    @Shadow protected abstract CompletableFuture<Boolean> promptBundledPackLoadFailure();

    /**
     * @author jodi lodi
     * @reason modi godi fodi dodi nodi
     */
    @Overwrite
    public static void confirmWorldCreation(Minecraft minecraft, CreateWorldScreen screen, Lifecycle lifecycle, Runnable runnable) {
        runnable.run();
    }

    /**
     * @author jodi lodi
     * @reason modi godi fodi dodi nodi
     */
    @Overwrite(remap = true)
    private void doLoadLevel(Screen screen, String s, boolean b, boolean b1) {
        KoboldGummy.gumm = false;
        LevelStorageSource.LevelStorageAccess access = this.createWorldAccess(s);
        if (access != null) {
            PackRepository packrepository = WorldOpenFlows.createPackRepository(access);

            WorldStem worldstem;
            try {
                access.readAdditionalLevelSaveData(); // Read extra (e.g. modded) data from the world before creating it
                worldstem = this.loadWorldStem(access, b, packrepository);
                if (worldstem.worldData() instanceof PrimaryLevelData pld) {
                    pld.withConfirmedWarning(true);
                }
            } catch (Exception exception) {
                LOGGER.warn("Failed to load datapacks, can't proceed with server load", exception);
                this.minecraft.setScreen(new DatapackLoadFailureScreen(() -> this.doLoadLevel(screen, s, true, b1)));
                WorldOpenFlows.safeCloseAccess(access, s);
                return;
            }

            WorldData worlddata = worldstem.worldData();
            boolean flag = worlddata.worldGenSettings().isOldCustomizedWorld();
            boolean flag1 = worlddata.worldGenSettingsLifecycle() != Lifecycle.stable();
            // Forge: Skip confirmation if it has been done already for this world
            boolean skipConfirmation = worlddata instanceof PrimaryLevelData pld && pld.hasConfirmedExperimentalWarning();
            if (skipConfirmation || !b1 || !flag && !flag1) {
                this.minecraft.getClientPackSource().loadBundledResourcePack(access).thenApply((p_233177_) -> true).exceptionallyComposeAsync((p_233183_) -> {
                    LOGGER.warn("Failed to load pack: ", p_233183_);
                    return this.promptBundledPackLoadFailure();
                }, this.minecraft).thenAcceptAsync((p_233168_) -> {
                    if (p_233168_) {
                        this.minecraft.doWorldLoad(s, access, packrepository, worldstem);
                    } else {
                        worldstem.close();
                        WorldOpenFlows.safeCloseAccess(access, s);
                        this.minecraft.getClientPackSource().clearServerPack().thenRunAsync(() -> this.minecraft.setScreen(screen), this.minecraft);
                    }

                }, this.minecraft).exceptionally((p_233175_) -> {
                    this.minecraft.delayCrash(CrashReport.forThrowable(p_233175_, "Load world"));
                    return null;
                });
            } else {
                this.doLoadLevel(screen, s, b, false);
                worldstem.close();
                WorldOpenFlows.safeCloseAccess(access, s);
            }
        }
    }
}
