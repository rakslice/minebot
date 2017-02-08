package net.famzangl.minecraft.minebot.ai.tools.rate;

import net.famzangl.minecraft.minebot.ai.path.world.BlockFloatMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class EnchantmentRater extends Rater {
	protected final Enchantment enchantmentId;

	public EnchantmentRater(Enchantment enchantmentId, String name,
			BlockFloatMap values) {
		super(name, values);
		this.enchantmentId = enchantmentId;
	}
	
	@Override
	protected boolean isAppleciable(ItemStack item, int forBlockAndMeta, IBlockState forBlockState) {
		return EnchantmentHelper.getEnchantmentLevel(enchantmentId,
				item) > 0;
	}

	@Override
	public String toString() {
		return "EnchantmentRater [enchantmentId=" + enchantmentId + ", name="
				+ name + "]";
	}
}