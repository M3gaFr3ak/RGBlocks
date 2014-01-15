package rgblocks.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import rgblocks.RGBlocks;

public class CommonProxy
{
	public void registerRecipes()
	{

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RGBlocks.instance.itemColorChanger, 1), new Object[]
		{ "RGB", "III", "SSS", 'R', "dyeRed", 'G', "dyeGreen", 'B', "dyeBlue", 'I', Item.ingotIron, 'S', Item.redstone }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RGBlocks.instance.blockRGB, 32), new Object[]
		{ "RGB", "III", "SSS", 'R', "dyeRed", 'G', "dyeGreen", 'B', "dyeBlue", 'I', Block.stone, 'S', Item.redstone }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RGBlocks.instance.blockRGBStairs, 32), new Object[]
		{ "RGB", "III", "SSS", 'R', "dyeRed", 'G', "dyeGreen", 'B', "dyeBlue", 'I', Block.stairsCobblestone, 'S', Item.redstone }));

	}
}
