package game.components.menu;

import org.joml.Vector2d;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends MenuComponent{

    Image image;

    Text text;

    public MenuButton(String id, Vector2d size, Vector2d position) {
        super(id, size, position);
    }

    public void doClick()
    {

    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public void setText(Text text)
    {
        this.text = text;
    }

    public void setString(String string)
    {
        if(text == null)
        {
            text = new Text(string);
        }
        else {
            this.text.setText(string);
        }
    }





}
