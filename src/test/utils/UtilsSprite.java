package test.utils;

import test.graphics.textures.Sprite;
import test.graphics.textures.Texture;
import test.graphics.textures.TextureCoords;

import java.util.ArrayList;
import java.util.HashMap;

public class UtilsSprite {

	/**
	 * Generates sprites from a spritemap by automatically
	 * @param spritemap The texture to pull individual sprites from
	 * @param width The expected width of a sprite
	 * @param height The expected height of a sprite
	 * @param textureCoords All texture coords of all sprites. This is used because TextureCoords can be shared.
	 * @return An array containing all the sprites within the texture.
	 */
	public static Sprite[] generateSpritesFromSpriteMap(Texture spritemap, int width, int height, HashMap<float[],TextureCoords> textureCoords)
	{
		int xsize = spritemap.getWidth()/width;
		int ysize = spritemap.getHeight()/height;
		float actualsizex = spritemap.getWidth()/((float)width);
		float actualsizey = spritemap.getHeight()/((float)height);
		float xend = 1 - ((1/actualsizex)*(actualsizex-xsize));
		float yend = 1 - ((1/actualsizey)*(actualsizey-ysize));
		if(xsize == 0 || ysize == 0)
			return null;
		Sprite[] sprites = new Sprite[xsize*ysize];
		for (int y = 0; y < ysize; y++) {
			for(int x = 0; x < xsize; x++) {
				float coordStartX = (xend/xsize)*x;
				float coordstartY = (yend/ysize)*y;
				float coordEndX = (xend/xsize)*(x+1);
				float coordEndY = (yend/ysize)*(y+1);
				float[] coords = new float[]{coordStartX, coordstartY, coordEndX, coordEndY};
				TextureCoords spriteTextureCoords = textureCoords.get(coords);
				if(spriteTextureCoords == null) {
					spriteTextureCoords = new TextureCoords(new float[]{
							//Texture Coords
							coordStartX,coordstartY, //0 Top Left
							coordEndX,coordstartY, //1 Top Right
							coordStartX,coordEndY, //2 Bottom Left
							coordEndX,coordEndY, //3 Bottom Right
					});
					textureCoords.put(coords,spriteTextureCoords);
				}
				sprites[x + (y * xsize)] = new Sprite(spritemap, spriteTextureCoords);
			}
		}
		return sprites;
	}

}
