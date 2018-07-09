package test.graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

//Any object that will be rendered
public class Entity {

	private int verticeCount;
	private int verticesVBO;
	private int textureCoordsVBO;
	private int indicesVBO;

	private Texture texture;

	public Entity(float[] vertices, float[] textureCoords, int[] indices, Texture texture)
	{
		//The amount of vertices will equal the number of indices.
		verticeCount = indices.length;

		//Asks OpenGL to create an ID to store information to. A VBO is a vertex buffer object (basically an array).
		//We use VBOs because we can store information in the video memory and refer to it later. This is much faster
		//than constantly passing OpenGL information manually every frame.
		verticesVBO = glGenBuffers();
		//Tells OpenGL that we intend to store an array in the VBO that it created for us above.
		glBindBuffer(GL_ARRAY_BUFFER, verticesVBO);
		//Shoves the information from our vertices array into the VBO that OpenGL created for us.
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);

		//See above note about VBOs
		textureCoordsVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordsVBO);
		//Shoves the information from our texture coords array into the VBO that OpenGL created for us.
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(textureCoords), GL_STATIC_DRAW);

		//See above note about VBOs
		indicesVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, indicesVBO);
		//Shoves the information from our indices array into the VBO that OpenGL created for us.
		glBufferData(GL_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);

		//Unbinds the VBO because we are done writing to it.
		glBindBuffer(GL_ARRAY_BUFFER,0);

		this.texture = texture;
	}

	public void render()
	{
		//Binds the texture so it is used in rendering.
		texture.bind();

		//Tells OpenGL that we intend to use vertices data to render triangles
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		//Tells OpenGL we intend to use our vertices we loaded previously
		glBindBuffer(GL_ARRAY_BUFFER, verticesVBO);
		//Tells OpenGL we intend to use 2D vertices
		glVertexPointer(2, GL_FLOAT, 0,0);

		//Tells OpenGL we intend to use our texture coords we loaded previously.
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordsVBO);
		//Tells OpenGL we intend to use the texture coords to specify how the texture will be drawn on.
		glTexCoordPointer(2, GL_FLOAT, 0,0);

		//Tells OpenGL to use our indices while rendering.
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);

		//Tells OpenGL to draw our vertices on the screen.
		glDrawElements(GL_TRIANGLES, verticeCount, GL_UNSIGNED_INT, 0);

		//Tells OpenGL we are done using the vertices data
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		//Tells OpenGL we are done using vertices to render triangles
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
	}

	public FloatBuffer createFloatBuffer(float[] data)
	{
		//Loads the vertices into an object that OpenGL knows how to use.
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		//"flips" the buffer, making its read head set to 0, instead of wherever it was last being written to.
		//This prepares it to be read by OpenGL.
		buffer.flip();
		return buffer;
	}

	public IntBuffer createIntBuffer(int[] data)
	{
		//Loads the vertices into an object that OpenGL knows how to use.
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		//"flips" the buffer, making its read head set to 0, instead of wherever it was last being written to.
		//This prepares it to be read by OpenGL.
		buffer.flip();
		return buffer;
	}

}
