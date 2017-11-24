package pro.x_way.infinities_war.gui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.List;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.Session;
import pro.x_way.infinities_war.actions.BaseAction;
import pro.x_way.infinities_war.text.StyleText;
import pro.x_way.infinities_war.units.Unit;
import pro.x_way.infinities_war.units.UnitFactory;
import pro.x_way.infinities_war.windows.BattleScreen;
import pro.x_way.infinities_war.windows.RpgGame;
import pro.x_way.infinities_war.windows.ScreenManager;

public class BattleGui {
    public static final String YOU_WIN = "You win!";


    private BattleScreen battleScreen;
    private Stage stage;
    private UnitFactory unitFactory;
    private List<Unit> units;
    private Unit currentUnit;
    private Skin skin;

    public BattleGui(BattleScreen battleScreen) {
        this.battleScreen = battleScreen;
        stage = battleScreen.getStage();
        unitFactory = Session.getUnitFactory();
        units = battleScreen.getUnits();
        currentUnit = battleScreen.getCurrentUnit();

        createGui();
    }

    public void createGui(){
        skin = new Skin(Assets.getInstance().getAtlas());
        prepareTextureForBtnPanel();
        for (Unit unit : units) {
            if (unit.isPlayer()) {
                Group actionPanel = createActionPanel(unit);
                stage.addActor(actionPanel);
                createBtnForActionPanel(unit, actionPanel);
            }
        }



        createBtnStyle(Assets.GO_TO_MENU);
        Button buttonGoToMenu = new Button(skin, Assets. GO_TO_MENU);
        buttonGoToMenu.setPosition(1160,600);
        buttonGoToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MENU);
            }
        });
        stage.addActor(buttonGoToMenu);


//        Create Window nextLevel
        Group windowNextLevel = createWindowNextLevel();
        Button buttonStayHere = createBtnStayHere();
        Button buttonNextLevel = createBtnNextLevel();
        Label label = createLabelYouWin();

//        Add
        windowNextLevel.addActor(label);
        windowNextLevel.addActor(buttonStayHere);
        windowNextLevel.addActor(buttonNextLevel);
        stage.addActor(windowNextLevel);
    }

    private Label createLabelYouWin() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = StyleText.getInstance().createFont36();
        labelStyle.fontColor = Color.WHITE;
        skin.add(YOU_WIN, labelStyle);
        Label label = new Label(YOU_WIN, skin, YOU_WIN);
        label.setPosition(70, 85);
        return label;
    }

    private Button createBtnStayHere() {
        createBtnStyle(Assets.BTN_STAY_HERE);
        Button buttonStayHere = new Button(skin, Assets.BTN_STAY_HERE);
        buttonStayHere.setPosition(20,20);
        return buttonStayHere;
    }

    private Button createBtnNextLevel() {
        createBtnStyle(Assets.BTN_NEXT_LEVEL);
        Button buttonNextLevel = new Button(skin, Assets.BTN_NEXT_LEVEL);
        buttonNextLevel.setPosition(160,20);
        return buttonNextLevel;
    }

    private void createBtnStyle(String name){
        Button.ButtonStyle buttonStyleNextLevel = new Button.ButtonStyle();
        buttonStyleNextLevel.up = skin.newDrawable(name);
        buttonStyleNextLevel.down = skin.newDrawable(name, Color.GRAY);
        skin.add(name, buttonStyleNextLevel);
        System.out.println(name);
    }

    private Group createActionPanel(Unit unit) {
        Group actionPanel = new Group();
        Image image = new Image(Assets.getInstance().getAtlas().findRegion(Assets.ACTION_PANEL));
        actionPanel.addActor(image);
        actionPanel.setPosition(RpgGame.SCREEN_WIDTH / 2 - 840 / 2, 5);
        actionPanel.setVisible(false);
        unit.setActionPanel(actionPanel);
        return actionPanel;
    }

    private void createBtnForActionPanel(final Unit o, Group actionPanel) {
        int counter = 0;
        final Unit innerUnit = o;
        for (BaseAction a : o.getActions()) {
            final BaseAction baseAction = a;
            Button btn = new Button(skin, a.getBtnTexture());
            btn.setPosition(30 + counter * 100, 30);
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (innerUnit.isPlayer()) {
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

    private void prepareTextureForBtnPanel() {
        List<BaseAction> actions = unitFactory.getActions();
        for (BaseAction action : actions) {
            createBtnStyle(action.getBtnTexture());
        }
    }

    private Group createWindowNextLevel() {
        Group windowNextLevel = new Group();
        Image image = new Image(Assets.getInstance().getAtlas().findRegion(Assets.WINDOW_NEXT_LEVEL));
        windowNextLevel.addActor(image);
        windowNextLevel.setPosition(RpgGame.SCREEN_WIDTH / 2 - 150, RpgGame.SCREEN_HEIGHT / 2);
        windowNextLevel.setVisible(false);
        windowNextLevel.setName(Assets.WINDOW_NEXT_LEVEL);
        return windowNextLevel;
    }
}
