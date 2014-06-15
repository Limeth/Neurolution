package cz.limeth.neurolution.screen;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Screen;

public enum ScreenType
{
	MAIN_MENU(MainMenu.class);
	
	private final Class<? extends Screen> screenClass;
	
	private ScreenType(Class<? extends Screen> screenClass)
	{
		this.screenClass = screenClass;
	}
	
	public Screen newInstance() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return screenClass.getConstructor().newInstance();
	}

	public Class<? extends Screen> getScreenClass()
	{
		return screenClass;
	}
}
