package test;

import game.input.CursorInput;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameEngine {

	private SceneManager sceneManager;
	private Window window;

    private GLFWCursorPosCallback cursorPos;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		sceneManager = new SceneManager();

		start("yeeter");

		if(window != null)
			glfwSetWindowShouldClose(window.getID(), true);
		glfwTerminate();
	}

	private void start(String name) {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable



		// Create the window
		long windowID = glfwCreateWindow(600, 600, name, NULL, NULL);
		if ( windowID == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		window = new Window(windowID);


        glfwSetCursorPosCallback(getWindow().getID(), cursorPos = new CursorInput());

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(windowID, (wind, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(wind, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(windowID, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
					windowID,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowID);
		// Enable v-sync
		//glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(windowID);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		//Tells OpenGL that we intend to use 2D textures.
		glEnable(GL_TEXTURE_2D);

		// Set the clear color. This is to say that when the screen is being rendered, this color will overwrite
		// the existing content.
		glClearColor(0, 0, 0, 0.0f);

		//This callback is called when the glfw is
		GLFW.glfwSetWindowCloseCallback(windowID, new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long window) {
				sceneManager.stop();

				// Free the window callbacks and destroy the window
				glfwFreeCallbacks(window);
				glfwDestroyWindow(window);
			}
		});

		//Example of mouse input callback

//		glfwSetCursorPosCallback(windowID, new GLFWCursorPosCallback() {
//			@Override
//			public void invoke(long window, double xpos, double ypos) {
//
//			}
//		});

		glfwSetMouseButtonCallback(windowID, new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {

			}
		});

		sceneManager.start(new StartScene(60,60, this));
	}

	public SceneManager getSceneManager()
	{
		return sceneManager;
	}

	public static void main(String[] args) {
		new GameEngine().run();
	}

	public Window getWindow() {
		return window;
	}
}