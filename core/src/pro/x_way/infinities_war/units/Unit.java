package pro.x_way.infinities_war.units;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

import pro.x_way.infinities_war.Calculator;
import pro.x_way.infinities_war.actions.BaseAction;
import pro.x_way.infinities_war.effects.Effect;
import pro.x_way.infinities_war.text.FlyingText;
import pro.x_way.infinities_war.text.GameText;
import pro.x_way.infinities_war.windows.BattleScreen;

public class Unit {
    public static final int FULL_STATUS = 90;
    public static final int HEIGHT_STATUS_BAR_ROW = 13;
    public static final int HEIGHT_STATUS_BAR = 26;

    public static final int TYPE_DAMAGE = -1;
    public static final int TYPE_HILL = 1;
    private final UnitFactory.UnitType unitType;


    public void reload(TextureRegion texture, List<BaseAction> actions) {
        this.texture = texture;
        this.frames = this.texture.split(WIDTH, HEIGHT);
        this.actions = actions;
        this.effects.clear();
        this.animationSpeed = 0.2f;
        this.frames = this.texture.split(WIDTH, HEIGHT);
        this.maxFrame = this.frames[0].length;
        this.maxAnimationType = this.frames.length - 1;
        this.currentAnimation = AnimationType.IDLE;
    }

    public UnitFactory.UnitType getType() {
        return unitType;
    }

    public enum AnimationType {
        IDLE(0), ATTACK(1);
        int number;
        AnimationType(int number) {
            this.number = number;
        }
    }

    private BattleScreen battleScreen;
    private Unit target;
    private boolean isPlayer;
    private TextureRegion texture;
    private int hp;
    private int mp;
    private int maxHp;
    private int maxMp;
    private int level;
    private int exp;
    private Rectangle rect;
    private Autopilot autopilot;
    private Vector2 position;
    private boolean flip;
    private float attackAction;
    private float takeDamageAction;
    private Stats stats;
    private Group actionPanel;

    private List<BaseAction> actions;
    private TextureRegion[][] frames;
    private AnimationType currentAnimation;
    private float animationTime;
    private float animationSpeed;
    private int maxFrame;
    private int maxAnimationType;
    private int animationFrame;
    private final int WIDTH = 90;
    private final int HEIGHT = 150;

    //StatusBar
    private Texture textureStatus;
    private Pixmap pixmap;

    //Bonus
    private List<Effect> effects;


    public Unit(UnitFactory.UnitType unitType, TextureRegion texture, Stats stats) {
        this.unitType = unitType;
        this.texture = texture;
        this.stats = stats;
        this.effects = new ArrayList<Effect>();
        this.position = new Vector2(0, 0);
        this.actions = new ArrayList<BaseAction>();
        this.animationSpeed = 0.2f;
        this.frames = this.texture.split(WIDTH, HEIGHT);
        this.maxFrame = this.frames[0].length;
        this.maxAnimationType = this.frames.length - 1;
        this.currentAnimation = AnimationType.IDLE;
    }

    public void render(SpriteBatch batch) {
        if (takeDamageAction > 0) {
            batch.setColor(1f, 1f - takeDamageAction, 1f - takeDamageAction, 1f);
        }
        float dx = (50f * (float) Math.sin((1f - attackAction) * 3.14f));
        if (flip) dx *= -1;
        float ang = 0;
        if (!isAlive()) ang = 90;
        int n = currentAnimation.number;
        if (n > maxAnimationType) {
            n = 0;
        }
        if (flip) {
            frames[n][animationFrame].flip(true, false);
        }
        batch.draw(frames[n][animationFrame], position.x + dx, position.y, 0, 0, WIDTH, HEIGHT, 1, 1, ang);
        if (flip) {
            frames[n][animationFrame].flip(true, false);
        }
        batch.setColor(1f, 1f, 1f, 1f);
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
            batch.draw(textureStatus, position.x, position.y + HEIGHT - HEIGHT_STATUS_BAR_ROW,
                    FULL_STATUS, HEIGHT_STATUS_BAR_ROW * 2);
            batch.setColor(1, 0, 0, 1);
            batch.draw(textureStatus, position.x, position.y + HEIGHT,
                    Calculator.calculateStatusBar(maxHp, hp), HEIGHT_STATUS_BAR_ROW);
            batch.setColor(0, 0, 1, 1);
            batch.draw(textureStatus, position.x, position.y + HEIGHT - HEIGHT_STATUS_BAR_ROW,
                    Calculator.calculateStatusBar(maxMp, mp), HEIGHT_STATUS_BAR_ROW);
            batch.setColor(1, 1, 1, 1);
            GameText.getInstance().printStatusBar(this);
        }
    }

    public void update(float dt) {
        animationTime += dt;
        animationFrame = (int) (animationTime / animationSpeed);
        animationFrame = animationFrame % maxFrame;
        if (takeDamageAction > 0) {
            takeDamageAction -= dt;
            if (takeDamageAction <= 0) {
                currentAnimation = AnimationType.IDLE;
            }
        }
        if (attackAction > 0) {
            attackAction -= dt;
            if (attackAction <= 0) {
                currentAnimation = AnimationType.IDLE;
            }
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


    public void changeHp(int value) {
        int prevHp = hp;
        hp += value;
        if (hp > maxHp) {
            hp = maxHp;
        }
        if (hp < 0) {
            hp = 0;
        }


        if (value > 0) {
            GameText.getInstance().hill(this, hp - prevHp);
        } else if (value < 0) {
            takeDamageAction = 1.0f;
            GameText.getInstance().damage(this, -(prevHp - hp));
        } else {
            GameText.getInstance().normalFlyText(this, 0);
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

    public List<BaseAction> getActions() {
        return actions;
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

    public Group getActionPanel() {
        return actionPanel;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public BattleScreen getBattleScreen() {
        return battleScreen;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public boolean isAI() {
        return autopilot != null;
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

    public void setCurrentAnimation(AnimationType currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public void setActionPanel(Group actionPanel) {
        this.actionPanel = actionPanel;
    }

    public void setAutopilot(Autopilot autopilot) {
        this.autopilot = autopilot;
    }

    public void setToMap(BattleScreen battleScreen, int cellX, int cellY) {
        this.battleScreen = battleScreen;
        this.position.set(battleScreen.getStayPoints()[cellX][cellY]);
        this.rect = new Rectangle(position.x, position.y, texture.getRegionWidth(), texture.getRegionHeight());
        this.recalculateHP();
        this.recalculateMP();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttackAction(float attackAction) {
        this.attackAction = attackAction;
    }

    public void setActions(List<BaseAction> actions) {
        this.actions = actions;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }
}
