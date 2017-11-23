package pro.x_way.infinities_war.actions;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.Calculator;
import pro.x_way.infinities_war.units.Unit;

public class MeleeAttackAction extends BaseAction {
    public MeleeAttackAction() {
        super("MELEE_ATTACK", Assets.BTN_MELEE_ATTACK);
    }

    @Override
    public boolean action(Unit me) {
        if (me.getTarget() == null) return false;
        if (me.isMyTeammate(me.getTarget().isPlayer())) return false;
        me.setAttackAction(1.0f);
        me.setCurrentAnimation(Unit.AnimationType.ATTACK);
        if (!Calculator.isMiss(me, me.getTarget())) {
            int dmg = Calculator.getMeleeDamage(me, me.getTarget());
            me.getTarget().changeHp(-dmg);
        } else {
            me.getTarget().evade();
        }
        me.getBattleScreen().getSpecialFXEmitter().setup(me, me.getTarget(), 1.0f, 2f, 2f, true);
//        me.getBattleScreen().getSpecialFXEmitter().setup(me.getTarget(), me.getTarget(), 1.0f, 2f, 8f, true);
        return true;
    }
}
