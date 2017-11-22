package pro.x_way.infinities_war;

import java.util.List;

import pro.x_way.infinities_war.units.Unit;
import pro.x_way.infinities_war.windows.BattleScreen;

public class Calculator {
    private static final int MIN_MELEE_ATTACK_CHANCE = 20;
    private static final int MAX_MELEE_ATTACK_CHANCE = 95;
    private static BattleScreen upTarget;

    public static int getMeleeDamage(Unit attacker, Unit target) {
        int dmg = attacker.getStats().getStrength() - target.getStats().getDefence();
        dmg = (int) (dmg * 0.8f + (float) dmg * Math.random() * 0.4f);
        if (dmg < 1) {
            dmg = 1;
        }
        return dmg;
    }

    public static int calculateStatusBar(int maxStatus, int currentStatus) {
        float percentTexture = Unit.FULL_STATUS / 100f;
        float percentStatus = currentStatus * 100 / maxStatus;
        return (int) (percentTexture * percentStatus);
    }


    public static boolean isMiss(Unit currentUnit, Unit targetUnit) {
        int currentUnitDexterity = currentUnit.getStats().getDexterity();
        int targetUnitDexterity = targetUnit.getStats().getDexterity();

        if (targetUnitDexterity - currentUnitDexterity < 0 || // this.dexterity -- атакующий
                targetUnitDexterity - currentUnitDexterity == 0) {
            return (int) (Math.random() * 10) <= 1;
        } else {
            int difference = targetUnitDexterity - currentUnitDexterity; // во сколько раз принимающий атаку ловчей
            return (int) (Math.random() * 10) > difference;
        }
    }

    public static void hideUserPanel(List<Unit> units){
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getActionPanel() != null) {
                units.get(i).getActionPanel().setVisible(false);
            }
        }
    }

    public static void showUserPanel(Unit currentUnit){
        if (currentUnit.getActionPanel() != null) {
            currentUnit.getActionPanel().setVisible(true);
        }
    }

    public static Unit giveNextUnitStep(BattleScreen battleScreen){
        do {
            battleScreen.incrementCurrentUnitIndex();
            if (battleScreen.getCurrentUnitIndex() >= battleScreen.getUnits().size()) {
                battleScreen.setCurrentUnitIndex(0);
            }
        } while (!battleScreen.getUnits().get(battleScreen.getCurrentUnitIndex()).isAlive());
        return battleScreen.getUnits().get(battleScreen.getCurrentUnitIndex());
    }

    public static float timerDecrement(float timer, float dt) {
        float result = 0;
        if (timer > 0.0f) {
            result = timer;
            result -= dt;
        }
        return result;
    }

    public static void updateUnit(BattleScreen battleScreen, float dt) {
        for (int i = 0; i < battleScreen.getUnits().size(); i++) {
            battleScreen.getUnits().get(i).update(dt);
        }
    }

    public static void setupTarget(BattleScreen battleScreen) {
        for (int i = 0; i < battleScreen.getUnits().size(); i++) {
            Unit unit = battleScreen.getUnits().get(i);

            if (battleScreen.getMip().isTouchedInArea(unit.getRect()) && unit.isAlive()) {
                battleScreen.getCurrentUnit().setTarget(battleScreen.getUnits().get(i));
                break;
            }
        }
    }
}
