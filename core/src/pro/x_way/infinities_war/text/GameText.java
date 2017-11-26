package pro.x_way.infinities_war.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import pro.x_way.infinities_war.units.Unit;


public class GameText {
    public final String MISS = "Miss";
    private BitmapFont font;
    volatile private SpriteBatch batch;
    private float dt;
    private FlyingText flyingText;

    private static final GameText ourInstance = new GameText();

    public static GameText getInstance() {
        return ourInstance;
    }

    private GameText() {

    }

    public void gameTextSetup() {
        font = StyleText.getInstance().getTextSize20();
        flyingText = new FlyingText();
    }

    public void update(float dt) {
        this.dt = dt;
        flyingText.update();
    }

    public void render(SpriteBatch batch) {
        this.batch = batch;
    }

    public void printFlyingText() {
        if (flyingText.active) flyingText.render(batch);
    }

    //BattleScreen Place
    public void win(String winner) {
        font.draw(batch, winner + " WIN", 600, 420);
    }

    //BattleScreen
    public void damage(Unit unit, int lastDamage) {
        flyingText.add(unit, String.valueOf(lastDamage), Color.RED);
    }

    public void miss(Unit unit) {
        flyingText.add(unit, String.valueOf(MISS), Color.WHITE);
    }

    public void hill(Unit unit, int countHill) {
        flyingText.add(unit, String.valueOf(countHill), Color.GREEN);
    }
    public void normalFlyText(Unit unit, int countHill) {
        flyingText.add(unit, String.valueOf(countHill), Color.WHITE);
    }


    //StatusBar
    public void printStatusBar(Unit unit) {
        Vector2 position = unit.getPosition();
        font.draw(batch, String.valueOf(unit.getHp()), position.x + (unit.getWIDTH()/3), position.y + unit.getHEIGHT() + Unit.HEIGHT_STATUS_BAR_ROW, Unit.FULL_STATUS, 1, false);
        font.draw(batch, String.valueOf(unit.getMp()), position.x + (unit.getWIDTH()/3), position.y + unit.getHEIGHT(), Unit.FULL_STATUS, 1, false);
        font.setColor(Color.WHITE);
    }

    public void printEffectText(Unit unit, Color color, String message) {
        flyingText.add(unit, message, color);
    }

    public void dispose() {
        font.dispose();
    }

    class FlyingText {
        public static final float FULL_TIME_ANIMATION = 1f;
        private float time;
        private String text;
        private boolean active;
        float x;
        float y;
        private Color color;

        public FlyingText() {
            this.text = "";
            this.time = 0.0f;
            this.color = Color.WHITE;
        }

        public void add(Unit unit, String text, Color color) {
            this.text = text;
            this.time = FULL_TIME_ANIMATION;
            this.active = true;
            this.color = color;
            x = getCenterTextureX(unit);
            y = getCenterTextureY(unit);

        }

        public void update() {
            if (time > 0) {
                time -= dt;
            } else active = false;
        }

        public void render(SpriteBatch batch) {
            font.setColor(color);
            setRotation();
            font.draw(batch, text, x, y);
            font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        private void setRotation() {
            x += time;
            y += time * 2;
        }

        public float getCenterTextureX(Unit unit) {
            return unit.getPosition().x + unit.getWIDTH() / 2;
        }

        public float getCenterTextureY(Unit unit) {
            return unit.getPosition().y + unit.getHEIGHT() / 2 + 25;
        }


    }
}
