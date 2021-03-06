package pro.x_way.infinities_war;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pro.x_way.infinities_war.units.Unit;
import pro.x_way.infinities_war.units.UnitFactory;
import pro.x_way.infinities_war.windows.BattleScreen;

//import static pro.x_way.infinities_war.units.GenerateUnit.UnitPosition.HERO_COLUMN_TWO;
//import static pro.x_way.infinities_war.units.GenerateUnit.UnitPosition.ROW_TWO;


public class Session implements Serializable {
    private static final Session ourInstance = new Session();
    private static UnitFactory unitFactory = new UnitFactory();

    private Unit[] columnOne = new Unit[2];
    private Unit[] columnTwo = new Unit[2];
    private Unit[] columnTree = new Unit[2];
    private Unit[] columnFour = new Unit[2];

    public static Session getInstance() {
        return ourInstance;
    }

    private Session() {
    }

    public void startGame(){
        createPlayerTeam();
        createAI();
    }

    private void createAI() {
        columnTree[1] = unitFactory.createUnit(UnitFactory.UnitType.ANVIL_MAN, false, 3);
        columnTree[0] = unitFactory.createUnit(UnitFactory.UnitType.ANVIL_MAN, false, 3);
        columnFour[0] = unitFactory.createUnit(UnitFactory.UnitType.ANVIL_MAN, false, 3);
        columnFour[1] = unitFactory.createUnit(UnitFactory.UnitType.SWORD_MAN, false, 3);
    }

    private void createPlayerTeam() {
        columnOne[0] = unitFactory.createUnit(UnitFactory.UnitType.SWORD_MAN, true, 1);
        columnOne[1] = unitFactory.createUnit(UnitFactory.UnitType.ANVIL_MAN, true, 1);
        columnTwo[0] = unitFactory.createUnit(UnitFactory.UnitType.SWORD_MAN, true, 1);
        columnTwo[1] = unitFactory.createUnit(UnitFactory.UnitType.ANVIL_MAN, true, 1);
    }

    public void setToMap(BattleScreen battleScreen) {
        if (columnOne[0] != null) columnOne[0].setToMap(battleScreen, UnitPosition.HERO_COLUMN_ONE.getNumber(), UnitPosition.ROW_ONE.getNumber());
        if (columnOne[1] != null) columnOne[1].setToMap(battleScreen, UnitPosition.HERO_COLUMN_ONE.getNumber(), UnitPosition.ROW_TWO.getNumber());
        if (columnTwo[0] != null) columnTwo[0].setToMap(battleScreen, UnitPosition.HERO_COLUMN_TWO.getNumber(), UnitPosition.ROW_ONE.getNumber());
        if (columnTwo[1] != null) columnTwo[1].setToMap(battleScreen, UnitPosition.HERO_COLUMN_TWO.getNumber(), UnitPosition.ROW_TWO.getNumber());
        if (columnTree[0] != null) columnTree[0].setToMap(battleScreen, UnitPosition.ENEMY_COLUMN_ONE.getNumber(), UnitPosition.ROW_ONE.getNumber());
        if (columnTree[1] != null) columnTree[1].setToMap(battleScreen, UnitPosition.ENEMY_COLUMN_ONE.getNumber(), UnitPosition.ROW_TWO.getNumber());
        if (columnFour[0] != null) columnFour[0].setToMap(battleScreen, UnitPosition.ENEMY_COLUMN_TWO.getNumber(), UnitPosition.ROW_ONE.getNumber());
        if (columnFour[1] != null) columnFour[1].setToMap(battleScreen, UnitPosition.ENEMY_COLUMN_TWO.getNumber(), UnitPosition.ROW_TWO.getNumber());
    }

    public List<Unit> getAllUnit(){
        List<Unit> units = new ArrayList<Unit>();
        for (int i = 0; i < columnOne.length; i++) {
            if (columnOne[i] != null) units.add(columnOne[i]);
            if (columnTwo[i] != null) units.add(columnTwo[i]);
            if (columnTree[i] != null) units.add(columnTree[i]);
            if (columnFour[i] != null) units.add(columnFour[i]);
        }
        return units;
    }

    public void reloadUnit() {
        List<Unit> units = getAllUnit();
        for (int i = 0; i < units.size(); i++) {
            unitFactory.reload(units.get(i));
        }
    }

    private enum Level{
        leve1(20), level2(40);

        int experienceAllMobs;

        Level(int experienceAllMobs) {
            this.experienceAllMobs = experienceAllMobs;
        }
    }

    public enum UnitPosition {
        HERO_COLUMN_ONE(0), HERO_COLUMN_TWO(1),
        ENEMY_COLUMN_ONE(2), ENEMY_COLUMN_TWO(3),
        ROW_ONE(0), ROW_TWO(1);

        int number;

        UnitPosition(int position) {
            this.number = position;
        }

        public int getNumber() {
            return number;
        }
    }

    public static UnitFactory getUnitFactory() {
        return unitFactory;
    }
}
