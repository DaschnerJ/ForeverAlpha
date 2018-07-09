package game.input.listeners;

import game.components.menu.MenuComponent;

import java.util.ArrayList;

public class MenuClickListener extends BaseListener{

    private ArrayList<MenuComponent> components = new ArrayList<>();

    public MenuClickListener()
    {

    }

    public void addComponent(MenuComponent component)
    {
        components.add(component);
    }

    public void removeComponent(MenuComponent component)
    {
        if(components.contains(component))
            components.remove(component);
    }

    public void checkMouse(double x, double y)
    {
        if(isActive()) {
            components.forEach(c ->
            {
                boolean intersects = intersects(c.getX(), c.getY(), c.getXOff(), c.getYOff(), x, y);
                if(intersects)
                {
                    c.mouseClick();
                }
            });
        }
    }

    private boolean intersects(double orgX, double orgY, double x, double y, double px, double py) {
        if(orgX < px && orgY < py && y > py && x > px)
            return true;
        else return false;
    }

}
