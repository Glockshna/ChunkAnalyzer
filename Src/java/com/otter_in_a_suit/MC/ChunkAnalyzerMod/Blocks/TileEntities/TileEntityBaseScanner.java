package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities;

/**
 * Probably the worst implementation of a TileEntity since pre-alpha-pre-Notch's-birth
 */


import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBaseScanner extends TileEntity implements IInventory {
	private ItemStack[] inventory;

	public TileEntityBaseScanner() {
		inventory = new ItemStack[1];
	}

	public int explosionThreshold = 50;
	public int searchFor_ID;// = Block.getIdFromBlock(Blocks.iron_ore);
	boolean debug = ChunkAnalyzerMod.scannerDebug;

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("explosionThreshold", explosionThreshold);
		if(debug)
			System.out.println("writeToNBT called");
		
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack != null) {
				if (debug)
					System.out.println(itemstack);

				compound.setInteger("searchFor_ID", searchFor_ID);
			} else {
				if (debug)
					System.out.println("itemstack null");
				// compound.setInteger("searchFor_ID",
				// Block.getIdFromBlock(Blocks.iron_ore));
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.explosionThreshold = compound.getInteger("explosionThreshold");
		this.searchFor_ID = compound.getInteger("searchFor_ID");
		if(debug)
			System.out.println("readFromNBT " + searchFor_ID);
		Item i = Item.getItemById(searchFor_ID);
		if (i != null) {
			setInventorySlotContents(0, new ItemStack(i, 1));
		}
	}

	public void setExplosionThreshold(int x) {
		this.explosionThreshold = x;
	}

	public void setSearchForBlock(Block b) {
		this.searchFor_ID = Block.getIdFromBlock(b);
	}

	public NBTTagCompound getNBTTagCompound() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("explosionThreshold", explosionThreshold);
		nbt.setInteger("searchFor_ID", searchFor_ID);
		return nbt;
	}

	/**
	 * Write values from NBT to this bit of an awkward solution, but since we
	 * ain't got no pointers making this more flexible is a pain, so I wrote it
	 * static for the time being
	 * 
	 * @param par1
	 *            : NBTTagCompound
	 */
	public void writeToParByNBT(NBTTagCompound par1) {
		this.explosionThreshold = (par1.hasKey("explosionThreshold")) ? par1
				.getInteger("explosionThreshold") : explosionThreshold;
		this.searchFor_ID = (par1.hasKey("searchFor_ID")) ? par1
				.getInteger("searchFor_ID") : searchFor_ID;
	}

	/**
	 * CUSTOM GUI
	 */
	public int getSizeInventory() {
		return inventory.length;
	}

	public ItemStack getStackInSlot(int var1) {
		return inventory[var1];
	}

	public ItemStack decrStackSize(int slot, int count) {
		ItemStack itemstack = getStackInSlot(slot);

		if (itemstack != null) {
			if (itemstack.stackSize <= count) {
				setInventorySlotContents(slot, null);
			} else {
				itemstack = itemstack.splitStack(count);
				markDirty();
			}
		}
		return itemstack;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack itemstack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return itemstack;
	}

	/**
	 * 
	 * @return Blocks.air = null
	 */
	public Block getStoredBlock() {
		Block b = Block.getBlockById(this.searchFor_ID);
		if (b == null || b == Blocks.air)
			return Blocks.air;
		else
			return b;
	}

	/*
	 * Called every time something changes w/in the inventory?
	 * 
	 * @see net.minecraft.inventory.IInventory#setInventorySlotContents(int,
	 * net.minecraft.item.ItemStack)
	 */
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (slot == 0 && itemstack != null) {
			Block b = Block.getBlockFromItem(itemstack.getItem());
			if (b != null) {
				this.searchFor_ID = Block.getIdFromBlock(b);
			}
		}
		inventory[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	public String getInventoryName() {
		return "Scanner";
	}

	public boolean hasCustomInventoryName() {
		return false;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return player
				.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	public void openInventory() {
		//
	}

	public void closeInventory() {
		//
	}

	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

}
