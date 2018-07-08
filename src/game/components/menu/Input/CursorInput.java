package game.components.menu.Input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorInput extends GLFWCursorPosCallback {

    MenuMouseListener menuMouseListener;

    public CursorInput()
    {
        menuMouseListener = new MenuMouseListener();
    }

    @Override
    public void invoke(long window, double x, double y)
    {
        //System.out.println("X: " + x + "Y: " + y);
        menuMouseListener.checkMouse((int)x, (int)y);

    }

}
