package test;

import test.graphics.Entity;
import test.graphics.Texture;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

//Example to show that a scene can stop the program, non-violently.
public class EndScene extends Scene {

	private Texture texture;
	boolean flip = true;
	private float i = 0;
	Entity entity;

	public EndScene(double targetTPS, double targetFPS, GameEngine gameEngine) {
		super(targetTPS, targetFPS, gameEngine);

		//Loads a texture from the jar. This texture is using
		texture = new Texture("./textures/testimage.png", false);

		entity = new Entity(new float[]{
				-0.5f, 0.5f, 0,
				0.5f, 0.5f, 0,

		});
	}

	@Override
	void tick() {
		/*if(flip)
		{
			if(i < 1f)
				i += 0.05f;
			else
				flip = false;
		}
		else
		{
			if(i > -1f)
				i -= 0.05f;
			else
				flip = true;
		}*/

		//Stops the running of the current scene. Simply comment this out to stop the game from shutting itself down.
		//stop();
	}

	@Override
	void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		glfwSetWindowTitle(getWindowID(),"FPS: " + getCurrentFPS());



		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(-0.5f + i,0.5f);
		glTexCoord2f(1,0);
		glVertex2f(0.5f + i,0.5f);
		glTexCoord2f(1,1);
		glVertex2f(0.5f + i,-0.5f);
		glTexCoord2f(0,1);
		glVertex2f(-0.5f + i,-0.5f);
		glEnd();

		glfwSwapBuffers(getWindowID());
	}

	@Override
	void onStop() {
		log("This scene is stopping the program!");
	}

}
