package pro.x_way.infinities_war.units;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import pro.x_way.infinities_war.windows.BattleScreen;
//
//import static pro.x_way.infinities_war.units.GenerateUnit.UnitPosition.*;
//
public class GenerateUnit {}
//    private UnitFactory unitFactory;
//
//    private static final GenerateUnit ourInstance = new GenerateUnit();
//
//    public static GenerateUnit getInstance() {
//        return ourInstance;
//    }
//
//    private GenerateUnit() {
//    }
//
//
//
//
//    public void makeMobs(){
//        loadUserTeam();
//        generateMobs();
//    }
//
//    private void generateMobs() {
//        Map<String, Boolean> listPosition  = new HashMap<String, Boolean>();
//        listPosition.put("positionOneIsBusy", false);
//        listPosition.put("positionTwoIsBusy", false);
//        listPosition.put("positionThreeIsBusy", false);
//        listPosition.put("positionFourIsBusy", false);
//
//        int maxExperience = Level.leve1.experienceAllMobs;
//
//        Set<Unit> setEnemyTeam = new HashSet<Unit>();
//        createUnitForBattle(setEnemyTeam, listPosition);
//
//    }
//
//    private void createUnitForBattle(Set<Unit> set, Map<String, Boolean> listPosition) {
//        // создаем мобов +-1 уровень от главного персонажа игрока
//        set.add(unitFactory.createUnit(UnitFactory.UnitType.SKELETON, false, 3));
//
//    }
//
//
//
//    public void setup (BattleScreen battleScreen) {
//        this.unitFactory = new UnitFactory(battleScreen);
//        makeMobs();
//    }
//
//    public UnitFactory getUnitFactory() {
//        return unitFactory;
//    }
//
//
//}
