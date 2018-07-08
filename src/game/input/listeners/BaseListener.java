package game.input.listeners;

public class BaseListener {

    boolean active = false;

    public void setActive()
    {
        active = true;
    }

    public void setInactive()
    {
        active = false;
    }

    public boolean isActive()
    {
        return active;
    }
}
