package pro.x_way.infinities_war.actions;

import com.badlogic.gdx.graphics.Texture;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.effects.DefenceStanceEffect;
import pro.x_way.infinities_war.units.Unit;

public class DefenceStanceAction extends BaseAction {
    public DefenceStanceAction() {
        super("DEFENCE", Assets.getInstance().getAssetManager().get("btnDefence.png", Texture.class));
    }

    @Override
    public boolean action(Unit me) {
        DefenceStanceEffect dse = new DefenceStanceEffect();
        dse.start(me, 1);
        me.addEffect(dse);
        return true;
    }
}
