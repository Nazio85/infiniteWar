package pro.x_way.infinities_war.units;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.actions.*;
import pro.x_way.infinities_war.windows.BattleScreen;


public class UnitFactory {
    public enum UnitType {
        KNIGHT, SKELETON
    }

    private BattleScreen battleScreen;
    private Map<UnitType, Unit> data;
    private List<Autopilot> aiBank;
    private List<BaseAction> actions;

    public List<BaseAction> getActions() {
        return actions;
    }

    public UnitFactory(BattleScreen battleScreen) {
        this.battleScreen = battleScreen;
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
        this.createUnitPatterns();
    }

    public void createActions() {
        this.actions = new ArrayList<BaseAction>();
        this.actions.add(new MeleeAttackAction());
        this.actions.add(new DefenceStanceAction());
        this.actions.add(new RestAction());
    }

    public void createUnitPatterns() {
        // все пер левл не должны превышать в сумме 10 (опыт не учитывается)
        data = new HashMap<UnitType, Unit>();
        Stats knightStats = new Stats(1, 20, 10, 30,
                2, 5,10,
                3, 2, 2, 2, 1,
                5);
        Unit knight = new Unit(Assets.getInstance().getAtlas().findRegion("knightAnim"), knightStats);
        knight.getActions().add(actions.get(0));
        knight.getActions().add(actions.get(1));
        knight.getActions().add(actions.get(2));
        data.put(UnitType.KNIGHT, knight);

        Stats skeletonStats = new Stats(1, 10, 20, 10,
                1, 0, 8,
                2, 3,3, 1, 1,
                4);
        Unit skeleton = new Unit(Assets.getInstance().getAtlas().findRegion("skeleton"), skeletonStats);
        skeleton.getActions().add(actions.get(0));
        skeleton.getActions().add(actions.get(1));
        skeleton.getActions().add(actions.get(2));
        data.put(UnitType.SKELETON, skeleton);
    }

    public Unit createUnit(UnitType unitType, boolean isHuman) {
        Unit unitPattern = data.get(unitType);
        Unit unit = new Unit(unitPattern.getTexture(), (Stats) unitPattern.getStats().clone());
        unit.setActions(unitPattern.getActions());
        if (!isHuman) {
            unit.setAutopilot(aiBank.get(0));
        }
        return unit;
    }

    public Unit createUnitAndAddToBattle(UnitType unitType, BattleScreen battleScreen,
                                         boolean isPlayer, GenerateUnit.UnitPosition x, GenerateUnit.UnitPosition y) {
        Unit unit = createUnit(unitType, isPlayer);
        unit.setToMap(battleScreen, x.getNumber(), y.getNumber());
        unit.setPlayer(isPlayer);
        if (!isPlayer) {
            unit.setFlip(true);
        }
        battleScreen.getUnits().add(unit);
        return unit;
    }
}
