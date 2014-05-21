package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * CURRENTLY UNUSED
 * 
 * @deprecated
 */
public class ModelMarker extends ModelBase {
  // fields
  ModelRenderer M1;
  ModelRenderer M2;
  ModelRenderer M3;
  ModelRenderer M4;
  ModelRenderer Q1;
  ModelRenderer Q2;

  public ModelMarker() {
    textureWidth = 64;
    textureHeight = 32;

    M1 = new ModelRenderer(this, 0, 16);
    M1.addBox(4F, 0F, -8F, 2, 12, 2);
    M1.setRotationPoint(2F, 12F, 0F);
    M1.setTextureSize(64, 32);
    M1.mirror = true;
    setRotation(M1, 0F, 0F, 0F);
    M2 = new ModelRenderer(this, 0, 16);
    M2.addBox(0F, 0F, 0F, 2, 12, 2);
    M2.setRotationPoint(-8F, 12F, 6F);
    M2.setTextureSize(64, 32);
    M2.mirror = true;
    setRotation(M2, 0F, 0F, 0F);
    M3 = new ModelRenderer(this, 0, 16);
    M3.addBox(0F, 0F, 0F, 2, 12, 2);
    M3.setRotationPoint(-8F, 12F, -8F);
    M3.setTextureSize(64, 32);
    M3.mirror = true;
    setRotation(M3, 0F, 0F, 0F);
    M4 = new ModelRenderer(this, 0, 16);
    M4.addBox(0F, 0F, 0F, 2, 12, 2);
    M4.setRotationPoint(6F, 12F, 6F);
    M4.setTextureSize(64, 32);
    M4.mirror = true;
    setRotation(M4, 0F, 0F, 0F);
    Q1 = new ModelRenderer(this, 0, 16);
    Q1.addBox(0F, 0F, 0F, 2, 12, 2);
    Q1.setRotationPoint(6F, 12F, 6F);
    Q1.setTextureSize(64, 32);
    Q1.mirror = true;
    setRotation(Q1, 0F, 0F, 1.570796F);
    Q2 = new ModelRenderer(this, 0, 16);
    Q2.addBox(0F, 0F, 0F, 2, 12, 2);
    Q2.setRotationPoint(6F, 12F, -8F);
    Q2.setTextureSize(64, 32);
    Q2.mirror = true;
    setRotation(Q2, 0F, 0F, 1.570796F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    M1.render(f5);
    M2.render(f5);
    M3.render(f5);
    M4.render(f5);
    Q1.render(f5);
    Q2.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
