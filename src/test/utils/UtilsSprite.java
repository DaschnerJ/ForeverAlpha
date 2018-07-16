package test.utils;

import test.graphics.textures.Texture;
import test.graphics.textures.TextureCoords;
import test.graphics.textures.TextureCoordsHolder;

import java.util.HashMap;

public class UtilsSprite {

	/**
	 * Generates sprites from a spritemap by automatically
	 * @param width The expected width of a sprite
	 * @param height The expected height of a sprite
	 * @return An array containing all the sprites within the texture.
	 */
	public static TextureCoords[] getTextureCoordsForSprites(TextureCoordsHolder textureCoordsHolder, Texture texture, int width, int height)
	{
		int xsize = texture.getWidth()/width;
		int ysize = texture.getHeight()/height;
		float actualsizex = texture.getWidth()/((float)width);
		float actualsizey = texture.getHeight()/((float)height);
		float xend = 1 - ((1/actualsizex)*(actualsizex-xsize));
		float yend = 1 - ((1/actualsizey)*(actualsizey-ysize));
		TextureCoords[] textureCoordsArray = new TextureCoords[xsize*ysize];
		for (int y = 0; y < ysize; y++) {
			for(int x = 0; x < xsize; x++) {
				float coordStartX = (xend/xsize)*x;
				float coordstartY = (yend/ysize)*y;
				float coordEndX = (xend/xsize)*(x+1);
				float coordEndY = (yend/ysize)*(y+1);
				float[] coords = new float[]{coordStartX, coordstartY, coordEndX, coordEndY};
				TextureCoords spriteTextureCoords = textureCoordsHolder.getOrAdd(coords);
				textureCoordsArray[x + (y * xsize)] = spriteTextureCoords;
			}
		}
		return textureCoordsArray;
	}

}
