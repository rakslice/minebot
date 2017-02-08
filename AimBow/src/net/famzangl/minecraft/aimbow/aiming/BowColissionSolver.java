/*******************************************************************************
 * This file is part of Minebot.
 *
 * Minebot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Minebot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minebot.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package net.famzangl.minecraft.aimbow.aiming;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * This is an incremental colission solver.
 * <p>
 * It can simulate multiple entities and detect which one is hit.
 * <p>
 * It uses a list of rays.
 * 
 * @author michael
 *
 */
public class BowColissionSolver extends ColissionSolver {
	public BowColissionSolver(Minecraft mc, EntityLivingBase renderViewEntity) {
		super(mc, renderViewEntity);
	}
	
	protected RayTraceResult computeHit(RayData s, int tick) {
		Vec3d Vec3d1 = new Vec3d(s.prevPosX, s.prevPosY, s.prevPosZ);
		Vec3d Vec3d = new Vec3d(s.posX, s.posY, s.posZ);
		RayTraceResult hit = minecraft.theWorld.rayTraceBlocks(Vec3d1,
				Vec3d, false, true, false);

		Vec3d1 = new Vec3d(s.prevPosX, s.prevPosY, s.prevPosZ);
		if (hit == null) {
			Vec3d = new Vec3d(s.posX, s.posY, s.posZ);
		} else {
			Vec3d = new Vec3d(hit.hitVec.xCoord, hit.hitVec.yCoord,
					hit.hitVec.zCoord);
		}

		double d0 = 0.0D;
		AxisAlignedBB bbox = s.boundingBox.addCoord(s.motionX, s.motionY,
				s.motionZ).expand(1.0D, 1.0D, 1.0D);
		List<Entity> entities = minecraft.theWorld.getEntitiesWithinAABB(
				Entity.class, bbox);
		// System.out.println("BBox: " + bbox);
		for (Entity e : entities) {
			if (e.canBeCollidedWith()
					&& (e != this.shootingEntity || tick >= 5)) {
				float f1 = 0.3F;
				AxisAlignedBB axisalignedbb1 = e.getEntityBoundingBox().expand(
						(double) f1, (double) f1, (double) f1);
				RayTraceResult RayTraceResult1 = axisalignedbb1
						.calculateIntercept(Vec3d1, Vec3d);

				if (RayTraceResult1 != null) {
					double d1 = Vec3d1.distanceTo(RayTraceResult1.hitVec);

					if (d1 < d0 || d0 == 0.0D) {
						hit = RayTraceResult1;
						hit.entityHit = e;
						d0 = d1;
					}
				}
			}
		}
		return hit;
	}
	
	@Override
	public float getVelocity() {
		return 3;
	}
	
	@Override
	protected RayData generateRayData() {
		return new BowRayData(2);
	}
}
