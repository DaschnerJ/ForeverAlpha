package test.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

	//The projection, typically orthographic.
	private Matrix4f projection;
	//How far zoomed in the camera is. More zoom = closer.
	private float zoom;
	//How far the camera has moved from origin.
	private Vector2f position;
	//The angle of this camera, in degrees.
	private float angle;

	//A cache for the calculated projection so if the camera does not move, more calculations are not necessary.
	private Matrix4f result;

	/**
	 * Create a camera for easy calculation/handling of projections.
	 * @param projection The projection, typically orthographic.
	 */
	public Camera(Matrix4f projection)
	{
		this.projection = projection;
		zoom = 1;
		angle = 0;
		position = new Vector2f();
		result = new Matrix4f();
		calculateProjection();
	}

	//Calculates the projection and puts it in the cache.
	private void calculateProjection()
	{
		result.set(projection);
		result.scale(zoom).rotate((float) Math.toRadians(-angle),0,0,1).translate(new Vector3f(position,0).mul(-1f));
	}

	//Gets the zoom of the camera. More zoom = closer.
	public float getZoom() {
		return zoom;
	}

	//Sets the zoom of the camera. More zoom = closer.
	public void setZoom(float zoom) {
		this.zoom = zoom;
		calculateProjection();
	}

	//Get translated projection for use in rendering.
	public Matrix4f getProjection()
	{
		return result;
	}

	//Sets the base projection that is translated, zoomed, etc. Aka orthographic projection.
	public void setBaseProjection(Matrix4f projection)
	{
		this.projection = projection;
		calculateProjection();
	}

	public void setPosition(Vector2f position) {
		this.position = position;
		calculateProjection();
	}

	//Sets the position of the camera.
	public void setPosition(float x, float y) {
		this.position = new Vector2f(x,y);
		calculateProjection();
	}

	//Gets the position of the camera.
	public Vector2f getPosition()
	{
		return position;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
		calculateProjection();
	}

	public Matrix4f getProjectionForEntity(float x, float y, float size, float angle)
	{
		return getProjectionForEntity(new Vector2f(x,y),size,angle);
	}

	public Matrix4f getProjectionForEntity(Entity entity)
	{
		return getProjectionForEntity(entity.getPosition(),entity.getSize(),entity.getAngle());
	}

	public Matrix4f getProjectionForEntity(Vector2f position, float size, float angle)
	{
		Matrix4f entityProjection = new Matrix4f(getProjection());
		return entityProjection.translate(new Vector3f(position,0)).rotate((float) Math.toRadians(angle),0,0,1).scale(size);
	}

}
