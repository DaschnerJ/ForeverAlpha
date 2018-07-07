package test;

public class SceneManager {

	//Currently running scene.
	private Scene currentScene;

	//A variable to hold a switched scene while the current scene finishes up.
	private Scene swap;

	//Whether or not the game loop is running. This is independent of a scene's ability to start and stop running.
	//When this stops running, so does the game. It will then proceed to exit the program.
	private boolean running;

	public SceneManager()
	{
		currentScene = null;
		running = false;
	}

	//Starts running scenes. It will continue to run scenes until a scene requests a stop or the window closes.
	public void start(Scene scene)
	{
		//We won't start if we are already running something.
		if(running)
			return;

		//Standard startup process.
		running = true;
		currentScene = scene;

		//This will start up any new scenes that are switched to, including the original one.
		while(!currentScene.hasStarted() && running) {
			currentScene.run();
			currentScene.onStop();
			if(swap != null) {
				currentScene = swap;
				swap = null;
			}
		}

		//Stop sequence
		running = false;
		System.out.println("Game is shutting down! Goodbye!");
	}

	//Switch from a running screen to a new one. This queues the switch until the current screen finishes.
	public void switchScene(Scene newScene)
	{
		if(currentScene != null)
			currentScene.stop();
		swap = newScene;
	}

	//Tells the current scene to stop running. It will, of course, finish its last tick before actually closing.
	public void stop()
	{
		if(currentScene != null)
			currentScene.stop();
		running = false;
	}

	//Gets the currently running scene.
	public Scene getCurrentScene()
	{
		return currentScene;
	}

}
