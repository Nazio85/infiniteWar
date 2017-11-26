package pro.x_way.infinities_war.skils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.units.Unit;


public class RestSkill extends Skill {
    private final static float positionBtnX = 1100;
    private final static float positionBtnY = 200;

    public RestSkill() {
        super(Assets.BTN_REST, false, positionBtnX, positionBtnY);
    }


    @Override
    public void action(Unit currentUnit, Unit targetUnit) {
        int countHill = (int) ((float) currentUnit.getMaxHp() / 100 * 15);
        currentUnit.changeHp(countHill, Unit.TypeDamage.Hill);
    }

    @Override
    public void render(SpriteBatch batch) {

    }

}
