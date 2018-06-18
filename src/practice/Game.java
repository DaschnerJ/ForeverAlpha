package practice;

import javax.swing.*;
import java.awt.*;

/**
 * This is just practice to learn OpenGL and test out things.
 */
//We extend Canvas to be able to paint and draw onto the screen.
    //We also use runnable since the game itself is a runnable and threadable application
public class Game extends Canvas implements Runnable{

    private static final long serialVersionUID = 1L;

    public static int width = 300;
    //To maintain the standard aspect ratio for monitors (16x9) resolution.
    public static int height = width / 16 * 9;
    //Scales the aspect ratio and render up to a bigger scale.
    public static int scale = 3;
    //Game thread.
    private Thread thread;
    //Controls if the game is running or not.
    private boolean running = false;
    //Game window.
    private JFrame frame;

    public Game()
    {
        //Sets window's size.
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);
        //Create the window.
        frame = new JFrame();
    }

    //We synchronize this thread with the game thread in order to keep consistency
    //and prevent concurrent modification and other issues.
    public synchronized void start()
    {
        //To enable he game to run.
        running = true;
        //We have this added here to attach the thread to the main game.
        thread = new Thread(this, "Display");
        thread.start();
    }

    //To stop the thread correctly.
    public synchronized void stop()
    {
        //Stops the game from running.
        running = false;
        //We join the thread to be able to stop it properly.
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        //We are disabling this since this causes a bunch of graphical issues.
        //(We are not dealing with this shit.)
        game.frame.setResizable(false);
        //Set the title of the window.
        game.frame.setTitle("Red's Practice Game");
        //We are addin the canvas to the game.
        game.frame.add(game);
        //Sizes the game frame to the correct size.
        game.frame.pack();
        //We are setting the close button to close the game and stop threads.
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Centers the window.
        game.frame.setLocationRelativeTo(null);
        //Finally show the game window.
        game.frame.setVisible(true);
        //Then start the game thread.
        game.start();
    }

    @Override
    public void run() {
        //Main game loop.
        while(running)
        {
            System.out.println("Running...");
        }
    }
}
