package pro.x_way.infinities_war.actions;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.effects.DefenceStanceEffect;
import pro.x_way.infinities_war.units.Unit;

public class DefenceStanceAction extends BaseAction {
    public DefenceStanceAction() {
        super("DEFENCE", Assets.BTN_DEFENCE);
    }

    @Override
    public boolean action(Unit me) {
        DefenceStanceEffect dse = new DefenceStanceEffect();
        dse.start(me, 1);
        me.addEffect(dse);
        return true;
    }
}
