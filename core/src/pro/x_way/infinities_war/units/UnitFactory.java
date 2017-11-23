package pro.x_way.infinities_war.units;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.actions.BaseAction;
import pro.x_way.infinities_war.actions.DefenceStanceAction;
import pro.x_way.infinities_war.actions.MeleeAttackAction;
import pro.x_way.infinities_war.actions.RestAction;


public class UnitFactory {
    public enum UnitType {
        KNIGHT(Assets.getInstance().getAtlas().findRegion(Assets.KNIGHT)),
        SKELETON(Assets.getInstance().getAtlas().findRegion(Assets.SKELETON));

        private TextureAtlas.AtlasRegion textureAtlas;


        UnitType(TextureAtlas.AtlasRegion textureAtlas) {
            this.textureAtlas = textureAtlas;
        }
    }

    private List<Autopilot> aiBank;
    private List<BaseAction> actions;

    public List<BaseAction> getActions() {
        return actions;
    }

    public UnitFactory() {
        this.createActions();
        this.aiBank = new ArrayList<Autopilot>();
        this.aiBank.add(new Autopilot() {
            @Override
            public boolean turn(Unit me) {
                if (!me.getBattleScreen().canIMakeTurn()) {
                    return false;
                }
                Unit target = null;
                do {
                    target = me.getBattleScreen().getUnits().get((int) (Math.random() * me.getBattleScreen().getUnits().size()));
                } while (target.isAI());
                me.setTarget(target);
                me.getActions().get(0).action(me);
                return true;
            }
        });
    }

    public void createActions() {
        this.actions = new ArrayList<BaseAction>();
        this.actions.add(new MeleeAttackAction());
        this.actions.add(new DefenceStanceAction());
        this.actions.add(new RestAction());
    }

    private Stats getStats(UnitType unitType) {
        Stats stats;
        // все пер левл не должны превышать в сумме 10 (опыт не учитывается)
        switch (unitType) {
            case KNIGHT:
                stats = new Stats(1, 20, 10, 30,
                        2, 5, 10,
                        3, 2, 2, 2, 1,
                        5);
                return stats;
            case SKELETON:
                stats = new Stats(1, 10, 20, 10,
                        1, 0, 8,
                        2, 3, 3, 1, 1,
                        4);
                return stats;
            default:
                return null;
        }
    }

    public void addActions(UnitType unitType, Unit unit){
        switch (unitType){
            case KNIGHT:
                unit.getActions().add(actions.get(0));
                unit.getActions().add(actions.get(1));
                unit.getActions().add(actions.get(2));
                break;
            case SKELETON:
                unit.getActions().add(actions.get(0));
                unit.getActions().add(actions.get(1));
                unit.getActions().add(actions.get(2));
                break;
        }

    }

    public Unit createUnit(UnitType unitType, boolean isHuman, int level) {
        Unit unit = new Unit(unitType, unitType.textureAtlas, getStats(unitType));
        unit.setLevel(level);
        addActions(unitType, unit);

        if (!isHuman) {
            unit.setAutopilot(aiBank.get(0));
        }
        unit.setFlip(isHuman);
        unit.setPlayer(isHuman);
        return unit;
    }


}
