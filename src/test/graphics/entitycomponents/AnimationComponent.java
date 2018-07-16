package test.graphics.entitycomponents;

import test.graphics.Camera;
import test.graphics.Entity;
import test.graphics.Shader;
import test.graphics.textures.TextureCoords;

/**
 * Using a similar method to the animation
 */
public class AnimationComponent extends EntityComponent {

	private float progressPerTick;
	private float currentProgress;
	private int frame;
	private TextureCoords[] animationTextureCoords;

	public AnimationComponent(TextureCoords[] animationTextureCoords, float ticksPerFrame)
	{
		progressPerTick = 1/ticksPerFrame;
		currentProgress = 0;
		frame = 0;
		this.animationTextureCoords = animationTextureCoords;
	}

	@Override
	public void tick(Entity entity) {
		currentProgress += progressPerTick;
		while (currentProgress >= 1) {
			frame++;
			if(frame == animationTextureCoords.length)
				frame = 0;
			entity.setTextureCoords(animationTextureCoords[frame]);
			currentProgress--;
		}
	}

	@Override
	public void render(Entity entity, Shader shader, Camera camera) {
	}

	public float getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(float currentProgress) {
		this.currentProgress = Math.min(1,Math.max(0,currentProgress));
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}
}
