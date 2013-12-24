package rgblocks.items;

import java.awt.Color;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import rgblocks.RGBlocks;
import rgblocks.tiles.TileEntityRGB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
			player.sendChatToPlayer(new ChatMessageComponent().addText("use the command \"changeColor <color as hex>\" to set the Color!"));
		} else
		{
			if (world.getBlockTileEntity(x, y, z) instanceof TileEntityRGB)
			{
				((TileEntityRGB) world.getBlockTileEntity(x, y, z)).setColor(getColor(itemstack), true);
			}
		}
		return false;
	}

	public static Color getColor(ItemStack itemstack)
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
			if (nbt.hasKey("clrBlue"))
			{
				blue = nbt.getInteger("clrBlue");
			} else
			{
				blue = 255;
			}
			return new Color(red, green, blue);
		}
	}

	public static void setColor(ItemStack itemstack, Color color)
	{
		if (!itemstack.hasTagCompound())
			itemstack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (color != null)
		{
			nbt.setInteger("clrRed", color.getRed());
			nbt.setInteger("clrGreen", color.getGreen());
			nbt.setInteger("clrBlue", color.getBlue());
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("rgblocks:colorchanger");
	}

	public Icon getIconFromDamage(int dmg)
	{
		return itemIcon;
	}

	public String getStatName()
	{
		return "Test!";
	}
}
