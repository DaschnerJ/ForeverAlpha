package game.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardInput extends GLFWKeyCallback {

    //It is 65536 because that is the maximum number of different possible keys...
    public static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public void updateKeys()
    {
        if(isKeyDown(GLFW_KEY_SPACE))
            System.out.println("Space is pressed!");
    }

}
