package rgbblocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import rgblocks.blocks.BlockRGB;
import rgblocks.blocks.BlockRGBStairs;
import rgblocks.render.RenderHandler;
import rgblocks.tiles.TileEntityRGB;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "rgblocks", name = "RGBlocks")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class RGBlocks
{
	@Instance("rgblocks")
	public static RGBlocks instance;
	public CreativeTabs ModTab = new CreativeTabs("RGBlocks")
	{
		public ItemStack getIconItemStack()
		{
			return new ItemStack(Block.wood, 1);
		}
	};
	public int renderID;
	public int stairsID;
	public int blockID;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		stairsID = config.getBlock("stairs_id", 1337, "ID for the RGB Stairs").getInt();
		blockID = config.getBlock("block_id", 1338, "ID for the RGB Block").getInt();
		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		BlockRGBStairs stairs = new BlockRGBStairs(stairsID);
		BlockRGB block = new BlockRGB(blockID);
		GameRegistry.registerBlock(stairs, ItemBlock.class, "rgbstairs");
		GameRegistry.registerBlock(block, ItemBlock.class, "rgbblock");
		GameRegistry.registerTileEntity(TileEntityRGB.class, "rgblocks.tileentityrgb");
		renderID = RenderingRegistry.getNextAvailableRenderId();
		RenderHandler handler = new RenderHandler(renderID);
		RenderingRegistry.registerBlockHandler(handler);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
