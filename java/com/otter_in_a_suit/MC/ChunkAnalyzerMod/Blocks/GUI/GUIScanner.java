package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GUI;

import org.lwjgl.opengl.GL11;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIScanner extends GuiContainer {
  public static final ResourceLocation texture = new ResourceLocation(
      ChunkAnalyzerMod.MODID.toLowerCase(), "textures/gui/deployer.png");

  public GUIScanner(InventoryPlayer invPlayer, TileEntityBaseScanner entity) {
    super(new ContainerGUIScanner(invPlayer, entity));

    xSize = 176;
    ySize = 165;
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
    GL11.glColor4f(1F, 1F, 1F, 1F); // RGBA
    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }
}
