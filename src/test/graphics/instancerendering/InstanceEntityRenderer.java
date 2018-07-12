package test.graphics.instancerendering;

import test.graphics.Model;
import test.graphics.Shader;

import java.util.ArrayList;

public class InstanceEntityRenderer extends ArrayList<InstanceEntity> {

	private Model model;

	//Used for rendering a lot of entities with the same model. Theoretically 99% of entities should be using the
	//square model, so this should be useful.
	public InstanceEntityRenderer(Model model)
	{
		this.model = model;
	}

	public void render(InstanceEntityCallback prerendercall)
	{
		model.bind();
		forEach(entity -> {
			entity.getSprite().bind();
			//Allows you to set uniform stuff before you render.
			prerendercall.prerender(entity);
			entity.render();
			entity.getSprite().unbind();
		});
		model.unbind();
	}

}
