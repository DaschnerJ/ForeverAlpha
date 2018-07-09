package game.components.menu;

import org.joml.Vector2d;

import javax.swing.*;

public class MenuSlider extends MenuComponent {

    int selected, min, max;



    public MenuSlider(String id, Vector2d size, Vector2d position, int selected, int min, int max) {
        super(id, size, position);
        this.selected = selected;
        this.min = min;
        this.max = max;
    }

    public int getSelected()
    {
        return selected;
    }

    public int getMax()
    {
        return max;
    }

    public int getMin()
    {
        return min;
    }

    public void setSelected(int selected)
    {
        this.selected = selected;
    }

}
