package pro.x_way.infinities_war.units;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

import pro.x_way.infinities_war.Assets;
import pro.x_way.infinities_war.skils.FabricSkills;
import pro.x_way.infinities_war.skils.Skill;


public class UnitFactory {


    public enum UnitType {
        SWORD_MAN, ANVIL_MAN, MOUNTAIN
    }

    private List<Autopilot> aiBank;
    private List<Skill> actions;

    public TextureAtlas.AtlasRegion getTexture(UnitType unitType) {
        switch (unitType) {
            case SWORD_MAN:
                return Assets.getInstance().getAtlas().findRegion("Unit1");
            case ANVIL_MAN:
                return Assets.getInstance().getAtlas().findRegion("Unit3");
            case MOUNTAIN:
                return Assets.getInstance().getAtlas().findRegion("Unit6");
        }
        return null;
    }

    public UnitFactory() {
        this.createActions();
        this.aiBank = new ArrayList<Autopilot>();
        this.aiBank.add(new Autopilot() {
            @Override
            public boolean turn(Unit currentUnit) {
                if (!currentUnit.getBattleScreen().canIMakeTurn()) {
                    return false;
                }
                Unit target;
                do {
                    target = currentUnit.getBattleScreen().getUnits().get((int) (Math.random() * currentUnit.getBattleScreen().getUnits().size()));
                } while (!target.isPlayer());
                currentUnit.setTarget(target);
                currentUnit.getActions().get(0).actionStart(currentUnit, currentUnit.getTarget());
                return true;
            }
        });
    }

    public void createActions() {
        this.actions = new ArrayList<Skill>();
        this.actions.add(FabricSkills.getSkill(FabricSkills.SkillType.actionMeleeAttack));
        this.actions.add(FabricSkills.getSkill(FabricSkills.SkillType.defence));
        this.actions.add(FabricSkills.getSkill(FabricSkills.SkillType.rest));
    }

    private Stats getStats(UnitType unitType) {
        Stats stats;
        // все пер левл не должны превышать в сумме 10 (опыт не учитывается)
        switch (unitType) {
            case SWORD_MAN:
                stats = new Stats(1, 10, 10, 10,
                        2, 5, 10,
                        3, 2, 2, 2, 1,
                        5);
                return stats;
            case ANVIL_MAN:
                stats = new Stats(1, 10, 20, 10,
                        1, 0, 8,
                        2, 3, 3, 1, 1,
                        4);
                return stats;
            case MOUNTAIN:
                stats = new Stats(1, 30, 20, 10,
                        1, 0, 20,
                        5, 5, 1, 1, 1,
                        10);
                return stats;
            default:
                return null;
        }
    }

    public void addActions(UnitType unitType, Unit unit) {
        switch (unitType) {
            case SWORD_MAN:
                unit.getActions().add(actions.get(0));
                unit.getActions().add(actions.get(1));
                unit.getActions().add(actions.get(2));
                break;
            case ANVIL_MAN:
                unit.getActions().add(actions.get(0));
                unit.getActions().add(actions.get(1));
                unit.getActions().add(actions.get(2));
                break;
        }


    }

    public Unit createUnit(UnitType unitType, boolean isPlayer, int level) {
        Unit unit = new Unit(getTexture(unitType), unitType, getStats(unitType));
        unit.setLevel(level);
        addActions(unitType, unit);

        if (!isPlayer) {
            unit.setAutopilot(aiBank.get(0));
        }
        unit.setPlayer(isPlayer);
        return unit;
    }

    public void reload(Unit unit) {
        UnitType unitType = unit.getUnitType();
        unit.reload(getTexture(unitType));
        addActions(unitType, unit);
        if (!unit.isPlayer()) {
            unit.setAutopilot(aiBank.get(0));
        }
    }


}
