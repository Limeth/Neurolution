package cz.limeth.neurolution.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import cz.limeth.neurolution.NConst;
import cz.limeth.neurolution.Neurolution;
import cz.limeth.neurolution.listeners.ButtonListener;

public class MainMenu implements Screen
{
	private ScreenViewport viewport;
	private Stage stage;
	private BitmapFont font;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private SpriteBatch batch;
	private TextButton[] buttons;
	private TextButtonStyle buttonStyle;
	
	public MainMenu()
	{
		viewport = new ScreenViewport();
		buttonStyle = new TextButtonStyle();
		buttonStyle.fontColor = Color.RED;
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void show()
	{
		batch = new SpriteBatch();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("atlas/button.pack"));
		buttonSkin = new Skin(buttonAtlas);
		font = new BitmapFont(Gdx.files.internal("font/openSans.fnt"));
		buttonStyle.up = buttonSkin.getDrawable("button");
		buttonStyle.down = buttonSkin.getDrawable("buttonPressed");
		buttonStyle.font = font;
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		buttons = new TextButton[]
		{
			newButton("New Neurolution", new ButtonListener()
			{
				@Override
				public void press(InputEvent event, float x, float y, int pointer, int button)
				{
					Neurolution.removeInputProcessor();
					Neurolution.getInstance().setScreen(new Simulation(1000, 1000, NConst.WORLD_SCALE));
				}
			}),
			newButton("Load Neurolution", new ButtonListener()
			{
				@Override
				public void press(InputEvent event, float x, float y, int pointer, int button)
				{
					System.out.println("Load");
				}
			}),
			newButton("Quit", new ButtonListener()
			{
				@Override
				public void press(InputEvent event, float x, float y, int pointer, int button)
				{
					Gdx.app.exit();
				}
			})
		};
		
		for(TextButton button : buttons)
			stage.addActor(button);
	}
	
	private TextButton newButton(String text, InputListener listener)
	{
		TextButton button = new TextButton(text, buttonStyle);
		
		button.setSize(NConst.MAIN_MENU_BUTTON_WIDTH, NConst.MAIN_MENU_BUTTON_HEIGHT);
		button.addListener(listener);
		
		return button;
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height, true);
		
		float menuHeight = buttons.length * NConst.MAIN_MENU_BUTTON_HEIGHT + (buttons.length - 1) * NConst.MAIN_MENU_BUTTON_SPACING;
		
		for(int i = 0; i < buttons.length; i++)
		{
			TextButton button = buttons[i];
			
			if(button == null)
				continue;
			
			float x = (width - NConst.MAIN_MENU_BUTTON_WIDTH) / 2f;
			float y = ((height + menuHeight) / 2f) - i * (NConst.MAIN_MENU_BUTTON_HEIGHT + NConst.MAIN_MENU_BUTTON_SPACING);
			
			button.setPosition(x, y);
		}
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
		stage.dispose();
		font.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		batch.dispose();
	}
}
