package pro.x_way.infinities_war.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pro.x_way.infinities_war.Calculator;
import pro.x_way.infinities_war.effects.Effect;
import pro.x_way.infinities_war.skils.Skill;
import pro.x_way.infinities_war.text.GameText;
import pro.x_way.infinities_war.windows.BattleScreen;

public class Unit implements Serializable {
    public static final int FULL_STATUS = 120;
    public static final int HEIGHT_STATUS_BAR_ROW = 17;
    public static final int HEIGHT_STATUS_BAR = 34;

    private UnitFactory.UnitType unitType;

    public enum TypeDamage {
        Damage, Hill, MESSAGE
    }


    private BattleScreen battleScreen;
    transient private Unit target;
    private boolean isPlayer;
    transient private TextureRegion texture;
    private int hp;
    private int mp;
    private int maxHp;
    private int maxMp;
    private int level;
    private int exp;
    private Rectangle rect;
    private Autopilot autopilot;
    private Vector2 position;
    private float attackAction;
    private float takeDamageAction;
    private Stats stats;


    private List<Skill> actions;

    private List<Button> buttons;
    //    transient private TextureRegion[][] frames;
//    transient private AnimationType currentAnimation;
//    transient private float animationTime;
//    transient private float animationSpeed;
//    transient private int maxFrame;
//    transient private int maxAnimationType;
//    transient private int animationFrame;
    private int widthTexture;
    private int heightTexture;
    private static float sizeTexture = 2f;
    transient private Color unitColor;
    transient private float rotationDx;
    transient private float rotationDy;

    //StatusBar
    private Texture textureStatus;
    private Pixmap pixmap;

    //Bonus
    private List<Effect> effects;


    public enum AnimationType {
        IDLE(0), ATTACK(1);
        int number;

        AnimationType(int number) {
            this.number = number;
        }
    }

    public Unit(TextureAtlas.AtlasRegion texture, UnitFactory.UnitType unitType, Stats stats) {
        this.unitType = unitType;
        this.texture = texture;
        this.widthTexture = (int) (texture.getRegionWidth() * sizeTexture);
        this.heightTexture = (int) (texture.getRegionHeight() * sizeTexture);
//        this.frames = this.texture.split(WIDTH, HEIGHT);
        this.stats = stats;
        this.effects = new ArrayList<Effect>();
        this.position = new Vector2(0, 0);
        this.actions = new ArrayList<Skill>();
        this.rotationDx = 0;
        this.rotationDy = 0;
        this.unitColor = Color.WHITE;
//        this.animationSpeed = 0.2f;
//        this.maxFrame = this.frames[0].length;
//        this.maxAnimationType = this.frames.length - 1;
//        this.currentAnimation = AnimationType.IDLE;
    }

    public void reload(TextureAtlas.AtlasRegion texture) {
        this.texture = texture;
//        this.frames = this.texture.split(WIDTH, HEIGHT);
//        this.maxFrame = this.frames[0].length;
        this.actions = new ArrayList<Skill>();
//        this.animationSpeed = 0.2f;
//        this.maxAnimationType = this.frames.length - 1;
//        this.currentAnimation = AnimationType.IDLE;
        this.rotationDx = 0;
        this.rotationDy = 0;
        this.unitColor = Color.WHITE;
    }

    public void render(SpriteBatch batch) {


        batch.setColor(unitColor);
        float dx = (50f * (float) Math.sin((1f - attackAction) * 3.14f));
        if (!isPlayer) dx *= -1;
        float ang = 0;
        if (!isAlive()) ang = 90;

        if (!isPlayer) texture.flip(true, false); // разворот врагов
        batch.draw(texture, position.x + dx, position.y, 0, 0, widthTexture, heightTexture, 1, 1, ang);
        if (!isPlayer) texture.flip(true, false); // разворот врагов

        batch.setColor(unitColor);
        if (isAlive()) {
            statusBar(batch);
        }
    }

