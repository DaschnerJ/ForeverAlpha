package test.graphics.textures;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
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

	public void bind(Texture texture)
	{
		texture.bind(0);
		glEnableVertexAttribArray(1);
		//Tells OpenGL we intend to use our texture coords we loaded previously.
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordsVBO);
		//Tells OpenGL we intend to use the texture coords to specify how the texture will be drawn on.
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
	}

	public void unbind()
	{
		glDisableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

}
