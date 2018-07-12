package test.graphics.textures;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;

public class Sprite {

	protected TextureCoords textureCoords;
	private Texture texture;

	public Sprite(Texture texture, TextureCoords textureCoords)
	{
		this.texture = texture;
		this.textureCoords = textureCoords;
	}

	public void bind()
	{
		texture.bind(0);
		glEnableVertexAttribArray(1);
		//Tells OpenGL we intend to use our texture coords we loaded previously.
		glBindBuffer(GL_ARRAY_BUFFER, textureCoords.getTextureCoordsVBO());
		//Tells OpenGL we intend to use the texture coords to specify how the texture will be drawn on.
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
	}

	public void unbind()
	{
		glDisableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

}
