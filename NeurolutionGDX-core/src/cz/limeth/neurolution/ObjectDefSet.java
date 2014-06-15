package cz.limeth.neurolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

@SuppressWarnings("serial")
public class ObjectDefSet<B extends BodyDef, F extends FixtureDef> extends HashSet<ObjectDef<B, F>>
{
	public void createObjects(World world)
	{
		for(ObjectDef<B, F> object : this)
			object.createObject(world);
	}
	
	@Override
	public boolean add(ObjectDef<B, F> arg0)
	{
		if(arg0 == null)
			throw new IllegalArgumentException("The value must not be null!");
		
		return super.add(arg0);
	}
	
	@Override
	public boolean addAll(Collection<? extends ObjectDef<B, F>> arg0)
	{
		ArrayList<ObjectDef<B, F>> list = new ArrayList<ObjectDef<B, F>>();
		
		for(ObjectDef<B, F> o : arg0)
			if(o != null)
				list.add(o);
		
		return super.addAll(list);
	}
}
