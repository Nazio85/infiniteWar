package pro.x_way.infinities_war.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class StyleText {
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private static final StyleText ourInstance = new StyleText();

    public static StyleText getInstance() {
        return ourInstance;
    }

    private StyleText() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    public BitmapFont getTextSize20(){
        parameter.size = 20;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = -1;
        parameter.shadowOffsetY = 1;
        parameter.color = Color.WHITE;
        return generator.generateFont(parameter);
    }

    public BitmapFont createFont96() {
        parameter.size = 96;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = -3;
        parameter.shadowOffsetY = 3;
        parameter.color = Color.WHITE;
        return generator.generateFont(parameter);
    }

    public BitmapFont createFont36() {
        parameter.size = 36;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = -3;
        parameter.shadowOffsetY = 3;
        parameter.color = Color.WHITE;
        return generator.generateFont(parameter);
    }

    public void dispose(){
        generator.dispose();
    }
}
