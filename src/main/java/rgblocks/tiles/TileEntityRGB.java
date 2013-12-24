package rgblocks.tiles;

import java.awt.Color;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import rgblocks.blocks.BlockRGB;
import rgblocks.blocks.BlockRGBStairs;

public class TileEntityRGB extends TileEntity
{
	Block block;
	int meta = 0;
	Color color = new Color(255, 255, 255);

	public Icon getIcon(ForgeDirection side, int direction)
	{
		if (block == null)
			return Block.stone.getBlockTextureFromSide(side.ordinal());
		return block.getIcon(side.ordinal(), meta);
	}

	public boolean tryToSetBlock(int blockID, int meta)
	{
		if (this.meta == meta && block == Block.blocksList[blockID])
			return false;
		if (Block.blocksList[blockID] instanceof BlockRGB || Block.blocksList[blockID] instanceof BlockRGBStairs)
			return false;
		this.block = Block.blocksList[blockID];
		this.meta = meta;
		PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		return true;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color, boolean sendPackets)
	{
		if (color != null)
		{
			this.color = color;
			if (sendPackets)
			{
				PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
				worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	public int clamp(int i, int min, int max)
	{
		if (i >= max)
			return max;
		if (i <= min)
			return min;
		return i;
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		int red = clamp(nbt.getInteger("clrRed"), 0, 255);
		int green = clamp(nbt.getInteger("clrGreen"), 0, 255);
		int blue = clamp(nbt.getInteger("clrBlue"), 0, 255);
		setColor(new Color(red, green, blue), false);
		block = Block.blocksList[nbt.getInteger("blockID")];
		meta = nbt.getInteger("meta");
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("clrRed", color.getRed());
		nbt.setInteger("clrGreen", color.getGreen());
		nbt.setInteger("clrBlue", color.getBlue());
		nbt.setInteger("blockID", block.blockID);
		nbt.setInteger("meta", meta);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
	{
		super.onDataPacket(net, packet);
		readFromNBT(packet.data);
	}
}
