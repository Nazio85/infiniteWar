package pro.x_way.infinities_war.actions;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.units.Unit;

public class RestAction extends BaseAction {
    public RestAction() {
        super("REST", Assets.BTN_HEAL);
    }

    @Override
    public boolean action(Unit me) {
        me.changeHp((int)(me.getMaxHp() * 0.15f));
        return true;
    }
}
