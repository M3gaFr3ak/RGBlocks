package rgblocks.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import rgblocks.blocks.BlockRGB;
import rgblocks.blocks.BlockRGBStairs;
import rgblocks.tiles.TileEntityRGB;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderHandler implements ISimpleBlockRenderingHandler
{
	private int renderID;

	public RenderHandler(int id)
	{
		renderID = id;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (block instanceof BlockRGBStairs)
		{
			renderer.renderBlockAsItem(Block.stairsWoodBirch, metadata, modelID);
		} else if (block instanceof BlockRGB)
		{
			renderer.renderBlockAsItem(Block.stone, metadata, modelID);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (world.getBlockTileEntity(x, y, z) instanceof TileEntityRGB)
		{
			if (block instanceof BlockRGBStairs)
			{
				renderer.renderBlockStairs((BlockRGBStairs) block, x, y, z);
			} else if (block instanceof BlockRGB)
			{
				renderer.renderStandardBlock(block, x, y, z);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return renderID;
	}

}
