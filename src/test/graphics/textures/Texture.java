package test.graphics.textures;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

	private int id;
	//Size, in pixels of the texture.
	private int width;
	private int height;

	//BEWARE THAT NONLINEAR IMAGES WILL PULL PIXEL COLOR FROM SLIGHTLY OUTSIDE TEXTURE COORDS
	//AS SUCH, THEY ARE PROBABLY BETTER USED IN ONLY ONE SPRITE
	public Texture(String filename, boolean linear)
	{
		//These buffers will be filled with the width and height of the image when the function below executes.
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);

		//UNUSED, but here to satisfy function below. This buffer will be filled with how many channels are in an image.
		//Eg. 4 Channels means it has RGBA but 3 means it only has RGB (no opacity).
		IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);

		//Creates a buffer to be used with OpenGL. This buffer will contain the pixel data, but it will be separated
		//into the RGBA channels. This convenience function provided by stb will also give us width, height, and channel
		//information about the image by filling the respective buffers.
		ByteBuffer pixelData = stbi_load(filename, widthBuffer, heightBuffer, channelsBuffer, 4);

		//Stores the information given to us
		this.width = widthBuffer.get();
		this.height = heightBuffer.get();

		//Have OpenGL generate us an ID that we can use to reference this texture.
		this.id = glGenTextures();

		//Tells OpenGL we intend to use the ID they gave us as a 2D texture and binds it.
		//Binding it makes the future calls we are about to do automatically associate with this id.
		glBindTexture(GL_TEXTURE_2D, this.id);

		//Tells OpenGL how we want the texture to be loaded/used.
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, linear ? GL_NEAREST : GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, linear ? GL_NEAREST : GL_LINEAR);

		//Gives OpenGL the image we loaded up above and tells it to store it in memory for quick reference later.
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);
		//Unbinds the texture so it will no longer be used
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	//Creates a new texture. Uses the given string to resolve a file path.
	public Texture(String filename)
	{
		this(filename,true);
	}

	//Binds this texture, telling OpenGL that any further functions involving textures should use this texture.
	public void bind(int sampler)
	{
		glActiveTexture(GL_TEXTURE0 + sampler);
		glBindTexture(GL_TEXTURE_2D, this.id);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getID() {
		return id;
	}
}