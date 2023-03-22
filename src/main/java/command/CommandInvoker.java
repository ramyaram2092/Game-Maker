package command;

import java.util.EmptyStackException;
import java.util.Stack;

import command.Command;

public class CommandInvoker 
{
	private Stack<Command> commandStack;
	private Stack<Command> undoneCommands;
	
	public CommandInvoker()
	{
		commandStack = new Stack<>();
		undoneCommands = new Stack<>();
	}
	
	public void receiveCommand(Command c)
	{
		c.execute();
		commandStack.push(c);
		//System.out.println(c);
	}
	
	public void undo()
	{
		try {
		Command toUndo = commandStack.pop();
		//System.out.println(toUndo);
		toUndo.unexecute();
		undoneCommands.push(toUndo);
		} catch(EmptyStackException e)
		{
			
		}
	}
	
	
	//Stretch goal, not a big deal if we can't get this in
	public void redo()
	{
		try {
		Command toRedo = undoneCommands.pop();
		toRedo.execute();
		commandStack.push(toRedo);
		} catch(EmptyStackException e)
		{
			
		}
	}
	
}
