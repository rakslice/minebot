package net.famzangl.minecraft.minebot.ai.animals;

import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.task.AITask;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class FaceAndInteractTask implements AITask {

	private boolean interacted = false;
	private Entity preferedAnimal;
	private IEntitySelector alsoAcceptedAnimal;
	private boolean doRightClick;
	private int ticksRun = 0;

	public FaceAndInteractTask(Entity preferedAnimal,
			IEntitySelector alsoAcceptedAnimal) {
		this(preferedAnimal, alsoAcceptedAnimal, true);
	}

	public FaceAndInteractTask(Entity preferedAnimal,
			IEntitySelector alsoAcceptedAnimal, boolean doRightClick) {
		this.preferedAnimal = preferedAnimal;
		this.alsoAcceptedAnimal = alsoAcceptedAnimal;
		this.doRightClick = doRightClick;
	}

	@Override
	public boolean isFinished(AIHelper h) {
		boolean collect = preferedAnimal instanceof EntityItem
				|| preferedAnimal instanceof EntityXPOrb;
		return collect ? preferedAnimal.boundingBox.intersectsWith(h
				.getMinecraft().thePlayer.boundingBox) : interacted;
	}

	@Override
	public void runTick(AIHelper h) {
		MovingObjectPosition over = h.getObjectMouseOver();
		if (over != null && over.typeOfHit == MovingObjectType.ENTITY
				&& alsoAcceptedAnimal.isEntityApplicable(over.entityHit)) {
			if (doRightClick)
				h.overrideUseItem();
			else
				h.overrideAttack();
			interacted = true;
		} else {
			double speed = h.getMinecraft().thePlayer.motionX
					* h.getMinecraft().thePlayer.motionX
					+ h.getMinecraft().thePlayer.motionZ
					* h.getMinecraft().thePlayer.motionZ;
			h.face(preferedAnimal.posX, preferedAnimal.posY,
					preferedAnimal.posZ);
			MovementInput i = new MovementInput();
			i.jump = speed < 0.01 && ticksRun > 8;
			i.moveForward = 1;
			h.overrideMovement(i);
		}
		ticksRun++;
	}
}