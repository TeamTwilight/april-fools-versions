package twilightforest.loot.functions;

import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IRegistryDelegate;
import twilightforest.loot.TFTreasure;

import java.util.HashMap;
import java.util.Map;

// Similar to EnchantRandomly but applies everything and with exact levels
public class Enchant extends LootItemConditionalFunction {

	private final Map<IRegistryDelegate<Enchantment>, Short> enchantments;

	protected Enchant(LootItemCondition[] conditions, Map<IRegistryDelegate<Enchantment>, Short> enchantments) {
		super(conditions);
		this.enchantments = enchantments;
	}

	@Override
	public LootItemFunctionType getType() {
		return TFTreasure.ENCHANT;
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		for (Map.Entry<IRegistryDelegate<Enchantment>, Short> e : enchantments.entrySet()) {
			if (stack.getItem() == Items.ENCHANTED_BOOK) {
				EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(e.getKey().get(), e.getValue()));
			} else {
				stack.enchant(e.getKey().get(), e.getValue());
			}
		}
		return stack;
	}

	public static Enchant.Builder builder() {
		return new Enchant.Builder();
	}

	public static class Builder extends LootItemConditionalFunction.Builder<Enchant.Builder> {
		private final Map<IRegistryDelegate<Enchantment>, Short> enchants = Maps.newHashMap();

		protected Enchant.Builder getThis() {
			return this;
		}

		public Enchant.Builder apply(Enchantment p_216077_1_, Integer p_216077_2_) {
			this.enchants.put(p_216077_1_.delegate, p_216077_2_.shortValue());
			return this;
		}

		public LootItemFunction build() {
			return new Enchant(this.getConditions(), this.enchants);
		}
	}

	public static class Serializer extends LootItemConditionalFunction.Serializer<Enchant> {

		@Override
		public void serialize(JsonObject object, Enchant function, JsonSerializationContext ctx) {
			if (!function.enchantments.isEmpty()) {
				JsonObject obj = new JsonObject();

				for (Map.Entry<IRegistryDelegate<Enchantment>, Short> e : function.enchantments.entrySet()) {
					obj.addProperty(e.getKey().get().getRegistryName().toString(), e.getValue());
				}

				object.add("enchantments", obj);
			}
		}

		@Override
		public Enchant deserialize(JsonObject object, JsonDeserializationContext ctx, LootItemCondition[] conditions) {
			Map<IRegistryDelegate<Enchantment>, Short> enchantments = new HashMap<>();

			if (object.has("enchantments")) {
				JsonObject enchantObj = GsonHelper.getAsJsonObject(object, "enchantments");

				for (Map.Entry<String, JsonElement> e : enchantObj.entrySet()) {
					ResourceLocation id = new ResourceLocation(e.getKey());
					if (!ForgeRegistries.ENCHANTMENTS.containsKey(id)) {
						throw new JsonSyntaxException("Can't find enchantment " + e.getKey());
					}

					Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(id);
					short lvl = e.getValue().getAsShort();

					for (IRegistryDelegate<Enchantment> other : enchantments.keySet()) {
						if (!ench.isCompatibleWith(other.get())) {
							throw new JsonParseException(String.format("Enchantments %s and %s conflict", ench.getRegistryName(), other.get().getRegistryName()));
						}
					}

					enchantments.put(ench.delegate, lvl);
				}
			}

			return new Enchant(conditions, enchantments);
		}
	}
}
