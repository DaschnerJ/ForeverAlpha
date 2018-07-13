package test.graphics.instancerendering;

import test.graphics.Camera;
import test.graphics.Entity;
import test.graphics.Model;
import test.graphics.Shader;

import java.util.ArrayList;

public class InstanceEntityRenderer extends ArrayList<Entity> {

	private Model model;

	//Used for rendering a lot of entities with the same model. Theoretically 99% of entities should be using the
	//square model, so this should be useful.
	public InstanceEntityRenderer(Model model)
	{
		this.model = model;
	}

	public void render(Shader shader, Camera camera)
	{
		model.bind();
		forEach(entity -> {
			entity.getTextureCoords().bind(entity.getTexture());
			entity.preRender(shader,camera);
			entity.render();
			entity.getTextureCoords().unbind();
		});
		model.unbind();
	}

}
