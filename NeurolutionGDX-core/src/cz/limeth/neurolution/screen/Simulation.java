package cz.limeth.neurolution.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cz.limeth.neurolution.NConst;
import cz.limeth.neurolution.ObjectDef;
import cz.limeth.neurolution.ObjectDefSet;

public class Simulation implements Screen
{
	private final float scale;
	private World world;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Box2DDebugRenderer debugRenderer;
	private ShapeRenderer renderer;
	private float width, height;
	private final ObjectDefSet<BodyDef, FixtureDef> defSet;
	private boolean shown;
	
	public Simulation(float width, float height, float scale)
	{
		this.width = width /= scale;
		this.height = height /= scale;
		this.scale = scale;
		camera = new OrthographicCamera(width, height);
		camera.position.set(width / 2, height / 2, 0);
		camera.update();
		viewport = new ExtendViewport(width, height, camera);
		defSet = new ObjectDefSet<BodyDef, FixtureDef>();
	}
	
	public void firstShow()
	{
		BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.position.set(width / 2, height / 2);
		circleBodyDef.type = BodyType.DynamicBody;
		
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(3);
		
		FixtureDef circleFixtureDef = new FixtureDef();
		circleFixtureDef.density = 1 * scale;
		circleFixtureDef.friction = 0.1f;
		circleFixtureDef.restitution = 0.8f;
		
		defSet.add(new ObjectDef<BodyDef, FixtureDef>(circleBodyDef, circleFixtureDef, circleShape));
		
		BodyDef lineBodyDef = new BodyDef();
		lineBodyDef.position.set(width / 2, height / 2 - 10);
		lineBodyDef.type = BodyType.DynamicBody;
		
		EdgeShape lineShape = new EdgeShape();
		lineShape.set(-5, -5, 5, 5);
		
		FixtureDef lineFixtureDef = new FixtureDef();
		lineFixtureDef.density = 2 * scale;
		lineFixtureDef.friction = 0.1f;
		lineFixtureDef.restitution = 0.8f;
		
		defSet.add(new ObjectDef<BodyDef, FixtureDef>(lineBodyDef, lineFixtureDef, lineShape));
		
		BodyDef groundDef = new BodyDef();
		groundDef.position.set(width / 2, 4);
		
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(width / 2, 4);
		
		FixtureDef groundFixtureDef = new FixtureDef();
		
		defSet.add(new ObjectDef<BodyDef, FixtureDef>(groundDef, groundFixtureDef, groundShape));
		
		shown = true;
	}
	
	@Override
	public void show()
	{
		world = new World(new Vector2(0, -9.8f), true);
		
		if(!shown)
			firstShow();
		
		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(camera.combined);
		
		if(NConst.DEBUG)
			debugRenderer = new Box2DDebugRenderer();
		
		Gdx.input.setInputProcessor(new InputProcessor()
		{
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button)
			{
				return false;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer)
			{
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button)
			{
				Vector3 vec = camera.project(new Vector3(screenX, screenY, 0), viewport.getViewportX(), viewport.getViewportY(), viewport.getViewportWidth(), viewport.getViewportHeight());
				Vector2 vec2 = new Vector2(vec.x, vec.y);
				System.out.println("[" + screenX + "; " + screenY + "] -> [" + vec.x + "; " + vec.y + "]");
				
				Array<Body> bodyArray = new Array<Body>();
				
				world.getBodies(bodyArray);
				
				for(Body body : bodyArray)
				{
					System.out.println(body.getWorldCenter());
					
					Array<Fixture> fixtureArray = body.getFixtureList();
					
					for(Fixture fixture : fixtureArray)
					{
						if(fixture.testPoint(vec2))
							System.out.println(body + " " + fixture);
					}
				}
				
				return false;
			}
			
			@Override
			public boolean scrolled(int amount)
			{
				return false;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY)
			{
				return false;
			}
			
			@Override
			public boolean keyUp(int keycode)
			{
				return false;
			}
			
			@Override
			public boolean keyTyped(char character)
			{
				return false;
			}
			
			@Override
			public boolean keyDown(int keycode)
			{
				return false;
			}
		});
		
		defSet.createObjects(world);
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(1/60f, 8, 3);
		
		renderer.begin(ShapeType.Filled);
		
		for(ObjectDef<BodyDef, FixtureDef> def : defSet)
		{
			Body body = def.getBody();
			Shape shape = def.getShape();
			Vector2 massCenter = body.getWorldCenter();
			float radius = shape.getRadius();
			
			renderer.setColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
			renderer.circle(massCenter.x, massCenter.y, radius);
		}
		
		renderer.end();
		
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void dispose()
	{
		world.dispose();
		renderer.dispose();
		
		if(debugRenderer != null)
			debugRenderer.dispose();
	}

	public float getScale()
	{
		return scale;
	}
}
