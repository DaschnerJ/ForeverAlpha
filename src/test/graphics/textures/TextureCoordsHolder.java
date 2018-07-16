package test.graphics.textures;

import java.util.HashMap;

/**
 * Used because texture coords can be reused among different things to save memory.
 */
public class TextureCoordsHolder extends HashMap<float[],TextureCoords> {

	public TextureCoords getOrAdd(float[] coordinates)
	{
		if(get(coordinates) != null)
			return get(coordinates);
		TextureCoords textureCoords = new TextureCoords(new float[]{
				coordinates[0],coordinates[1],
				coordinates[2],coordinates[1],
				coordinates[0],coordinates[3],
				coordinates[2],coordinates[3],
		});
		put(coordinates, textureCoords);
		return textureCoords;
	}

}
