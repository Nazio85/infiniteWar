package pro.x_way.infinities_war.actions;

import com.badlogic.gdx.graphics.Texture;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.units.Unit;

public class RestAction extends BaseAction {
    public RestAction() {
        super("REST", Assets.getInstance().getAssetManager().get("btnHeal.png", Texture.class));
    }

    @Override
    public boolean action(Unit me) {
        me.changeHp((int)(me.getMaxHp() * 0.15f));
        return true;
    }
}
