package rgbblocks;

import net.minecraft.block.Block;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import rgbblocks.commands.CommandSetColor;
import rgbblocks.items.ItemColorChanger;
import rgblocks.blocks.BlockRGB;
import rgblocks.blocks.BlockRGBStairs;
import rgblocks.render.RenderHandler;
import rgblocks.tiles.TileEntityRGB;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
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
	public int renderHandlerID;
	public int stairsRGBID;
	public int blockRGBID;
	public int itemColorChangerID;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		stairsRGBID = config.getBlock("stairsRGBID", 1337, "ID for the RGB Stairs").getInt();
		blockRGBID = config.getBlock("blockRGBID", 1338, "ID for the RGB Block").getInt();
		itemColorChangerID = config.getItem("itemColorChangerID", 1338, "ID for the RGB Color Changer").getInt();
		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		BlockRGBStairs stairs = new BlockRGBStairs(stairsRGBID);
		BlockRGB block = new BlockRGB(blockRGBID);
		GameRegistry.registerBlock(stairs, ItemBlock.class, "rgbstairs");
		GameRegistry.registerBlock(block, ItemBlock.class, "rgbblock");
		GameRegistry.registerTileEntity(TileEntityRGB.class, "rgblocks.tileentityrgb");
		new ItemColorChanger(itemColorChangerID);
		renderHandlerID = RenderingRegistry.getNextAvailableRenderId();
		RenderHandler handler = new RenderHandler(renderHandlerID);
		RenderingRegistry.registerBlockHandler(handler);
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		MinecraftServer server = MinecraftServer.getServer();
		ServerCommandManager manager = (ServerCommandManager) server.getCommandManager();
		manager.registerCommand(new CommandSetColor());
	}
}
