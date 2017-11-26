package pro.x_way.infinities_war.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pro.x_way.infinities_war.units.Unit;

public class SpecialFXEmitter {
    private SpecialFX[] fxs;

    public SpecialFXEmitter() {
        this.fxs = new SpecialFX[100];
        for (int i = 0; i < fxs.length; i++) {
            this.fxs[i] = new SpecialFX();
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < fxs.length; i++) {
            if (fxs[i].isActive()) {
                fxs[i].render(batch);
            }
        }
    }

    public void setup(Unit me, Unit target, float maxTime, float scaleFrom, float scaleTo, boolean oneCycle) {
        for (int i = 0; i < fxs.length; i++) {
            if (!fxs[i].isActive()) {
                fxs[i].setup(me.getPosition().x + 45, me.getPosition().y + 75, // 45-75 чтоб летело не из ног
                        target.getPosition().x + 45, target.getPosition().y + 75,
                        maxTime, scaleFrom, scaleTo, oneCycle);
                break;
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < fxs.length; i++) {
            if (fxs[i].isActive()) {
                fxs[i].update(dt);
            }
        }
    }
}
