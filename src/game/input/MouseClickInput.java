package game.input;

import game.input.listeners.MenuClickListener;
import game.input.listeners.MenuMouseListener;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseClickInput extends GLFWCursorPosCallback {

    public static MenuClickListener menuClickListener;

    public MouseClickInput()
    {
        menuClickListener = new MenuClickListener();
    }

    @Override
    public void invoke(long window, double x, double y) {
        System.out.println("Clicked " + x + ", " + y + "!");
        menuClickListener.checkMouse(x, y);
    }



}
