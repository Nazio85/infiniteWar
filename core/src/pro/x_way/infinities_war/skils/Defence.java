package pro.x_way.infinities_war.skils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.effects.DefenceStanceEffect;
import pro.x_way.infinities_war.units.Unit;


public class Defence extends Skill {
    private final static float positionBtnX = 1100;
    private final static float positionBtnY = 30;

    public Defence() {
        super(Assets.BTN_DEFENCE, true, positionBtnX, positionBtnY);
    }

    @Override
    public void action(Unit currentUnit, Unit targetUnit) {
        DefenceStanceEffect dse = new DefenceStanceEffect();
        dse.start(currentUnit, 1);
        currentUnit.addEffect(dse);
    }

    @Override
    public void render(SpriteBatch batch) {

    }

}
