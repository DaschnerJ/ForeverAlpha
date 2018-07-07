package test;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

//Example to show that a scene can stop the program, non-violently.
public class EndScene extends Scene {

	public EndScene(double targetTPS, double targetFPS, GameEngine gameEngine) {
		super(targetTPS, targetFPS, gameEngine);
	}

	@Override
	void tick() {
		//Stops the running of the current scene. Simply comment this out to stop the game from shutting itself down.
		//stop();
	}

	@Override
	void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		glfwSwapBuffers(getWindowID());
	}

	@Override
	void onStop() {
		log("This scene is stopping the program!");
	}

}
