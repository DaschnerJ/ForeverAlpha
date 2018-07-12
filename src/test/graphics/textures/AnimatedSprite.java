package test.graphics.textures;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.HashMap;
import java.util.Objects;

public class AnimatedSprite extends Sprite {

	private TextureCoords[] animationTextureCoords;
	private float progressPerTick;
	private float currentProgress;
	private int frame;

	/**
	 * Creates an animated sprite. Keep in mind that this must be ticked within the main game loop.
	 * @param textureCoordsHashMap A texture coords hashmap to keep memory costs down.
	 * @param texture The texture to use in the animation
	 * @param width The width of each frame
	 * @param height The height of each frame
	 * @param ticksPerFrame How many ticks it takes for one frame to pass
	 */
	public AnimatedSprite(HashMap<float[], TextureCoords> textureCoordsHashMap, Texture texture, int width, int height, float ticksPerFrame) {
		super(texture, getBaseTextureCoords(textureCoordsHashMap,texture,width,height));
		animationTextureCoords = getTextureCoords(textureCoordsHashMap,texture,width,height);
		progressPerTick = 1/ticksPerFrame;
		currentProgress = 0;
		frame = 0;
	}

	public void setTicksPerFrame(float ticks)
	{
		progressPerTick = 1/ticks;
	}

	public void tick()
	{
		currentProgress += progressPerTick;
		while (currentProgress >= 1) {
			frame++;
			if(frame == animationTextureCoords.length)
				frame = 0;
			textureCoords = animationTextureCoords[frame];
			currentProgress--;
		}
	}

	//Uses a slightly modified version of the spritemap loading function
	private static TextureCoords[] getTextureCoords(HashMap<float[],TextureCoords> textureCoordsHashMap, Texture texture, int width, int height)
	{
		int xsize = texture.getWidth()/width;
		int ysize = texture.getHeight()/height;
		float actualsizex = texture.getWidth()/((float)width);
		float actualsizey = texture.getHeight()/((float)height);
		float xend = 1 - ((1/actualsizex)*(actualsizex-xsize));
		float yend = 1 - ((1/actualsizey)*(actualsizey-ysize));
		TextureCoords[] textureCoords = new TextureCoords[xsize*ysize];
		for (int y = 0; y < ysize; y++) {
			for(int x = 0; x < xsize; x++) {
				float coordStartX = (xend/xsize)*x;
				float coordstartY = (yend/ysize)*y;
				float coordEndX = (xend/xsize)*(x+1);
				float coordEndY = (yend/ysize)*(y+1);
				float[] coords = new float[]{coordStartX, coordstartY, coordEndX, coordEndY};
				TextureCoords spriteTextureCoords = textureCoordsHashMap.get(coords);
				if(spriteTextureCoords == null) {
					spriteTextureCoords = new TextureCoords(new float[]{
							//Texture Coords
							coordStartX,coordstartY, //0 Top Left
							coordEndX,coordstartY, //1 Top Right
							coordStartX,coordEndY, //2 Bottom Left
							coordEndX,coordEndY, //3 Bottom Right
					});
					textureCoordsHashMap.put(coords,spriteTextureCoords);
				}
				textureCoords[x + (y * xsize)] = spriteTextureCoords;
			}
		}
		return textureCoords;
	}

	private static TextureCoords getBaseTextureCoords(HashMap<float[],TextureCoords> textureCoordsHashMap, Texture texture, int width, int height)
	{
		int xsize = texture.getWidth()/width;
		int ysize = texture.getHeight()/height;
		float actualsizex = texture.getWidth()/((float)width);
		float actualsizey = texture.getHeight()/((float)height);
		float xend = 1 - ((1/actualsizex)*(actualsizex-xsize));
		float yend = 1 - ((1/actualsizey)*(actualsizey-ysize));
		float coordEndX = (xend/xsize);
		float coordEndY = (yend/ysize);
		float[] coords = new float[]{0, 0, coordEndX, coordEndY};
		TextureCoords spriteTextureCoords = textureCoordsHashMap.get(coords);
		if(spriteTextureCoords == null) {
			spriteTextureCoords = new TextureCoords(new float[]{
					//Texture Coords
					0,0, //0 Top Left
					coordEndX,0, //1 Top Right
					0,coordEndY, //2 Bottom Left
					coordEndX,coordEndY, //3 Bottom Right
			});
			textureCoordsHashMap.put(coords,spriteTextureCoords);
		}
		return spriteTextureCoords;
	}

}
