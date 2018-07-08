package game.components.menu;

import org.joml.Vector2d;

public class MenuComponent {

    //The ID in order to inventory all the components and keep track of them.
    String id;

    //How big the component is in the particular position. Both of these are handled
    //As integers (x,y).
    Vector2d size;
    Vector2d position;

    //Is the component visible or hidden from the user?
    boolean visible;

    public MenuComponent(String id, Vector2d size, Vector2d position)
    {
        this.id = id;
        this.size = size;
        this.position = position;
        //By default components are hidden to allow modification then display.
        visible = false;
    }

    public MenuComponent getComponent()
    {
        return this;
    }

    public void setPosition(int x, int y)
    {
        position = new Vector2d(x, y);
    }

    @Deprecated
    public void setSize(int x, int y)
    {
        size = new Vector2d(x, y);
    }

    public Vector2d getPosition()
    {
        return position;
    }

    public Vector2d getSize()
    {
        return size;
    }

    public String getId()
    {
        return id;
    }

    public void hide()
    {
        visible = false;
    }

    public void show()
    {
        visible = true;
    }

    public void mouseOn()
    {
        //What to do when the component is moused on.
    }

    public void mouseOff()
    {
        //What to do when the component is moused off.
    }

    public void draw()
    {
        if(visible)
        {
            glDraw();
        }
        //Draw the component here...
    }

    private void glDraw()
    {

    }


}
