package twilightforest.init;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.sounds.Music;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import twilightforest.TwilightForestMod;
import twilightforest.block.TorchberryPlantBlock;
import twilightforest.world.components.feature.config.HollowLogConfig;
import twilightforest.world.components.feature.config.RootConfig;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.components.feature.config.ThornsConfig;
import twilightforest.world.registration.TreeConfigurations;
import twilightforest.world.registration.TreeDecorators;

import java.util.List;

public final class TFConfiguredFeatures {

	//vanilla features with custom placement code
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_LAVA = register("lava_lake", Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(Blocks.LAVA.defaultBlockState()), BlockStateProvider.simple(Blocks.STONE)));
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_WATER = register("water_lake", Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(Blocks.WATER), BlockStateProvider.simple(Blocks.STONE)));

	//"structures" that arent actually structures
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> SIMPLE_WELL = register("simple_well", TFFeatures.SIMPLE_WELL.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> FANCY_WELL = register("fancy_well", TFFeatures.FANCY_WELL.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> DRUID_HUT = register("druid_hut", TFFeatures.DRUID_HUT.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> GRAVEYARD = register("graveyard", TFFeatures.GRAVEYARD.get(), FeatureConfiguration.NONE);

	//all the fun little things you find around the dimension
	public static final Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_MUSHGLOOM = register("mushroom/big_mushgloom", TFFeatures.BIG_MUSHGLOOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(TFBlocks.HUGE_MUSHGLOOM.get().defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.TRUE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), BlockStateProvider.simple(TFBlocks.HUGE_MUSHGLOOM_STEM.get().defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.FALSE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), 1));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> FALLEN_LEAVES = register("fallen_leaves", TFFeatures.FALLEN_LEAVES.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MAYAPPLE = register("mayapple", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TFBlocks.MAYAPPLE.get()))));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FIDDLEHEAD = register("fiddlehead", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TFBlocks.FIDDLEHEAD.get()))));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> FIRE_JET = register("fire_jet", TFFeatures.FIRE_JET.get(), new BlockStateConfiguration(TFBlocks.FIRE_JET.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> FOUNDATION = register("foundation", TFFeatures.FOUNDATION.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> GROVE_RUINS = register("grove_ruins", TFFeatures.GROVE_RUINS.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> HOLLOW_LOG = register("hollow_log", TFFeatures.FALLEN_HOLLOW_LOG.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, ?>> HOLLOW_STUMP = register("hollow_stump", TFFeatures.HOLLOW_STUMP.get(), TreeConfigurations.HOLLOW_TREE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> HUGE_LILY_PAD = register("huge_lily_pad", TFFeatures.HUGE_LILY_PAD.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> HUGE_WATER_LILY = register("huge_water_lily", TFFeatures.HUGE_WATER_LILY.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> CICADA_LAMPPOST = register("cicada_lamppost", TFFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(TFBlocks.CICADA_JAR.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> FIREFLY_LAMPPOST = register("firefly_lamppost", TFFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(TFBlocks.FIREFLY_JAR.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> MONOLITH = register("monolith", TFFeatures.MONOLITH.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MUSHGLOOM_CLUSTER = register("mushgloom_cluster", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TFBlocks.MUSHGLOOM.get()))));
	public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> MYCELIUM_BLOB = register("mycelium_blob", TFFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.MYCELIUM), BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK), UniformInt.of(4, 6), 3));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> OUTSIDE_STALAGMITE = register("outside_stalagmite", TFFeatures.CAVE_STALACTITE.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> PLANT_ROOTS = register("plant_roots", TFFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(TFBlocks.ROOT_STRAND.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> PUMPKIN_LAMPPOST = register("pumpkin_lamppost", TFFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(Blocks.JACK_O_LANTERN.defaultBlockState()));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> SMOKER = register("smoker", TFFeatures.FIRE_JET.get(), new BlockStateConfiguration(TFBlocks.SMOKER.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> STONE_CIRCLE = register("stone_circle", TFFeatures.STONE_CIRCLE.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<ThornsConfig, ?>> THORNS = register("thorns", TFFeatures.THORNS.get(), new ThornsConfig(7, 3, 3, 50));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TORCH_BERRIES = register("torch_berries", TFFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(TFBlocks.TORCHBERRY_PLANT.get().defaultBlockState().setValue(TorchberryPlantBlock.HAS_BERRIES, true)));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TROLL_ROOTS = register("troll_roots", TFFeatures.TROLL_VINES.get(), new BlockStateConfiguration(TFBlocks.TROLLVIDR.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> VANILLA_ROOTS = register("vanilla_roots", TFFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(Blocks.HANGING_ROOTS.defaultBlockState()));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> WEBS = register("webs", TFFeatures.WEBS.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<RootConfig, ?>> WOOD_ROOTS_SPREAD = register("ore/wood_roots_spread", TFFeatures.WOOD_ROOTS.get(), new RootConfig(TreeDecorators.ROOT_BLEND_PROVIDER, BlockStateProvider.simple(TFBlocks.LIVEROOT_BLOCK.get())));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> SNOW_UNDER_TREES = register("snow_under_trees", TFFeatures.SNOW_UNDER_TREES.get(), FeatureConfiguration.NONE);

	//fallen logs!
	public static final Holder<ConfiguredFeature<HollowLogConfig, ?>> TF_OAK_FALLEN_LOG = register("tf_oak_fallen_log", TFFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(TFBlocks.TWILIGHT_OAK_LOG.get().defaultBlockState(), TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<HollowLogConfig, ?>> CANOPY_FALLEN_LOG = register("canopy_fallen_log", TFFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(TFBlocks.CANOPY_LOG.get().defaultBlockState(), TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<HollowLogConfig, ?>> MANGROVE_FALLEN_LOG = register("mangrove_fallen_log", TFFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(TFBlocks.MANGROVE_LOG.get().defaultBlockState(), TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<HollowLogConfig, ?>> OAK_FALLEN_LOG = register("oak_fallen_log", TFFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(Blocks.OAK_LOG.defaultBlockState(), TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<HollowLogConfig, ?>> SPRUCE_FALLEN_LOG = register("spruce_fallen_log", TFFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(Blocks.SPRUCE_LOG.defaultBlockState(), TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL.get().defaultBlockState()));
	public static final Holder<ConfiguredFeature<HollowLogConfig, ?>> BIRCH_FALLEN_LOG = register("birch_fallen_log", TFFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(Blocks.BIRCH_LOG.defaultBlockState(), TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL.get().defaultBlockState()));

	//smol stone veins
	public static final Holder<ConfiguredFeature<OreConfiguration,?>> SMALL_GRANITE = register("small_granite", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.GRANITE.defaultBlockState(), 16));
	public static final Holder<ConfiguredFeature<OreConfiguration,?>> SMALL_DIORITE = register("small_diorite", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.DIORITE.defaultBlockState(), 16));
	public static final Holder<ConfiguredFeature<OreConfiguration,?>> SMALL_ANDESITE = register("small_andesite", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.ANDESITE.defaultBlockState(), 16));

	//Ores! Lets keep pre 1.18 ore rates :)
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> LEGACY_COAL_ORE = register("legacy_coal_ore", Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.COAL_ORE.defaultBlockState(), 17));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> LEGACY_IRON_ORE = register("legacy_iron_ore", Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.IRON_ORE.defaultBlockState(), 9));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> LEGACY_GOLD_ORE = register("legacy_gold_ore", Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.GOLD_ORE.defaultBlockState(), 9));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> LEGACY_REDSTONE_ORE = register("legacy_redstone_ore", Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.REDSTONE_ORE.defaultBlockState(), 8));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> LEGACY_DIAMOND_ORE = register("legacy_diamond_ore", Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.DIAMOND_ORE.defaultBlockState(), 8));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> LEGACY_LAPIS_ORE = register("legacy_lapis_ore", Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.LAPIS_ORE.defaultBlockState(), 7));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> LEGACY_COPPER_ORE = register("legacy_copper_ore", Feature.ORE, new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.COPPER_ORE.defaultBlockState(), 10));

	//Dark Forest needs special placements, so here we go
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_MUSHGLOOMS = register("dark_mushglooms", TFFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TFBlocks.MUSHGLOOM.get())), List.of(Blocks.GRASS_BLOCK), 50));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_PUMPKINS = register("dark_pumpkins", TFFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PUMPKIN)), List.of(Blocks.GRASS_BLOCK), 50));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_GRASS = register("dark_grass", TFFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.GRASS)), List.of(Blocks.GRASS_BLOCK), 128));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_FERNS = register("dark_ferns", TFFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FERN)), List.of(Blocks.GRASS_BLOCK), 128));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_MUSHROOMS = register("dark_mushrooms", TFFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BROWN_MUSHROOM)), List.of(Blocks.GRASS_BLOCK), 50));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_DEAD_BUSHES = register("dark_dead_bushes", TFFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.DEAD_BUSH)), List.of(Blocks.GRASS_BLOCK), 50));

	//troll caves special stuff
	public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> UBEROUS_SOIL_PATCH_BIG = register("uberous_soil_patch_big", TFFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(RuleBasedBlockStateProvider.simple(TFBlocks.UBEROUS_SOIL.get()), BlockPredicate.matchesBlocks(Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT), UniformInt.of(4, 8), 1));
	public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> TROLL_CAVE_MYCELIUM =  register("troll_cave_mycelium", TFFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.MYCELIUM), BlockPredicate.matchesBlocks(Blocks.STONE, TFBlocks.DEADROCK.get()), UniformInt.of(3, 5), 0));
	public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> TROLL_CAVE_DIRT = register("troll_cave_dirt", TFFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.DIRT), BlockPredicate.matchesBlocks(Blocks.STONE, TFBlocks.DEADROCK.get()), UniformInt.of(2, 5), 0));
	public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> UBEROUS_SOIL_PATCH_SMALL = register("uberous_soil_patch_small", TFFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(RuleBasedBlockStateProvider.simple(TFBlocks.UBEROUS_SOIL.get()), BlockPredicate.matchesBlocks(Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT), UniformInt.of(2, 3), 0));

	//Trees!
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> TWILIGHT_OAK_TREE = register("tree/twilight_oak_tree", Feature.TREE, TreeConfigurations.TWILIGHT_OAK);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> LARGE_TWILIGHT_OAK_TREE = register("tree/large_twilight_oak_tree", Feature.TREE, TreeConfigurations.LARGE_TWILIGHT_OAK);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SWAMPY_OAK_TREE = register("tree/swampy_oak_tree", Feature.TREE, TreeConfigurations.SWAMPY_OAK);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CANOPY_TREE = register("tree/canopy_tree", Feature.TREE, TreeConfigurations.CANOPY_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> FIREFLY_CANOPY_TREE = register("tree/firefly_canopy_tree", Feature.TREE, TreeConfigurations.CANOPY_TREE_FIREFLY);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DEAD_CANOPY_TREE = register("tree/dead_canopy_tree", Feature.TREE, TreeConfigurations.CANOPY_TREE_DEAD);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MANGROVE_TREE = register("tree/mangrove_tree", Feature.TREE, TreeConfigurations.MANGROVE_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DARKWOOD_TREE = register("tree/darkwood_tree", TFFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.DARKWOOD_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> HOMEGROWN_DARKWOOD_TREE = register("tree/homegrown_darkwood_tree", TFFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.HOMEGROWN_DARKWOOD_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DARKWOOD_LANTERN_TREE = register("tree/darkwood_lantern_tree", TFFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.DARKWOOD_LANTERN_TREE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, ?>> TIME_TREE = register("tree/time_tree", TFFeatures.TREE_OF_TIME.get(), TreeConfigurations.TIME_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> TRANSFORMATION_TREE = register("tree/transformation_tree", Feature.TREE, TreeConfigurations.TRANSFORM_TREE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, ?>> MINING_TREE = register("tree/mining_tree", TFFeatures.MINERS_TREE.get(), TreeConfigurations.MINING_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SORTING_TREE = register("tree/sorting_tree", Feature.TREE, TreeConfigurations.SORT_TREE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, ?>> FOREST_CANOPY_OAK_TREE = register("tree/forest_canopy_oak_tree", TFFeatures.CANOPY_OAK.get(), TreeConfigurations.FOREST_CANOPY_OAK);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, ?>> SAVANNAH_CANOPY_OAK_TREE = register("tree/savannah_canopy_oak_tree", TFFeatures.CANOPY_OAK.get(), TreeConfigurations.SAVANNAH_CANOPY_OAK);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, ?>> HOLLOW_TREE = register("tree/hollow_tree", TFFeatures.HOLLOW_TREE.get(), TreeConfigurations.HOLLOW_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RAINBOW_OAK_TREE = register("tree/rainbow_oak", Feature.TREE, TreeConfigurations.RAINBOAK_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> LARGE_RAINBOW_OAK_TREE = register("tree/large_rainbow_oak", Feature.TREE, TreeConfigurations.LARGE_RAINBOAK_TREE);
	public static final Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BROWN_CANOPY_MUSHROOM_TREE = register("mushroom/brown_canopy_mushroom", TFFeatures.CANOPY_BROWN_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.TRUE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.FALSE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), 3));
	public static final Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> RED_CANOPY_MUSHROOM_TREE = register("mushroom/red_canopy_mushroom", TFFeatures.CANOPY_RED_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(Blocks.RED_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.TRUE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.FALSE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), 3));
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MEGA_SPRUCE_TREE = register("tree/mega_spruce_tree", TFFeatures.SNOW_TREE.get(), TreeConfigurations.BIG_SPRUCE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, ?>> LARGE_WINTER_TREE = register("tree/large_winter_tree", TFFeatures.LARGE_WINTER_TREE.get(), TreeConfigurations.LARGE_WINTER);
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SNOWY_SPRUCE_TREE = register("tree/snowy_spruce_tree", TFFeatures.SNOW_TREE.get(), TreeFeatures.SPRUCE.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DARK_FOREST_OAK_TREE = register("tree/dark_forest_oak_tree", TFFeatures.DARK_CANOPY_TREE.get(), TreeFeatures.OAK.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DARK_FOREST_BIRCH_TREE = register("tree/dark_forest_birch_tree", TFFeatures.DARK_CANOPY_TREE.get(), TreeFeatures.BIRCH.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> VANILLA_OAK_TREE = register("tree/vanilla_oak_tree", Feature.TREE, TreeFeatures.OAK.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> VANILLA_BIRCH_TREE = register("tree/vanilla_birch_tree", Feature.TREE, TreeFeatures.BIRCH.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SMALLER_JUNGLE_TREE = register("tree/smaller_jungle_tree", Feature.TREE, TreeConfigurations.SMALL_JUNGLE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> DUMMY_TREE = register("tree/dummy", Feature.NO_OP, NoneFeatureConfiguration.INSTANCE);

	//random selectors
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CANOPY_TREES = register("tree/selector/canopy_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(CANOPY_TREE), 0.6F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> DENSE_CANOPY_TREES = register("tree/selector/dense_canopy_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(CANOPY_TREE), 0.7F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FIREFLY_FOREST_TREES = register("tree/selector/firefly_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(CANOPY_TREE), 0.33F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FIREFLY_CANOPY_TREE), 0.45F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_FOREST_TREES = register("tree/selector/dark_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(DARK_FOREST_BIRCH_TREE), 0.2F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(DARK_FOREST_OAK_TREE), 0.2F)), PlacementUtils.inlinePlaced(DARKWOOD_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> HIGHLANDS_TREES = register("tree/selector/highlands_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.SPRUCE), 0.25F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.PINE), 0.1F)), PlacementUtils.inlinePlaced(MEGA_SPRUCE_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> ENCHANTED_FOREST_TREES = register("tree/selector/enchanted_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_OAK_TREE), 0.15F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_BIRCH_TREE), 0.15F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(LARGE_RAINBOW_OAK_TREE), 0.1F)), PlacementUtils.inlinePlaced(RAINBOW_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SNOWY_FOREST_TREES = register("tree/selector/snowy_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(MEGA_SPRUCE_TREE), 0.1F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(LARGE_WINTER_TREE), 0.01F)), PlacementUtils.inlinePlaced(SNOWY_SPRUCE_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> VANILLA_TF_TREES = register("tree/selector/vanilla_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_BIRCH_TREE), 0.25F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_OAK_TREE), 0.25F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> VANILLA_TF_BIG_MUSH = register("tree/selector/vanilla/vanilla_mushrooms", Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM)));

	//super funky tree placement lists
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CANOPY_MUSHROOMS_SPARSE =
			register("mushroom/canopy_mushrooms_sparse", 
					Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(BROWN_CANOPY_MUSHROOM_TREE), 0.15f),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_CANOPY_MUSHROOM_TREE), 0.05f)),
							PlacementUtils.inlinePlaced(DUMMY_TREE)));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CANOPY_MUSHROOMS_DENSE =
			register("mushroom/canopy_mushrooms_dense", 
					Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(BROWN_CANOPY_MUSHROOM_TREE), 0.675f),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_CANOPY_MUSHROOM_TREE), 0.225f)),
							PlacementUtils.inlinePlaced(DUMMY_TREE)));

	//ground decoration
	public static final RandomPatchConfiguration SMALL_FLOWER_CONFIG = (new RandomPatchConfiguration(32, 7, 7,
			PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
					new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
							Blocks.POPPY.defaultBlockState(),
							Blocks.DANDELION.defaultBlockState(),
							Blocks.RED_TULIP.defaultBlockState(),
							Blocks.ORANGE_TULIP.defaultBlockState(),
							Blocks.PINK_TULIP.defaultBlockState(),
							Blocks.WHITE_TULIP.defaultBlockState(),
							Blocks.CORNFLOWER.defaultBlockState(),
							Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
							Blocks.BLUE_ORCHID.defaultBlockState(),
							Blocks.ALLIUM.defaultBlockState(),
							Blocks.AZURE_BLUET.defaultBlockState(),
							Blocks.OXEYE_DAISY.defaultBlockState())
					)), BlockPredicate.ONLY_IN_AIR_PREDICATE)));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PLACER = register("flower_placer", Feature.FLOWER, SMALL_FLOWER_CONFIG);

	public static final RandomPatchConfiguration SMALL_FLOWER_CONFIG_ALT = (new RandomPatchConfiguration(32, 7, 7,
			PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
					new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
							Blocks.WHITE_TULIP.defaultBlockState(),
							Blocks.PINK_TULIP.defaultBlockState(),
							Blocks.ORANGE_TULIP.defaultBlockState(),
							Blocks.RED_TULIP.defaultBlockState(),
							Blocks.DANDELION.defaultBlockState(),
							Blocks.POPPY.defaultBlockState(),
							Blocks.OXEYE_DAISY.defaultBlockState(),
							Blocks.AZURE_BLUET.defaultBlockState(),
							Blocks.ALLIUM.defaultBlockState(),
							Blocks.BLUE_ORCHID.defaultBlockState(),
							Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
							Blocks.CORNFLOWER.defaultBlockState())
					)), BlockPredicate.ONLY_IN_AIR_PREDICATE)));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PLACER_ALT = register("flower_placer_alt", Feature.FLOWER, SMALL_FLOWER_CONFIG_ALT);


	//music!
	public static final Music TFMUSICTYPE = new Music(TFSounds.MUSIC.get(), 1200, 12000, true);

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, TwilightForestMod.prefix(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
	}
}