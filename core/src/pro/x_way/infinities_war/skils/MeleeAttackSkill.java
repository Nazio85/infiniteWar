package pro.x_way.infinities_war.skils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.Calculator;
import pro.x_way.infinities_war.units.Unit;


public class MeleeAttackSkill extends Skill {
    private final static float positionBtnX = 30;
    private final static float positionBtnY = 30;
    private boolean isDodge = false;

    public MeleeAttackSkill() {
        super(Assets.BTN_MELEE_ATTACK, true, positionBtnX, positionBtnY);
    }


    @Override
    public void action(Unit currentUnit, Unit targetUnit) {
        if (!Calculator.isMiss(currentUnit, targetUnit)) {
            int dmg = currentUnit.getStats().getStrength() - targetUnit.getStats().getDefence();
            if (dmg < 0) dmg = 0;
            int totalDamage = Calculator.getTotalDamage(dmg);
            targetUnit.changeHp(totalDamage, Unit.TypeDamage.Damage);
        } else isDodge = true;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isDodge) {
            currentUnit.setRotationDx(ROTATION_DX * (float) Math.sin((1f - skillAnimationTime) * 3.14f));
            Color color = new Color(Color.rgba8888(1f, 1f - skillAnimationTime, 1f - skillAnimationTime, 1));
            targetUnit.setUnitColor(color); //покраснение}
        } else {
            currentUnit.setRotationDx(ROTATION_DX * (float) Math.sin((1f - skillAnimationTime) * 3.14f));
            targetUnit.setRotationDx((ROTATION_DX * (float) Math.sin((1f - skillAnimationTime) * 3.14f)) * -1);
            isDodge = false;
        }
    }
}
