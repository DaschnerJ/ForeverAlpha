package test.graphics.textures;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static test.utils.UtilsBuffer.createFloatBuffer;

public class TextureCoords {

	private int textureCoordsVBO;

	public TextureCoords(float[] textureCoords)
	{
		//See above note about VBOs
		textureCoordsVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordsVBO);
		//Shoves the information from our texture coords array into the VBO that OpenGL created for us.
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(textureCoords), GL_STATIC_DRAW);
	}

	public int getTextureCoordsVBO() {
		return textureCoordsVBO;
	}
}
