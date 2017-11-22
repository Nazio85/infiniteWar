package pro.x_way.infinities_war.effects;


import pro.x_way.infinities_war.units.Unit;

public abstract class Effect {

    Unit unit;
    int rounds;

    public void start(Unit unit, int rounds) {
        this.unit = unit;
        this.rounds = rounds;
    }

    public void tick() {
        this.rounds--;
    }

    public boolean isEnded() {
        return rounds == 0;
    }

    public abstract void end();
}
