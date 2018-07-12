package test.graphics;

import org.joml.Vector2f;
import test.graphics.textures.Sprite;

import static org.lwjgl.opengl.GL11.*;

//Any object that will be rendered
public class Entity {

	//Where the entity is in the world.
	private Vector2f position;
	//How big the entity is. eg. 2 means twice as tall and twice as wide (which means 4 times area of original)
	//If you intend to scale by area, use sqrt(2) to scale by 2x area, for example
	private float size;
	//What angle the entity is rotated at
	private float angle;

	//The model to use for rendering this entity.
	private Model model;

	private Sprite sprite;

	public Entity(Model model, Sprite sprite) {
		this.model = model;
		this.sprite = sprite;
		this.position = new Vector2f();
		this.size = 1;
		this.angle = 0;
	}

	public void render()
	{
		//Binds the texture so it is used in rendering.
		sprite.bind();
		model.bind();

		//Tells OpenGL to draw our vertices on the screen.
		glDrawElements(GL_TRIANGLES, model.getVerticeCount(), GL_UNSIGNED_INT, 0);

		model.unbind();
		sprite.unbind();
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(float x, float y)
	{
		this.position = new Vector2f(x,y);
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void move(Vector2f move)
	{
		this.position.add(move);
	}

	public void move(float x, float y)
	{
		this.position.add(x,y);
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
