package cz.limeth.neurolution.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class ButtonListener extends InputListener
{
	public abstract void press(InputEvent event, float x, float y, int pointer, int button);
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	{
		return true;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button)
	{
		Actor target = event.getTarget();
		
		if(x < target.getX())
			return;
		else if(x >= target.getRight())
			return;
		else if(y < target.getY())
			return;
		else if(y >= target.getTop())
			return;
				
		press(event, x, y, pointer, button);
	}
}
