package cz.limeth.neurolution;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import cz.limeth.neurolution.screen.ScreenType;

public class Neurolution extends Game
{
	private static Neurolution instance;
	
	@Override
	public void create()
	{
		instance = this;
		
		setScreen(ScreenType.MAIN_MENU);
	}
	
	public boolean setScreen(ScreenType type)
	{
		try
		{
			setScreen(type.newInstance());
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static void removeInputProcessor()
	{
		Gdx.input.setInputProcessor(null);
	}

	public static Neurolution getInstance()
	{
		return instance;
	}
}
