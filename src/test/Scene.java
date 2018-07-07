package test;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

/*A scene is a bit of instructions for how to run the game at a certain point in time. It can specify tick behaviour
and rendering behaviour, making it a robust way to handle different parts of the game while also being simple.

*/
public abstract class Scene {

	private GameEngine gameEngine;
	//Current time, in seconds, computed from nanoseconds
	private double time;
	//Last time a tick or frame fired, in seconds
	private double lastUpdate;
	private double lastFrame;
	//Time, in seconds, that it should take per tick/frame
	private double targetTPS;
	private double targetFPS;
	//Whether the scene is running or not
	private boolean running;
	//Whether the scene has started or not
	private boolean hasStarted;
	//Window associated with this scene.
	private Window window;
	//Window ID associated with the window that is associated with this scene.
	private long windowID;

	public Scene(double targetTPS, double targetFPS, GameEngine gameEngine)
	{
		this.window = gameEngine.getWindow();
		this.windowID = window.getID();
		this.targetTPS = 1/targetTPS;
		this.targetFPS = 1/targetFPS;
		this.gameEngine = gameEngine;
		refreshTime();
		running = false;
		hasStarted = false;
	}

	public void run()
	{
		running = true;
		hasStarted = true;
		refreshTime();
		while(running)
		{
			//Updates current time
			updateTime();

			/*Goes through keyboard/window input queue. This is to say that when a key is pressed on the keyboard it
			does not run handlers for those until this is called*/
			glfwPollEvents();

			//Runs ticks when ticks are due
			if(time - lastUpdate > targetTPS) {
				int caughtUpTicks = 0;
				//Attempts to tick up to 10 times before giving up and skipping ticks
				while(time - lastUpdate > targetTPS && caughtUpTicks++ < 10 && running) {
					tick();
					lastUpdate += targetTPS;
				}
				//Skips all ticks up to the current moment so that the gameEngine may catch up
				if(caughtUpTicks == 10 && time - lastUpdate > targetTPS)
					lastUpdate += ((int) ((time - lastUpdate) / targetTPS));
			}

			//Renders when a render is due
			if(time - lastFrame > targetFPS) {
				render();

				/*Does not attempt to catch up frames if frames are past due,
				  as re-rendering without gameEngine change (ticks) has no benefit

				  Therefore, skips all frames due up to last frame due*/
				if(time - lastFrame > targetFPS)
					lastFrame += ((int)((time - lastFrame) / targetFPS));
			}


			try {
				//Sleeps minimum time possible.
				Thread.sleep(0,0);
			} catch (InterruptedException ignored) {
			}
		}
	}

	//Tells if this scene is running or not
	public boolean isRunning() {
		return running;
	}

	//Each scene may define what will happen in a tick
	abstract void tick();

	//Each scene may define how they will render.
	abstract void render();

	//When the scene stops, this is called.
	abstract void onStop();

	//Tells the loop to stop. This won't actually happen until the last tick/frame finishes processing.
	public void stop()
	{
		running = false;
	}

	//Gets the window associated with this scene.
	public Window getWindow()
	{
		return window;
	}

	//Gets the window id associated with this scene. Convenience/performance function.
	public long getWindowID()
	{
		return windowID;
	}

	//Resets expected times in case of a pause.
	private void refreshTime()
	{
		updateTime();
		lastUpdate = time - this.targetTPS;
		lastFrame = time - this.targetFPS;
	}

	//Turns current time into a double from ultra precise nano seconds. Aka 5.23 billion nanoseconds = 5.23 seconds.
	//Useful for ultra precise timing.
	private void updateTime()
	{
		time = System.nanoTime() / 1000000000d;
	}

	//Get the current TPS. This is actually the time it takes for a tick to pass, rather than the number of ticks/s.
	public double getTPS() {
		return targetTPS;
	}

	/*Gets the game engine associated with this scene. The game engine provides access to all the systems, like audio
	etc., rather than the actual world and entities etc.*/
	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public boolean hasStarted() {
		return hasStarted;
	}

	//Logs info to the debug console, making it clearly identifiable.
	public void log(String info)
	{
		System.out.println(this.getClass().getSimpleName() + ": " + info);
	}

	public void log(Number number)
	{
		System.out.println(this.getClass().getSimpleName() + ": " + number);
	}
}
