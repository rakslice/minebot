package net.famzangl.minecraft.minebot.ai.tools.rate;

import net.famzangl.minecraft.minebot.ai.path.world.BlockFloatMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class MatchesRater extends Rater {
	public MatchesRater(String name, BlockFloatMap values) {
		super(name, values);
	}

	@Override
	protected boolean isAppleciable(ItemStack item, int forBlockAndMeta, IBlockState forBlockState) {
		return item != null
				&& forBlockAndMeta >= 0
				&& item.getItem() != null
				&& item.getItem().getStrVsBlock(item, forBlockState) > 1;
	}

	@Override
	public String toString() {
		return "MatchesRater [name=" + name + "]";
	}
}