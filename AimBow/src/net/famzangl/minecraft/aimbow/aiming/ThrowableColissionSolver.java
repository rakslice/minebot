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

public class ThrowableColissionSolver extends ColissionSolver{

	public ThrowableColissionSolver(Minecraft mc, EntityLivingBase renderViewEntity) {
		super(mc, renderViewEntity);
	}

	@Override
	protected RayTraceResult computeHit(RayData s, int tick) {
        Vec3d Vec3d = new Vec3d(s.posX, s.posY, s.posZ);
        Vec3d Vec3d1 = new Vec3d(s.posX + s.motionX, s.posY + s.motionY, s.posZ + s.motionZ);
        RayTraceResult RayTraceResult = minecraft.theWorld.rayTraceBlocks(Vec3d, Vec3d1);
        Vec3d = new Vec3d(s.posX, s.posY, s.posZ);
        Vec3d1 = new Vec3d(s.posX + s.motionX, s.posY + s.motionY, s.posZ + s.motionZ);

        if (RayTraceResult != null)
        {
            Vec3d1 = new Vec3d(RayTraceResult.hitVec.xCoord, RayTraceResult.hitVec.yCoord, RayTraceResult.hitVec.zCoord);
        }

        Entity entity = null;
        List<Entity> list = minecraft.theWorld.getEntitiesWithinAABB(Entity.class, s.boundingBox.addCoord(s.motionX, s.motionY, s.motionZ).expand(1.0D, 1.0D, 1.0D));
        double d0 = 0.0D;
        EntityLivingBase entitylivingbase = this.shootingEntity;

        for (int j = 0; j < list.size(); ++j)
        {
            Entity entity1 = (Entity)list.get(j);

            if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase || tick >= 5))
            {
                float f = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f, (double)f, (double)f);
                RayTraceResult RayTraceResult1 = axisalignedbb.calculateIntercept(Vec3d, Vec3d1);

                if (RayTraceResult1 != null)
                {
                    double d1 = Vec3d.distanceTo(RayTraceResult1.hitVec);

                    if (d1 < d0 || d0 == 0.0D)
                    {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null)
        {
            return new RayTraceResult(entity);
        } else {
        	return RayTraceResult;
        }
	}
	
	@Override
	public float getVelocity() {
		return 1.5f;
	}
	
	@Override
	protected RayData generateRayData() {
		return new ThrowableRayData();
	}

}
