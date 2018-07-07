package test;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

//Example to show how a simple scene would be run. Also showcases the simplicity of switching scenes.
public class StartScene extends Scene {

	int i = 0;

	public StartScene(double targetTPS, double targetFPS, GameEngine gameEngine) {
		super(targetTPS, targetFPS, gameEngine);
	}

	@Override
	void tick() {
		log(i++);
		if(i > 20) {
			getGameEngine().getSceneManager().switchScene(new EndScene(getTPS(), 60, getGameEngine()));
			//Here to showcase that the final tick is finished before switching to another scene.
			System.out.println("StartScene: This tick is being finished.");
		}

	}

	@Override
	void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		glfwSwapBuffers(getWindowID());
	}

	@Override
	void onStop() {
		log("This scene has been told to stop running new ticks!");
	}

}
