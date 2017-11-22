package pro.x_way.infinities_war.gui;


import pro.x_way.infinities_war.windows.BattleScreen;

public class GUI {
    private static final GUI ourInstance = new GUI();

    public static GUI getInstance() {
        return ourInstance;
    }

    private GUI() {

    }

    public void getBattleGui(BattleScreen battleScreen){
        new BattleGui(battleScreen);
    }
}
