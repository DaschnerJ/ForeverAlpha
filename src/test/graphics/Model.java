package test.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static test.utils.UtilsBuffer.createFloatBuffer;
import static test.utils.UtilsBuffer.createIntBuffer;

public class Model {

	private int verticeCount;
	private int verticesVBO;
	private int indicesVBO;

	public Model(float[] vertices, int[] indices)
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
		indicesVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, indicesVBO);
		//Shoves the information from our indices array into the VBO that OpenGL created for us.
		glBufferData(GL_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);

		//Unbinds the VBO because we are done writing to it.
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void bind()
	{
		//Tells OpenGL that we intend to use vertex data and texture coords data (defined in the Shader) respectively
		glEnableVertexAttribArray(0);

		//Tells OpenGL we intend to use our vertices we loaded previously
		glBindBuffer(GL_ARRAY_BUFFER, verticesVBO);
		//Tells OpenGL we intend to use our 2D vertices in the 0th
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		//Tells OpenGL to use our indices while rendering.
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
	}

	public void unbind()
	{
		//Tells OpenGL we are done using vertices to render triangles
		glDisableVertexAttribArray(0);

		//Tells OpenGL we are done using the vertices data
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getVerticeCount() {
		return verticeCount;
	}

	public int getVerticesVBO() {
		return verticesVBO;
	}

	public int getIndicesVBO() {
		return indicesVBO;
	}

}
