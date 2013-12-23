package rgblocks.commands;

import java.awt.Color;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import rgblocks.items.ItemColorChanger;

public class CommandSetColor extends CommandBase
{

	@Override
	public String getCommandName()
	{
		return "changeColor";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "commandname <color as hex>";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		Color color = Color.decode(astring.length > 0 ? astring[0] : "");
		if (color == null)
			icommandsender.sendChatToPlayer(new ChatMessageComponent().addText("Invalid arguments"));
		ItemStack currentItem = ((EntityPlayer) icommandsender).inventory.getCurrentItem();
		ItemColorChanger.setColor(currentItem, color);
		icommandsender.sendChatToPlayer(new ChatMessageComponent().addText("Color successfully changed"));
	}

	public boolean canCommandSenderUseCommand(ICommandSender commandsender)
	{
		return commandsender instanceof EntityPlayer;
	}

	public int compareTo(ICommand par1ICommand)
	{
		return this.getCommandName().compareTo(par1ICommand.getCommandName());
	}

	public int compareTo(Object par1Obj)
	{
		return this.compareTo((ICommand) par1Obj);
	}
}
