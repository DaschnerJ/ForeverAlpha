package test.utils;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class UtilsBuffer {

	public static FloatBuffer createFloatBuffer(float[] data)
	{
		//Loads the vertices into an object that OpenGL knows how to use.
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		//"flips" the buffer, making its read head set to 0, instead of wherever it was last being written to.
		//This prepares it to be read by OpenGL.
		buffer.flip();
		return buffer;
	}

	public static IntBuffer createIntBuffer(int[] data)
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
