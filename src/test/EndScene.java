package test;

import test.graphics.Entity;
import test.graphics.Shader;
import test.graphics.Texture;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

//Example to show that a scene can stop the program, non-violently.
public class EndScene extends Scene {

	private Texture texture;
	boolean flip1 = true;
	boolean flip2 = true;
	boolean flip3 = true;

	private float r = 0;
	private float g = 0;
	private float b = 0;
	private Entity entity;
	private Shader shader;

	public EndScene(double targetTPS, double targetFPS, GameEngine gameEngine) {
		super(targetTPS, targetFPS, gameEngine);

		//Loads a texture from the jar
		texture = new Texture("./textures/testimage.png", false);

		//Creates an entity with vertices, texture coords, and a texture.
		entity = new Entity(new float[]{
				-0.5f, 0.5f, //0
				0.5f, 0.5f, //1
				-0.5f, -0.5f, //2
				0.5f, -0.5f, //3
		}, new float[]{
				0,0, //0
				1,0, //1
				0,1, //2
				1,1, //3
		}, new int[]{
				0,1,2,
				2,1,3,
		}, texture);

		//Creates a shader for our use.
		shader = new Shader("./shaders/vertexshader.shader","./shaders/fragmentshader.shader");

		setFPSCap(false);
	}

	@Override
	void tick() {
		if(flip1)
		{
			if(r < 1f)
				r += 0.05f;
			else
				flip1 = false;
		}
		else
		{
			if(r > 0f)
				r -= 0.05f;
			else
				flip1 = true;
		}

		if(flip2)
		{
			if(g < 1f)
				g += 0.02f;
			else
				flip2 = false;
		}
		else
		{
			if(g > 0f)
				g -= 0.02f;
			else
				flip2 = true;
		}

		if(flip3)
		{
			if(b < 1f)
				b += 0.08f;
			else
				flip3 = false;
		}
		else
		{
			if(b > 0f)
				b -= 0.08f;
			else
				flip3 = true;
		}

		shader.setUniform("red",r);
		shader.setUniform("green",g);
		shader.setUniform("blue",b);

		//Stops the running of the current scene. Simply comment this out to stop the game from shutting itself down.
		//stop();
		if(getTicksSinceStart() % 60 == 0)
			glfwSetWindowTitle(getWindowID(),"ForeverAlpha | FPS: " + getCurrentFPS() + ", TPS: " + getTPS());
	}

	@Override
	void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		shader.bind();
		entity.render();

		glfwSwapBuffers(getWindowID());
	}

	@Override
	void onStop() {
		log("This scene is stopping the program!");
	}

}
