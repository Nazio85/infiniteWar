package pro.x_way.infinities_war.effects;


import com.badlogic.gdx.graphics.Color;

import pro.x_way.infinities_war.text.GameText;
import pro.x_way.infinities_war.units.Unit;

public class DefenceStanceEffect extends Effect {
    @Override
    public void start(Unit unit, int rounds) {
        super.start(unit, rounds);
        unit.getStats().setDefence(unit.getStats().getDefence() + 3);
        GameText.getInstance().printEffectText(unit, Color.GREEN, "Shields UP!");
    }

    @Override
    public void end() {
        unit.getStats().setDefence(unit.getStats().getDefence() - 3);
        GameText.getInstance().printEffectText(unit, Color.WHITE, "Shields DOWN!");
    }
}
