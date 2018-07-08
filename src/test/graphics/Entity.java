package test.graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

//Any object that will be rendered
public class Entity {

	private int verticeCount;
	private int verticesVBO;

	public Entity(float[] vertices)
	{
		//Because our vertices are given in 2D coordinates, divide by 2 to get the number of vertices we have.
		verticeCount = vertices.length / 2;

		//Loads the vertices into an object that OpenGL knows how to use.
		FloatBuffer loadedVertices = BufferUtils.createFloatBuffer(vertices.length);
		loadedVertices.put(vertices);
		//"flips" the buffer, making its read head set to 0, instead of wherever it was last being written to.
		//This prepares it to be read by OpenGL.
		loadedVertices.flip();

		//Asks OpenGL to create an ID to store information to. A VBO is a vertex buffer object (basically an array).
		//We use VBOs because we can store information in the video memory and refer to it later. This is much faster
		//than constantly passing OpenGL information manually every frame.
		verticesVBO = glGenBuffers();

		//Tells OpenGL that we intend to store an array in the VBO that it created for us above.
		glBindBuffer(GL_ARRAY_BUFFER, verticesVBO);

		//Shoves the information from our buffer(vertices array) into the VBO that OpenGL created for us.
		glBufferData(GL_ARRAY_BUFFER, loadedVertices, GL_STATIC_DRAW);

		//Unbinds the VBO because we are done writing to it.
		glBindBuffer(GL_ARRAY_BUFFER,0);
	}

	public void render()
	{
		//Tells OpenGL that we intend to use vertices data to render triangles
		glEnableClientState(GL_VERTEX_ARRAY);

		//Tells OpenGL we intend to use our vertices we loaded previously
		glBindBuffer(GL_ARRAY_BUFFER, verticesVBO);
		//Tells OpenGL we intend to use 2D vertices
		glVertexPointer(2, GL_FLOAT, 0,0);

		//Tells OpenGL to draw our vertices on the screen.
		glDrawArrays(GL_TRIANGLES, 0, verticeCount);

		//Tells OpenGL we are done using the vertices data
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		//Tells OpenGL we are done using vertices to render triangles
		glDisableClientState(GL_VERTEX_ARRAY);
	}

}