    private Pixmap getStatusBarTexture() {
        pixmap = new Pixmap(FULL_STATUS, HEIGHT_STATUS_BAR, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillRectangle(0, 0, Calculator.calculateStatusBar(maxHp, hp), HEIGHT_STATUS_BAR);
        return pixmap;
    }

    public void statusBar(SpriteBatch batch) {
        if (textureStatus == null) textureStatus = new Texture(getStatusBarTexture());
        if (isAlive()) {
            batch.setColor(0, 0, 0, 1);
            batch.draw(textureStatus, position.x + (widthTexture / 3), position.y + heightTexture - HEIGHT_STATUS_BAR_ROW,
                    FULL_STATUS, HEIGHT_STATUS_BAR_ROW * 2);
            batch.setColor(1, 0, 0, 1);
            batch.draw(textureStatus, position.x + (widthTexture / 3), position.y + heightTexture,
                    Calculator.calculateStatusBar(maxHp, hp), HEIGHT_STATUS_BAR_ROW);
            batch.setColor(0, 0, 1, 1);
            batch.draw(textureStatus, position.x + (widthTexture / 3), position.y + heightTexture - HEIGHT_STATUS_BAR_ROW,
                    Calculator.calculateStatusBar(maxMp, mp), HEIGHT_STATUS_BAR_ROW);
            batch.setColor(1, 1, 1, 1);
            GameText.getInstance().printStatusBar(this);
        }
    }

    public void update(float dt) {
//        animationTime += dt;
//        animationFrame = (int) (animationTime / animationSpeed);
//        animationFrame = animationFrame % maxFrame;
        if (takeDamageAction > 0) {
            takeDamageAction -= dt;
//            if (takeDamageAction <= 0) {
//                currentAnimation = AnimationType.IDLE;
//            }
        }
        if (attackAction > 0) {
            attackAction -= dt;
//            if (attackAction <= 0) {
//                currentAnimation = AnimationType.IDLE;
//            }
        }
    }

    public void recalculateHP() {
        this.maxHp = 5 * stats.getEndurance();
        this.hp = this.maxHp;
    }

    public void recalculateMP() {
        this.maxMp = 30;
        this.mp = this.maxMp;
    }


    public void evade() {
        GameText.getInstance().miss(this);
    }


    public void changeHp(int value, TypeDamage typeDamage) {
        int prevHp = hp;
        switch (typeDamage) {
            case Hill:
                hp += value;
                if (hp > maxHp) hp = maxHp;
                GameText.getInstance().hill(this, hp - prevHp);
                break;
            case Damage:
                hp -= value;
                if (hp < 0) hp = 0;
                GameText.getInstance().damage(this, prevHp - hp);
                break;
            case MESSAGE:
                GameText.getInstance().normalFlyText(this, 0);
                break;
        }
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }


    public void updateBeforeStep() {
        target = null;
        for (int i = effects.size() - 1; i >= 0; i--) {
            effects.get(i).tick();
            if (effects.get(i).isEnded()) {
                effects.get(i).end();
                effects.remove(i);
            }
        }
    }

    public Autopilot getAutopilot() {
        return autopilot;
    }

    public Stats getStats() {
        return stats;
    }

    public Unit getTarget() {
        return target;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public UnitFactory.UnitType getUnitType() {
        return unitType;
    }

    public List<Skill> getActions() {
        return actions;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public int getLevel() {
        return level;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRect() {
        return rect;
    }


    public TextureRegion getTexture() {
        return texture;
    }

    public BattleScreen getBattleScreen() {
        return battleScreen;
    }

    public int getHEIGHT() {
        return heightTexture;
    }

    public int getWIDTH() {
        return widthTexture;
    }


    public boolean isMyTeammate(boolean isPlayer) {
        return this.isPlayer == isPlayer;
    }


    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public boolean isAlive() {
        return hp > 0;
    }

//    public void setCurrentAnimation(AnimationType currentAnimation) {
//        this.currentAnimation = currentAnimation;
//    }


    public void setAutopilot(Autopilot autopilot) {
        this.autopilot = autopilot;
    }

    public void setToMap(BattleScreen battleScreen, int cellX, int cellY) {
        this.battleScreen = battleScreen;
        this.position.set(battleScreen.getStayPoints()[cellX][cellY]);
        this.rect = new Rectangle(position.x, position.y, widthTexture, heightTexture);
        this.recalculateHP();
        this.recalculateMP();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttackAction(float attackAction) {
        this.attackAction = attackAction;
    }

    public void setActions(List<Skill> actions) {
        this.actions = actions;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }

    public void setRotationDx(float rotationDx) {
        this.rotationDx = rotationDx;
    }

    public void setRotationDy(float rotationDy) {
        this.rotationDy = rotationDy;
    }

    public void setUnitColor(Color unitColor) {
        this.unitColor = unitColor;
    }
}
