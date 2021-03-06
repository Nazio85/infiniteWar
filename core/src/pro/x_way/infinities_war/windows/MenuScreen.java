package pro.x_way.infinities_war.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.Session;
import pro.x_way.infinities_war.text.StyleText;


public class MenuScreen implements Screen {
    public static final String START_NEW_GAME = "START NEW GAME";
    public static final String CONTINUE_NEW_GAME = "CONTINUE NEW GAME";
    public static final String EXIT_GAME = "EXIT GAME";
    public static final String BUTTON_STYLE = "tbs";
    private Texture backgroundTexture;
    private TextureAtlas.AtlasRegion buttonTexture;
    private Music music;
    private SpriteBatch batch;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font96;
    private BitmapFont font36;

    private Stage stage;
    private Skin skin;
    private float time;

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        //Текстуры
        backgroundTexture = Assets.getInstance().getAssetManager().get("background.png", Texture.class);
//        buttonTexture = Assets.getInstance().getAtlas().findRegion("menuBtn");
//        music = Gdx.audio.newMusic(Gdx.files.internal("Jumping bat.wav"));
//        music.setLooping(true);
//        music.play();

        //Шрифты
        font36 = StyleText.getInstance().createFont36();
        font96 = StyleText.getInstance().createFont96();

        //Слушатель
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        skin.add("font36", font36);
        TextButton.TextButtonStyle textButtonStyle = createButtonStyle();
        skin.add(BUTTON_STYLE, textButtonStyle);

        Button btnNewGame = new TextButton(START_NEW_GAME, skin, BUTTON_STYLE);
        Button btnContinueGame = new TextButton(CONTINUE_NEW_GAME, skin, BUTTON_STYLE);
        Button btnExitGame = new TextButton(EXIT_GAME, skin, BUTTON_STYLE);
        btnNewGame.setPosition(640 - 240, 360);
        btnContinueGame.setPosition(640 - 240, 240);
        btnExitGame.setPosition(640 - 240, 120);
        stage.addActor(btnNewGame);
        stage.addActor(btnContinueGame);
        stage.addActor(btnExitGame);

        btnNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Session.getInstance().startGame();
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.BATTLE);
            }
        });

        btnContinueGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                GameSession.getInstance().loadSession();
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.BATTLE);
//                GameSession.getInstance().saveSession();
            }
        });
        btnExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    private TextButton.TextButtonStyle createButtonStyle() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable(Assets.MENU_BTN);
        textButtonStyle.down = skin.newDrawable(Assets.MENU_BTN, Color.GRAY);
        textButtonStyle.font = font36;
        return textButtonStyle;
    }

    @Override
    public void render(float delta) {
        update(delta);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0,RpgGame.SCREEN_WIDTH, RpgGame.SCREEN_HEIGHT);
        font96.draw(batch, "geek-android-rpg-game", 0, 600 + 20.0f * (float)Math.sin(time), 1280, 1, false);
        batch.end();
        stage.draw();
    }

    public void update(float dt) {
        time += dt;
        stage.act(dt);
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().onResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        music.dispose();
        backgroundTexture.dispose();
        font36.dispose();
        font96.dispose();
    }
}
