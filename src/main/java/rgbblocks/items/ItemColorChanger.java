package rgbblocks.items;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import rgbblocks.RGBlocks;
import rgblocks.tiles.TileEntityRGB;

public class ItemColorChanger extends Item
{

	public ItemColorChanger(int id)
	{
		super(id);
		setCreativeTab(RGBlocks.instance.ModTab);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if (player.isSneaking())
		{
			player.sendChatToPlayer(new ChatMessageComponent().addText("use the command \"setColor <color as hex>\""));
		} else
		{
			if (world.getBlockTileEntity(x, y, z) instanceof TileEntityRGB)
			{
				((TileEntityRGB) world.getBlockTileEntity(x, y, z)).setColor(getColor(itemstack));
			}
		}
		return false;
	}

	public Color getColor(ItemStack itemstack)
	{
		if (!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
			return null;
		} else
		{
			int red, blue, green;
			NBTTagCompound nbt = itemstack.getTagCompound();
			if (nbt.hasKey("clrRed"))
			{
				red = nbt.getInteger("clrRed");
			} else
			{
				red = 255;
			}
			if (nbt.hasKey("clrGreen"))
			{
				green = nbt.getInteger("clrGreen");
			} else
			{
				green = 255;
			}
			if (nbt.hasKey("clrGreen"))
			{
				blue = nbt.getInteger("clrBlue");
			} else
			{
				blue = 255;
			}
			return new Color(red, green, blue);
		}
	}
}
