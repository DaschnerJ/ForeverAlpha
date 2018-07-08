package game.components.menu;

import org.joml.Vector2d;

import javax.swing.*;
import java.awt.*;

public class MenuField extends MenuComponent{

    TextField field;

    Text text;

    public MenuField(String id, Vector2d size, Vector2d position) {
        super(id, size, position);
    }

    public void setText(Text text)
    {
        this.text = text;
    }

    public Text getText()
    {
        return text;
    }

    public void setString(String string)
    {
        this.text.setText(string);
    }

    public String getString()
    {
        return this.text.getText();
    }




}
