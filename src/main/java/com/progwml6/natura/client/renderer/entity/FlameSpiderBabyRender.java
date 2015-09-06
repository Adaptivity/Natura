package com.progwml6.natura.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.progwml6.natura.common.world.entites.BabyHeatscarSpider;

public class FlameSpiderBabyRender extends RenderSpider
{

    public FlameSpiderBabyRender(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return texture;
    }

    protected void scaleSpider(BabyHeatscarSpider par1EntityCaveSpider, float par2)
    {
        GL11.glScalef(0.85f, 0.85f, 0.85f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.scaleSpider((BabyHeatscarSpider) par1EntityLivingBase, par2);
    }

    static final ResourceLocation texture = new ResourceLocation("natura", "textures/mob/flamespider.png");

}
