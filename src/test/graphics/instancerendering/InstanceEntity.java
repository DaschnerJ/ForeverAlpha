package test.graphics.instancerendering;

import test.graphics.Entity;
import test.graphics.Model;
import test.graphics.textures.Sprite;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

public class InstanceEntity extends Entity {

	public InstanceEntity(Model model, Sprite sprite) {
		super(model, sprite);
	}

	@Override
	public void render()
	{
		glDrawElements(GL_TRIANGLES, getModel().getVerticeCount(), GL_UNSIGNED_INT, 0);
	}

}
