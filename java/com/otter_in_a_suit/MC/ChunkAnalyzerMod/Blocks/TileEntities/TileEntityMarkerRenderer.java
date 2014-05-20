package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities;

import org.lwjgl.opengl.GL11;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Models.ModelMarker;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Thanks to http://www.minecraftforge.net/wiki/Custom_Tile_Entity_Renderer I have no clue what's
 * going on here But sure looks fancy
 */
/**
 * CURRENTLY UNUSED
 * @deprecated
 */
public class TileEntityMarkerRenderer extends TileEntitySpecialRenderer {
  private ModelMarker model;

  public TileEntityMarkerRenderer() {
    this.model = new ModelMarker();
  }

  // This method is called when minecraft renders a tile entity
  @Override
  public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f) {
    GL11.glPushMatrix();
    // This will move our renderer so that it will be on proper place in the world
    GL11.glTranslatef((float) d, (float) d1, (float) d2);
    TileEntityMarker tileEntityYour = (TileEntityMarker) tileEntity;
    /*
     * Note that true tile entity coordinates (tileEntity.xCoord, etc) do not match to render
     * coordinates (d, etc) that are calculated as [true coordinates] - [player coordinates (camera
     * coordinates)]
     */
    renderMarker(tileEntityYour, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord,
        tileEntity.zCoord, ChunkAnalyzerMod.markerTorch);
    GL11.glPopMatrix();
  }

  // And this method actually renders your tile entity
  public void renderMarker(TileEntityMarker tl, World world, int i, int j, int k, Block block) {
    Tessellator tessellator = Tessellator.instance;
    // This will make your block brightness dependent from surroundings lighting.
    // float f = block.getBlockBrightness(world, i, j, k); // not workin in 1.7.2
    float f = 1;
    int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
    int l1 = l % 65536;
    int l2 = l / 65536;
    tessellator.setColorOpaque_F(f, f, f);
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);

    /*
     * This will rotate your model corresponding to player direction that was when you placed the
     * block. If you want this to work, add these lines to onBlockPlacedBy method in your block
     * class. int dir = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) &
     * 3; world.setBlockMetadataWithNotify(x, y, z, dir, 0);
     */

    GL11.glPushMatrix();
    GL11.glTranslated(i + 0.5, j + 1, k + 0.5);
    GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
    GL11.glRotatef(180, 0, 0, 1);

    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ChunkAnalyzerMod.MODID + "textures/blocks/Marker.png"));
    /*
     * Place your rendering code here.
     */

    this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

    GL11.glPopMatrix();
  }
}
