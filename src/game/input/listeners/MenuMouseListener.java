package game.input.listeners;

import game.components.menu.MenuComponent;



import java.util.ArrayList;

public class MenuMouseListener extends BaseListener{

    private ArrayList<MenuComponent> components = new ArrayList<>();

    private ArrayList<MenuComponent> in = new ArrayList<>();

    public MenuMouseListener()
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
                if (in.contains(c)) {
                    if (!intersects) {
                        in.remove(c);
                        c.mouseOff();
                    }
                } else {
                    if (intersects) {
                        in.add(c);
                        c.mouseOn();
                    }
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
