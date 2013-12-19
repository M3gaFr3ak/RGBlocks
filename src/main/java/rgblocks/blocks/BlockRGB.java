package rgblocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import rgblocks.RGBlocks;
import rgblocks.tiles.TileEntityRGB;

public class BlockRGB extends Block implements ITileEntityProvider
{

	public BlockRGB(int id)
	{
		super(id, Material.rock);
		setCreativeTab(RGBlocks.instance.ModTab);
		setUnlocalizedName("rgblocks.blockrgb");
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityRGB();
	}

	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity blockTE = world.getBlockTileEntity(x, y, z);
		if (blockTE instanceof TileEntityRGB)
			return ((TileEntityRGB) blockTE).getIcon(ForgeDirection.getOrientation(side), world.getBlockMetadata(x, y, z));
		return Block.stone.getIcon(side, world.getBlockMetadata(x, y, z));
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return RGBlocks.instance.renderHandlerID;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ)
	{
		ItemStack currentItem = player.inventory.getCurrentItem();
		TileEntity blockTE = world.getBlockTileEntity(x, y, z);
		if (currentItem == null || !(blockTE instanceof TileEntityRGB))
			return false;
		if (currentItem.getItem() instanceof ItemBlock)
		{
			ItemBlock item = (ItemBlock) currentItem.getItem();
			return ((TileEntityRGB) blockTE).tryToSetBlock(item.getBlockID(), currentItem.getItemDamage());
		} else
		{
			return false;
		}
	}

	public float getBlockBrightness(IBlockAccess world, int x, int y, int z)
	{
		return world.getBrightness(x, y, z, getLightValue(world, x, y, z));
	}

	public int getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z)
	{
		return world.getLightBrightnessForSkyBlocks(x, y, z, getLightValue(world, x, y, z));
	}

	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		if (!(world.getBlockTileEntity(x, y, z) instanceof TileEntityRGB))
			return 0;
		TileEntityRGB te = (TileEntityRGB) world.getBlockTileEntity(x, y, z);
		return te.getColor().getRGB();
	}
}