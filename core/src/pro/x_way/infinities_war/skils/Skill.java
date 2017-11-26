package pro.x_way.infinities_war.skils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import pro.x_way.infinities_war.units.Unit;


public abstract class Skill {
    public static float ROTATION_DX = 25f;
    public static float ANIMATION_TIME = 1.0f;

    protected boolean active = false;
    protected Unit currentUnit;
    protected Unit targetUnit;
    protected float skillAnimationTime;
    protected boolean buttonIsBig;
    protected Vector2 positionButton;
    protected String textureName;

    public Skill(String name, boolean buttonIsBig, float x, float y) {
        this.textureName = name;
        this.buttonIsBig = buttonIsBig;
        this.positionButton = new Vector2(x, y);
    }

    public abstract void action(Unit currentUnit, Unit targetUnit);

    public abstract void render(SpriteBatch batch);

    public void actionStart(Unit currentUnit, Unit targetUnit){
        this.currentUnit = currentUnit;
        if (targetUnit != null) this.targetUnit = targetUnit;
        else  this.targetUnit = currentUnit;

        active = true;
        skillAnimationTime = ANIMATION_TIME;

        action(this.currentUnit, this.targetUnit);
    }

    public void update(float dt) {
        if (active) {
            active = false;
            currentUnit.setRotationDx(0);
            currentUnit.setRotationDy(0);
            currentUnit.setUnitColor(Color.WHITE);
            if (targetUnit != null) {
                targetUnit.setRotationDx(0);
                targetUnit.setUnitColor(Color.WHITE);
                targetUnit.setRotationDy(0);
            }

        } else skillAnimationTime -= dt;
    }

    public boolean isActive() {
        return active;
    }

    public String getBtnTexture() {
        return textureName;
    }

    public Vector2 getPositionButton() {
        return positionButton;
    }

    public float getSize() {
        if (buttonIsBig) return 150f;
        else return 75f;
    }
}
