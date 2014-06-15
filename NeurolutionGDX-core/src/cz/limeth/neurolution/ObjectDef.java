package cz.limeth.neurolution;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class ObjectDef<B extends BodyDef, F extends FixtureDef>
{
	private B bodyDef;
	private F fixtureDef;
	private Body body;
	private Fixture fixture;

	public ObjectDef(B bodyDef, F fixtureDef)
	{
		if(bodyDef == null || fixtureDef == null)
			throw new NullPointerException();

		this.bodyDef = bodyDef;
		this.fixtureDef = fixtureDef;
	}

	public ObjectDef(B bodyDef, F fixtureDef, Shape shape)
	{
		this(bodyDef, fixtureDef);

		if(shape == null)
			throw new NullPointerException();

		fixtureDef.shape = shape;
	}

	public void createObject(World world)
	{
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
	}

	public B getBodyDef()
	{
		return bodyDef;
	}

	public void setBodyDef(B bodyDef)
	{
		if(bodyDef == null)
			throw new IllegalArgumentException("The argument must not be null!");

		this.bodyDef = bodyDef;
	}

	public F getFixtureDef()
	{
		return fixtureDef;
	}

	public void setFixtureDef(F fixtureDef)
	{
		if(fixtureDef == null)
			throw new IllegalArgumentException("The argument must not be null!");

		this.fixtureDef = fixtureDef;
	}

	public Shape getShape()
	{
		return fixtureDef.shape;
	}

	public void setShape(Shape shape)
	{
		if(shape == null)
			throw new IllegalArgumentException("The argument must not be null!");

		fixtureDef.shape = shape;
	}

	public Body getBody()
	{
		return body;
	}

	public Fixture getFixture()
	{
		return fixture;
	}
}
