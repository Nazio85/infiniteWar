package pro.x_way.infinities_war.actions;

import com.badlogic.gdx.graphics.Texture;

import pro.x_way.infinities_war.units.Unit;

public abstract class BaseAction {
    String name;
    Texture btnTexture;

    public BaseAction(String name, Texture btnTexture) {
        this.name = name;
        this.btnTexture = btnTexture;
    }

    public String getName() {
        return name;
    }

    public Texture getBtnTexture() {
        return btnTexture;
    }

    public abstract boolean action(Unit me);
}
