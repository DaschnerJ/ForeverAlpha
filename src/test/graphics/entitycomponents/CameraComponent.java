package test.graphics.entitycomponents;

import test.graphics.Camera;
import test.graphics.Entity;
import test.graphics.Shader;

public class CameraComponent extends EntityComponent {

	@Override
	public void tick(Entity entity) {
	}

	@Override
	public void render(Entity entity, Shader shader, Camera camera) {
		shader.setUniform("projection",camera.getProjectionForEntity(entity));
	}

}
