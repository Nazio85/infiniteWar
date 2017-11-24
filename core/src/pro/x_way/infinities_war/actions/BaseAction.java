package pro.x_way.infinities_war.actions;

import pro.x_way.infinities_war.units.Unit;

public abstract class BaseAction {
    String textureName;

    public BaseAction( String textureName) {
        this.textureName = textureName;
    }


    public String getBtnTexture() {
        return textureName;
    }

    public abstract boolean action(Unit me);
}
