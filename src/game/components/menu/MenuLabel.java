package game.components.menu;

import org.joml.Vector2d;

public class MenuLabel extends MenuComponent{

    Text text;

    public MenuLabel(String id, Vector2d size, Vector2d position, Text text) {
        super(id, size, position);
        this.text = text;
    }

    public Text getText()
    {
        return text;
    }

    public String getString()
    {
        return text.text;
    }

    public void setText(Text text)
    {
        this.text = text;
    }

    public void setString(String string)
    {
        this.text.setText(string);
    }
}
