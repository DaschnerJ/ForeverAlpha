package practice;

import practice.graphics.Screen;
import practice.input.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

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

    private Keyboard key;

    //We create a screen to handle the pixel map.
    private Screen screen;

    public static String title = "ForeverAlpha";

    //Our final rendered image view.
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    //We create an array to store the new image's RGB values to be modified and displayed.
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game()
    {
        //Sets window's size.
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);

        //We initialize the screen to handle the pixel map here since dimensions should be set before this point.
        screen = new Screen(width, height);

        //Create the window.
        frame = new JFrame();

        key = new Keyboard();
        addKeyListener(key);

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

    int x = 0;
    int y = 0;
    public void update()
    {
        key.update();
        if(key.up) y++;
        if(key.down) y--;
        if(key.left) x++;
        if(key.right)  x--;
    }

    /**
     * The part we have to think about here is the buffer strategy. How do we want to take a rendered image and
     * display the image?
     *
     * We take the frame and calculate it and then store the created frame. Then we hold the frame in memory in
     * ordered to be rendered at a later date. This way we create a buffer to make the game view smoother. Most
     * games run at an actual frame of 20-30 frames but to handle higher rate of change, the frame rate will be
     * 60 FPS. Most common screens can only handle 60 FPS so anything higher than this does not affect much other
     * than mouse movement...
     *
     * Later on I will probably rewrite this to be handled by OpenGL as this seems to be the only part that is
     * really affected by this. And since this is a 2D game and particles and ect will probably come later, we
     * will simply skip using GPU since it is not resource heavy yet.
     */
    public void render()
    {
        //We use a built in Java Buffer Strategy from canvas.
        BufferStrategy bs = getBufferStrategy();

        //We do not want to create a buffer each and every time. So we only create the Buffer Strategy if
        //it does not exist. We use '3' because we want triple buffering for smoother graphics. This also
        //allows to take advantage of threading capabilities so while we wait to display we can calculate
        //the next frame. Anything higher does not matter.
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        //We remove old image before showing the new image.
        screen.clear();
        //We swap the new screen in.
        screen.render(0 + x, 0 + y);
        //We copy the screen's calculated int array to the current game screen to be rendered.
        for(int i = 0; i < pixels.length; i++)
        {
            pixels[i] = screen.pixels[i];
        }
        //We get graphics to be able to draw to screen with the current buffer we are using.
        Graphics g = bs.getDrawGraphics();
        //All graphics we want to draw MUST be drawn here. Let's not repeat Hafen...
        {
//            //Sets the color to paint with.
//            g.setColor(Color.BLACK);
//            //We draw a black background just to have a base template for the background of the game.
//            g.fillRect(0, 0, getWidth(), getHeight());
            //Draw the image to the screen.
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
        //Removes old frames for garbage collection and free memory. Also prevents overloads and crashes...
        g.dispose();
        //This will sho the next calculated buffer.
        bs.show();
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        //We are disabling this since this causes a bunch of graphical issues.
        //(We are not dealing with this shit.)
        game.frame.setResizable(false);
        //Set the title of the window.
        game.frame.setTitle(title);
        //We are adding the canvas to the game.
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
        //We get the system time to control the speed of the game update and normalize game speed
        //across computers.
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        //We now convert nanoseconds into milliseconds. The dividing number is the frame rate limiter.
        //In this case we are limiting it to 60.
        final double ns = 1000000000.0 / 60.0;
        //Measure change in time.
        double delta = 0;
        //Counts how many frames we actually rendered per second.
        int frames = 0;
        //Counts how many times we actually update the game. This should always be 60.
        int updates = 0;
        //To focus and click into the canvas to allow opening and using the game immediately.
        requestFocus();
        //Main game loop.
        while(running)
        {
            //Get new system time to see change in time.
            long now = System.nanoTime();
            //Add the change in time.
            delta += (now-lastTime) / ns;
            //Update the last time for the next difference in time.
            lastTime = now;
            //If the time is great enough difference, calculate the difference.
            while(delta >= 1)
            {
                //A game tick.
                update();
                updates++;
                delta--;
            }
            //Render graphics
            render();
            frames++;
            //Reset the variables each second.
            if(System.currentTimeMillis() - timer > 1000)
            {
                //Add the difference to prevent this from running again.
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                frame.setTitle(title + " | " + updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
            //System.out.println("Running...");
        }

        //Program is done.
        stop();
    }
}
