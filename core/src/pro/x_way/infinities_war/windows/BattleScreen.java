package pro.x_way.infinities_war.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.Calculator;
import pro.x_way.infinities_war.MyInputProcessor;
import pro.x_way.infinities_war.SpecialFXEmitter;
import pro.x_way.infinities_war.gui.GUI;
import pro.x_way.infinities_war.text.GameText;
import pro.x_way.infinities_war.units.GenerateUnit;
import pro.x_way.infinities_war.units.Unit;

public class BattleScreen implements Screen {
    public static final String WINDOW_NEXT_LEVEL = "windowNextLevel";
    private SpriteBatch batch;
    private List<Unit> units;
    private int currentUnitIndex;
    private Unit currentUnit;
    private TextureRegion textureSelector;
    private Texture backGround;


    private Vector2[][] stayPoints;
    private float animationTimer;
    private Stage stage;
//    private BitmapFont font;

    private MyInputProcessor mip;
    private SpecialFXEmitter specialFXEmitter;


    public BattleScreen(SpriteBatch batch) {
        this.batch = batch;
    }



    @Override
    public void show() {
        createStayPoints();



        units = new ArrayList<Unit>();

        GenerateUnit.getInstance().setup(this);

        //Управление
        mip = new MyInputProcessor();
        Gdx.input.setInputProcessor(mip);


        backGround = Assets.getInstance().getAssetManager().get("background.png", Texture.class);

        // Шрифты
//        font = StyleText.getInstance().getTextSize14();
        GameText.getInstance().gameTextSetup();


        textureSelector = Assets.getInstance().getAtlas().findRegion("selector");

        currentUnitIndex = 0;
        currentUnit = units.get(currentUnitIndex);
        specialFXEmitter = new SpecialFXEmitter();
        createGUI();
        InputMultiplexer im = new InputMultiplexer(stage, mip);
        Gdx.input.setInputProcessor(im);
        animationTimer = 0.0f;
    }

    private void createStayPoints() {
        final int LEFT_STAY_POINT_X = 150;
        final int TOP_STAY_POINT_Y = 400;
        final int DISTANCE_BETWEEN_UNITS_X = 200;
        final int DISTANCE_BETWEEN_UNITS_Y = 200;
        final int DISTANCE_BETWEEN_TEAMS = 200;
        final int BATTLE_ROW = 2;
        final int BATTLE_COLUMN = 4;

        stayPoints = new Vector2[BATTLE_COLUMN][BATTLE_ROW];

        for (int i = 0; i < BATTLE_COLUMN; i++) {
            for (int j = 0; j < BATTLE_ROW; j++) {
                int x = LEFT_STAY_POINT_X + i * DISTANCE_BETWEEN_UNITS_X + j * 20;
                if (i > 1) x += DISTANCE_BETWEEN_TEAMS;
                stayPoints[i][j] = new Vector2(x, TOP_STAY_POINT_Y - j * DISTANCE_BETWEEN_UNITS_Y);
            }
        }
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        GUI.getInstance().getBattleGui(this);
    }


    private void showWindowNextLevel() {
        Array<Actor> array = stage.getActors();

        for (Actor actor : array) {
            if (actor.getName() != null)
                if (actor.getName().equals(WINDOW_NEXT_LEVEL))
                    actor.setVisible(true);
        }
    }

    public boolean isHeroTurn() {
        return currentUnit.getAutopilot() == null;
    }

    public void nextTurn() {
        if (!endGame()){
            Calculator.hideUserPanel(units);
            currentUnit = Calculator.giveNextUnitStep(this);
            currentUnit.updateBeforeStep();
            Calculator.showUserPanel(currentUnit);
            animationTimer = 1.0f;
        }
        else {

        }
    }

    private boolean endGame() {
        int player = 0;
        int AI = 0;

        for (int i = 0; i < units.size(); i++) {
            Unit unit = units.get(i);
            if (unit.isAlive()) {
                if (unit.isPlayer()) player++;
                else AI++;
            }
        }
        if (player == 0) {
            showWindowNextLevel();
            return true;
        } else if (AI == 0) {
            showWindowNextLevel();
            return true;
        }

        return false;
    }

    public void update(float dt) {
        GameText.getInstance().update(dt);
        if (isHeroTurn() && canIMakeTurn()) {
            stage.act(dt);
            if (currentUnit.getActionPanel() != null) {
                currentUnit.getActionPanel().setVisible(true);
            }
        }

        animationTimer = Calculator.timerDecrement(animationTimer, dt);
        Calculator.updateUnit(this, dt);
        Calculator.setupTarget(this);


        if (!isHeroTurn()) {
            if (currentUnit.getAutopilot().turn(currentUnit)) {
                nextTurn();
            }
        }


        specialFXEmitter.update(dt);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        update(delta);
        batch.draw(backGround,0,0);

        batch.setColor(1, 1, 0, 0.8f);
        batch.draw(textureSelector, currentUnit.getPosition().x, currentUnit.getPosition().y - 5);
        if (isHeroTurn() && currentUnit.getTarget() != null) {
            batch.setColor(1, 0, 0, 0.8f);
            batch.draw(textureSelector, currentUnit.getTarget().getPosition().x, currentUnit.getTarget().getPosition().y - 5);
        }
        batch.setColor(1, 1, 1, 1);
        for (int i = 0; i < units.size(); i++) {
            units.get(i).render(batch);
        }

        specialFXEmitter.render(batch);

        GameText.getInstance().printFlyingText();
        batch.end();
        stage.draw();
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
    }

    public SpecialFXEmitter getSpecialFXEmitter() {
        return specialFXEmitter;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public Vector2[][] getStayPoints() {
        return stayPoints;
    }

    public boolean canIMakeTurn() {
        return animationTimer <= 0.0f;
    }

    public void incrementCurrentUnitIndex() {
        this.currentUnitIndex = ++currentUnitIndex;
    }

    public int getCurrentUnitIndex() {
        return currentUnitIndex;
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public MyInputProcessor getMip() {
        return mip;
    }

    public void setCurrentUnitIndex(int currentUnitIndex) {
        this.currentUnitIndex = currentUnitIndex;
    }

    public Stage getStage() {
        return stage;
    }



}