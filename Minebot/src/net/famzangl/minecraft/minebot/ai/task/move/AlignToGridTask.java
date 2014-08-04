package net.famzangl.minecraft.minebot.ai.task.move;

import net.famzangl.minecraft.minebot.Pos;
import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.task.AITask;

/**
 * Ensures that the player is standing on a block.
 * @author michael
 *
 */
public class AlignToGridTask extends AITask {
	private final int x;
	private final int y;
	private final int z;

	public AlignToGridTask(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public AlignToGridTask(Pos p) {
		this(p.x, p.y, p.z);
	}

	@Override
	public boolean isFinished(AIHelper h) {
		return h.isStandingOn(x, y, z);
	}

	@Override
	public void runTick(AIHelper h) {
		h.walkTowards(x + 0.5, z + 0.5, false);
	}

	@Override
	public String toString() {
		return "AlignToGridTask [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
