package pro.x_way.infinities_war.effects;


import com.badlogic.gdx.graphics.Color;

import pro.x_way.infinities_war.text.GameText;
import pro.x_way.infinities_war.units.Unit;

public class RegenerationEffect extends Effect {
    @Override
    public void start( Unit unit, int rounds) {
        super.start(unit, rounds);
        GameText.getInstance().printEffectText(unit, Color.GREEN, "Regeneration 3T/+2HP");
    }

    @Override
    public void tick() {
        super.tick();
        GameText.getInstance().printEffectText(unit, Color.GREEN, "Regeneration");
        unit.changeHp((int)(unit.getMaxHp() * 0.05f));
    }

    @Override
    public void end() {
        GameText.getInstance().printEffectText(unit, Color.WHITE, "Regeneration end");
    }
}
