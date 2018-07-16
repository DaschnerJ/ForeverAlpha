package test.graphics;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private int id;
	private int vertexShaderID;
	private int fragmentShaderID;

	//This is the program that the graphics card runs to render your game.
	public Shader(String vertexShaderFilename, String fragmentShaderFilename)
	{
		//Tells OpenGL that we intend to create a shader, a program to take input and process it into an output
		id = glCreateProgram();

		//Creates the vertex shader
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderID, compileStringFromFile(vertexShaderFilename));
		glCompileShader(vertexShaderID);
		//Checks if the vertex shader compiled correctly. If not, prints error to console
		if(glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) != 1)
			System.err.println(glGetShaderInfoLog(vertexShaderID));

		//Creates the fragment shader
		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderID, compileStringFromFile(fragmentShaderFilename));
		glCompileShader(fragmentShaderID);
		//Checks if the fragment shader compiled correctly. If not, prints error to console
		if(glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) != 1)
			System.err.println(glGetShaderInfoLog(fragmentShaderID));

		//Makes the shaders that we compiled above run when this shader program is run.
		glAttachShader(id, vertexShaderID);
		glAttachShader(id, fragmentShaderID);

		//Creates a variable in the shader program named "vertices"
		glBindAttribLocation(id, 0, "vertices");
		glBindAttribLocation(id, 1, "texture_coords");

		//Creates an executable for the graphics card using the vertex shader and fragment shader above.
		glLinkProgram(id);
		//Error checking on the link above.
		if(glGetProgrami(id, GL_LINK_STATUS) != 1)
			System.err.println(glGetProgramInfoLog(id));

		//A check to make sure the program will run fine in the current state of OpenGL
		glValidateProgram(id);
		//Error checking on the validate above.
		if(glGetProgrami(id, GL_LINK_STATUS) != 1)
			System.err.println(glGetProgramInfoLog(id));

	}

	//Tells OpenGL that we intend to use this program to render.
	public void bind()
	{
		glUseProgram(id);
	}

	//Reads a file and turns the contents into a string.
	public String compileStringFromFile(String filepath)
	{
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String text = null;
			while((text = reader.readLine()) != null)
			{
				stringBuilder.append(text);
				stringBuilder.append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	//Sets a uniform variable(a variable inside the shader program) externally. This is how you communicate with the
	//program on your graphics card about how to render something.
	//This function passes an integer to that variable.
	public void setUniform(String name, int value)
	{
		//Asks OpenGL to give us the address of a uniform variable.
		int address = glGetUniformLocation(id, name);
		//If the address exists, set the value of it to this integer.
		if(address != -1)
			glUniform1i(address,value);
	}

	//Sets a uniform variable(a variable inside the shader program) externally. This is how you communicate with the
	//program on your graphics card about how to render something.
	//This function passes a float to that variable.
	public void setUniform(String name, float value)
	{
		//Asks OpenGL to give us the address of a uniform variable.
		int address = glGetUniformLocation(id, name);
		//If the address exists, set the value of it to this float.
		if(address != -1)
			glUniform1f(address,value);
	}

	//Sets a uniform variable(a variable inside the shader program) externally. This is how you communicate with the
	//program on your graphics card about how to render something.
	//This function passes a vec3f(3 floats) to that variable.
	public void setUniform(String name, float value1, float value2, float value3)
	{
		//Asks OpenGL to give us the address of a uniform variable.
		int address = glGetUniformLocation(id, name);
		//If the address exists, set the value of it to this vec3f.
		if(address != -1)
			glUniform3f(address,value1,value2,value3);
	}

	//Sets a uniform variable(a variable inside the shader program) externally. This is how you communicate with the
	//program on your graphics card about how to render something.
	//This function passes a matrix4f(4x4 matrix) to that variable.
	public void setUniform(String name, Matrix4f value)
	{
		//Asks OpenGL to give us the address of a uniform variable.
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		//Shoves the matrix into the buffer
		value.get(buffer);
		//Asks OpenGL to give us the address of a uniform variable.
		int address = glGetUniformLocation(id, name);
		//If the address exists, set the value of it to this matrix.
		if(address != -1)
			glUniformMatrix4fv(address,false,buffer);
	}

}
