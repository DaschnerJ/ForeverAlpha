package test.graphics.entitycomponents;

import test.graphics.Camera;
import test.graphics.Entity;
import test.graphics.Shader;

public abstract class EntityComponent {

	public abstract void tick(Entity entity);

	public abstract void render(Entity entity, Shader shader, Camera camera);

}
