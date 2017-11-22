package pro.x_way.infinities_war.gui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.time.format.TextStyle;
import java.util.List;

import javax.xml.soap.Text;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.actions.BaseAction;
import pro.x_way.infinities_war.text.StyleText;
import pro.x_way.infinities_war.units.GenerateUnit;
import pro.x_way.infinities_war.units.Unit;
import pro.x_way.infinities_war.units.UnitFactory;
import pro.x_way.infinities_war.windows.BattleScreen;
import pro.x_way.infinities_war.windows.RpgGame;

public class BattleGui {
    private static final String WINDOW_NEXT_LEVEL = "windowNextLevel";
    private static final String NEXT_LEVEL = "nextLevel";
    private static final String STAY_HERE = "stayHere";
    public static final String YOU_WIN = "You win!";
    private BattleScreen battleScreen;
    private Stage stage;
    private UnitFactory unitFactory;
    private List<Unit> units;
    private Unit currentUnit;

    public BattleGui(BattleScreen battleScreen) {
        this.battleScreen = battleScreen;
        stage = battleScreen.getStage();
        unitFactory = GenerateUnit.getInstance().getUnitFactory();
        units = battleScreen.getUnits();
        currentUnit = battleScreen.getCurrentUnit();

        createGui();
    }

    public void createGui(){
        Skin skinAction = new Skin();
        prepareTextureForBtnPanel(skinAction);
        for (Unit unit : units) {
            if (!unit.isAI()) {
                Group actionPanel = createActionPanel(unit);
                stage.addActor(actionPanel);

                createBtnForActionPanel(skinAction, unit, actionPanel);
            }
        }

        Group windowNextLevel = createWindowNextLevel();

        Skin skin = new Skin();
        Button buttonStayHere = createBtnStayHere(skin);
        Button buttonNextLevel = createBtnNextLevel(skin);
        Label label = createLabelYouWin();

        windowNextLevel.addActor(label);
        windowNextLevel.addActor(buttonStayHere);
        windowNextLevel.addActor(buttonNextLevel);
        stage.addActor(windowNextLevel);
    }

    private Label createLabelYouWin() {
        Skin skin = new Skin();
        skin.add(YOU_WIN, Assets.getInstance().getAssetManager().get(Assets.BTN_NEXT_LEVEL, Texture.class));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = StyleText.getInstance().createFont36();
        labelStyle.fontColor = Color.WHITE;
        skin.add(YOU_WIN, labelStyle);
        Label label = new Label(YOU_WIN, skin, YOU_WIN);
        label.setPosition(70, 85);
        return label;
    }

    private Button createBtnStayHere(Skin skinBtnNextLevel) {
        skinBtnNextLevel.add(STAY_HERE, Assets.getInstance().getAssetManager().get(Assets.BTN_STAY_HERE, Texture.class));
        Button.ButtonStyle buttonStyleStayHere = new Button.ButtonStyle();
        buttonStyleStayHere.up = skinBtnNextLevel.newDrawable(STAY_HERE);
        buttonStyleStayHere.down = skinBtnNextLevel.newDrawable(STAY_HERE, Color.GRAY);
        skinBtnNextLevel.add(STAY_HERE, buttonStyleStayHere);
        Button buttonStayHere = new Button(skinBtnNextLevel, STAY_HERE);
        buttonStayHere.setPosition(20,20);
        return buttonStayHere;
    }

    private Group createActionPanel(Unit unit) {
        Group actionPanel = new Group();
        Image image = new Image(Assets.getInstance().getAssetManager().get("actionPanel.png", Texture.class));
        actionPanel.addActor(image);
        actionPanel.setPosition(RpgGame.SCREEN_WIDTH / 2 - 840 / 2, 5);
        actionPanel.setVisible(false);
        unit.setActionPanel(actionPanel);
        return actionPanel;
    }

    private void createBtnForActionPanel(Skin skinAction, final Unit o, Group actionPanel) {
        int counter = 0;
        final Unit innerUnit = o;
        for (BaseAction a : o.getActions()) {
            final BaseAction baseAction = a;
            Button btn = new Button(skinAction, a.getName());
            btn.setPosition(30 + counter * 100, 30);
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!innerUnit.isAI()) {
                        if (baseAction.action(innerUnit)) {
                            battleScreen.nextTurn();
                        }
                    }
                }
            });
            actionPanel.addActor(btn);
            counter++;
        }
    }

    private void prepareTextureForBtnPanel(Skin skinAction) {
        List<BaseAction> actions = unitFactory.getActions();
        for (BaseAction action : actions) {
            skinAction.add(action.getName(), action.getBtnTexture());
            Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
            buttonStyle.up = skinAction.newDrawable(action.getName());
            skinAction.add(action.getName(), buttonStyle);
        }
    }

    private Button createBtnNextLevel(Skin skinBtnNextLevel) {
        skinBtnNextLevel.add(NEXT_LEVEL, Assets.getInstance().getAssetManager().get(Assets.BTN_NEXT_LEVEL, Texture.class));
        Button.ButtonStyle buttonStyleNextLevel = new Button.ButtonStyle();
        buttonStyleNextLevel.up = skinBtnNextLevel.newDrawable(NEXT_LEVEL);
        buttonStyleNextLevel.down = skinBtnNextLevel.newDrawable(NEXT_LEVEL, Color.GRAY);
        skinBtnNextLevel.add(NEXT_LEVEL, buttonStyleNextLevel);
        Button buttonNextLevel = new Button(skinBtnNextLevel, NEXT_LEVEL);
        buttonNextLevel.setPosition(160,20);
        return buttonNextLevel;
    }



    private Group createWindowNextLevel() {
        Group windowNextLevel = new Group();
        Image image = new Image(Assets.getInstance().getAtlas().findRegion(Assets.WINDOW_NEXT_LEVEL));
        windowNextLevel.addActor(image);
        windowNextLevel.setPosition(RpgGame.SCREEN_WIDTH / 2 - 150, RpgGame.SCREEN_HEIGHT / 2);
        windowNextLevel.setVisible(false);
        windowNextLevel.setName(WINDOW_NEXT_LEVEL);
        return windowNextLevel;
    }
}
