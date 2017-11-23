package pro.x_way.infinities_war.actions;

import pro.x_way.infinities_war.units.Unit;

public abstract class BaseAction {
    String name;
    String textureName;

    public BaseAction(String name, String textureName) {
        this.name = name;
        this.textureName = textureName;
    }

    public String getName() {
        return name;
    }

    public String getBtnTexture() {
        return textureName;
    }

    public abstract boolean action(Unit me);
}
