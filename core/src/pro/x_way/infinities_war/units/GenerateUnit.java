package pro.x_way.infinities_war.units;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pro.x_way.infinities_war.windows.BattleScreen;

import static pro.x_way.infinities_war.units.GenerateUnit.UnitPosition.*;

public class GenerateUnit {
    private UnitFactory unitFactory;
    private BattleScreen battleScreen;

    private static final GenerateUnit ourInstance = new GenerateUnit();

    public static GenerateUnit getInstance() {
        return ourInstance;
    }

    private GenerateUnit() {
    }

    public enum UnitPosition {
        HERO_COLUMN_ONE(0), HERO_COLUMN_TWO(1),
        ENEMY_COLUMN_ONE(2), ENEMY_COLUMN_TWO(2),
        ROW_ONE(0), ROW_TWO(1);

        int number;

        UnitPosition(int position) {
            this.number = position;
        }

        public int getNumber() {
            return number;
        }
    }


    public void makeMobs(){
        loadUserTeam();

        generateMobs();
    }

    private void generateMobs() {
        Map<String, Boolean> listPosition  = new HashMap<String, Boolean>();
        listPosition.put("positionOneIsBusy", false);
        listPosition.put("positionTwoIsBusy", false);
        listPosition.put("positionThreeIsBusy", false);
        listPosition.put("positionFourIsBusy", false);

        int maxExperience = Level.leve1.experienceAllMobs;

        Set<Unit> setEnemyTeam = new HashSet<Unit>();
        createUnitForBattle(setEnemyTeam, listPosition);

    }

    private void createUnitForBattle(Set<Unit> set, Map<String, Boolean> listPosition) {
        // создаем мобов +-1 уровень от главного персонажа игрока
        set.add(unitFactory.createUnitAndAddToBattle(UnitFactory.UnitType.SKELETON, battleScreen,
                false, ENEMY_COLUMN_ONE, ROW_ONE));

    }

    private void loadUserTeam() {
        unitFactory.createUnitAndAddToBattle(UnitFactory.UnitType.SKELETON, battleScreen,
                true, HERO_COLUMN_TWO, ROW_ONE);
        unitFactory.createUnitAndAddToBattle(UnitFactory.UnitType.KNIGHT, battleScreen,
                true, HERO_COLUMN_TWO, ROW_TWO);
        unitFactory.createUnitAndAddToBattle(UnitFactory.UnitType.SKELETON, battleScreen,
                true, HERO_COLUMN_ONE, ROW_ONE);
        unitFactory.createUnitAndAddToBattle(UnitFactory.UnitType.SKELETON, battleScreen,
                true, HERO_COLUMN_ONE, ROW_TWO);
    }

    public void setup (BattleScreen battleScreen) {
        this.unitFactory = new UnitFactory(battleScreen);
        this.battleScreen = battleScreen;
        makeMobs();
    }

    public UnitFactory getUnitFactory() {
        return unitFactory;
    }

    private enum Level{
        leve1(20), level2(40);

        int experienceAllMobs;

        Level(int experienceAllMobs) {
            this.experienceAllMobs = experienceAllMobs;
        }
    }
}
