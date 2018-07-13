package test;

import org.joml.Matrix4f;
import test.graphics.Camera;
import test.graphics.Entity;
import test.graphics.Model;
import test.graphics.Shader;
import test.graphics.entitycomponents.AnimationComponent;
import test.graphics.instancerendering.InstanceEntityRenderer;
import test.graphics.textures.Texture;
import test.graphics.textures.TextureCoords;
import test.graphics.textures.TextureCoordsHolder;
import test.utils.UtilsSprite;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

//Example to show that a scene can stop the program, non-violently.
public class EndScene extends Scene {

	private FloatBool r = new FloatBool(1);
	private FloatBool g = new FloatBool(1);
	private FloatBool b = new FloatBool(1);
	private FloatBool varyZoom = new FloatBool(1);
	private FloatBool varyMove = new FloatBool(0);
	private FloatBool varyRotate = new FloatBool(0);
	private Camera camera;
	private Shader shader;
	private Model model;
	private InstanceEntityRenderer entities;
	private TextureCoordsHolder textureCoordsHolder;
	private TextureCoords[] sprites;
	private Texture texture2;

	public EndScene(double targetTPS, double targetFPS, GameEngine gameEngine) {
		super(targetTPS, targetFPS, gameEngine);

		//Loads a texture from the jar
		//texture = new Texture("./textures/testimage2.png", true);
		texture2 = new Texture("./textures/testimage5.png", true);

		model = new Model(new float[]{
				//Vertices
				-0.5f, 0.5f, //0
				0.5f, 0.5f, //1
				-0.5f, -0.5f, //2
				0.5f, -0.5f, //3
		}, new int[]{
				//Indices
				0,1,2,
				2,1,3,
		});

		// 0,0, Top Left
		// 1,0, Top Right
		// 0,1, Bottom Left
		// 1,1, Bottom Right
		textureCoordsHolder = new TextureCoordsHolder();
		sprites = UtilsSprite.getTextureCoordsForSprites(textureCoordsHolder,texture2,64,64);
		//Remember that this is used in the tick() portion of this class.
		System.out.println("texcoordssize: " + textureCoordsHolder.size());

		entities = new InstanceEntityRenderer(model);
		float x = 0;
		float max = 10;
		int number = 0;
		while(x++ < max) {
			Entity entity = new Entity(model, texture2, sprites[number % sprites.length]);
			AnimationComponent component = new AnimationComponent(sprites,60f);
			component.setFrame(number++);
			entity.setAngle((360f/max)*x);
			entity.setSize(1.8f);
			double radians = Math.toRadians(entity.getAngle());
			entity.setPosition((float) (Math.cos(radians) * 3),(float) (Math.sin(radians) * 3));
			entity.addComponent(component);
			entities.add(entity);
		}

		//Creates a shader for our use.
		shader = new Shader("./shaders/vertexshader.shader","./shaders/fragmentshader.shader");
		camera = new Camera(new Matrix4f().ortho2D(-200,200,-200,200));
		setFPSCap(false);
	}

	@Override
	void initialize() {
		shader.bind();
		shader.setUniform("projection",camera.getProjection());
		shader.setUniform("color",1,1,1);
		camera.setZoom(50);
		camera.setAngle(0);
		camera.setPosition(0,0);
	}

	@Override
	void tick() {

		varyingNumber(r,0.04f,1f,4f);
		varyingNumber(g,0.05f,1f,4f);
		varyingNumber(b,0.06f,1f,4f);
		varyingNumber(varyZoom, 0.00001f, 1f, 1f);
		varyingNumber(varyMove, 0.03f, -3f, 3f);
		varyingNumber(varyRotate,4f,-360,360);

		//shader.setUniform("color",r.value,g.value,b.value);
		entities.parallelStream().forEach(entity -> {
			entity.setAngle(entity.getAngle() + 0.25f);
			double radians = Math.toRadians(entity.getAngle() + 90);
			entity.setPosition((float) (Math.cos(radians) * 3),(float) (Math.sin(radians) * 3));
			entity.setSize(varyZoom.value);
			entity.tick();
		});
		//camera.setZoom(varyZoom.value);
		//camera.setPosition(varyMove.value,0);
		//camera.setAngle(varyRotate.value);
		glfwSetWindowTitle(getWindowID(),"ForeverAlpha | FPS: " + getCurrentFPS() + ", TPS: " + getTPS() + " | X: " + varyMove.value);
	}

	@Override
	void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		shader.bind();
		entities.render(shader, camera);

		glfwSwapBuffers(getWindowID());
	}

	private void varyingNumber(FloatBool data, float amount, float min, float max)
	{
		if(data.flip)
		{
			if(data.value < max)
				data.value += amount;
			else
				data.flip = false;
		}
		else
		{
			if(data.value > min)
				data.value -= amount;
			else
				data.flip = true;
		}
	}

	@Override
	void onStop() {
		log("This scene is stopping the program!");
	}

	private class FloatBool
	{
		public boolean flip = true;
		public float value;

		FloatBool(float start)
		{
			value = start;
		}
	}

}
