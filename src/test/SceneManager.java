package test;

public class SceneManager {

	private Scene currentScene;
	private boolean running;
	private boolean stopping;

	public SceneManager()
	{
		currentScene = null;
		running = false;
		stopping = false;
	}

	//Starts running scenes. It will continue to run scenes until a scene requests a stop or the window closes.
	public void start(Scene scene)
	{
		//We won't start if we are already running something.
		if(running)
			return;

		//Standard startup process.
		running = true;
		stopping = false;
		currentScene = scene;

		//This will start up any new scenes that are switched to, including the original one.
		//Stopping check to ensure that scenes do not try and continue the run process.
		while(!currentScene.hasStarted() && !stopping)
			currentScene.run();

		//Stop sequence
		running = false;
		System.out.println("Game is shutting down! Goodbye!");
	}

	//Switch from a running screen to a new one
	public void switchScene(Scene newScene)
	{
		if(currentScene != null)
			currentScene.stop();
		currentScene = newScene;
	}

	//Tells the current scene to stop running. It will, of course, finish its last tick before actually closing.
	public void stop()
	{
		if(currentScene != null)
			currentScene.stop();
		running = false;
		stopping = true;
	}

	public Scene getCurrentScene()
	{
		return currentScene;
	}

}
