package pro.x_way.infinities_war;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import pro.x_way.infinities_war.windows.ScreenManager;


public class Assets {
    private static final Assets ourInstance = new Assets();

    public static final String WINDOW_NEXT_LEVEL = "windowNextLevel";
    public static final String BTN_STAY_HERE = "btnStayHere";
    public static final String BTN_NEXT_LEVEL = "btnNextLevel";
    public static final String MENU_BTN = "menuBtn";
    public static final String EXPLOSION64 = "explosion64";
    public static final String KNIGHT_ANIM = "knightAnim";
    public static final String KNIGHT = "knight";
    public static final String SKELETON = "skeleton";
    public static final String ACTION_PANEL = "actionPanel";
    public static final String BTN_RANGED = "btnRanged";
    public static final String BTN_REGEN = "btnRegen";
    public static final String BTN_MELEE_ATTACK = "btnMeleeAttack";
    public static final String BTN_DEFENCE = "btnDefence";
    public static final String BTN_HEAL = "btnHeal";
    public static final String SELECTOR = "selector";
    public static final String HP_BAR = "hpBar";
    public static final String BACKGROUND_PNG = "background.png";
    public static final String PACK_PACK = "pack.pack";
    public static final String GO_TO_MENU = "goToMenu";

    public static Assets getInstance() {
        return ourInstance;
    }

    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public TextureAtlas getAtlas() {
        return textureAtlas;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private Assets() {
        assetManager = new AssetManager();
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type) {
            case MENU:
                assetManager.load(PACK_PACK, TextureAtlas.class);
                assetManager.load(BACKGROUND_PNG, Texture.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get(PACK_PACK, TextureAtlas.class);
                break;
            case BATTLE:
                assetManager.load(BACKGROUND_PNG, Texture.class);
                assetManager.load(PACK_PACK, TextureAtlas.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get(PACK_PACK, TextureAtlas.class);
                break;
        }
    }

    public void clear() {
        assetManager.clear();
    }
}
