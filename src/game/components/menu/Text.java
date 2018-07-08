package game.components.menu;

import java.awt.*;

public class Text {

    String text;
    Font font;
    Color color;

    public Text(String text)
    {
        this.text = text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setFont(Font font)
    {
        this.font = font;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public String getText()
    {
        return text;
    }

    public Font getFont()
    {
        return font;
    }

    public Color getColor()
    {
        return color;
    }
}
