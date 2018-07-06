package practice.graphics;

import java.util.Random;

/**
 * Class that is meant to display and fill pixels.
 */
public class Screen {

    private int width, height;
    public int[] pixels;

    public final int MAP_SIZE = 64;
    public final int MAP_SIZE_MASK = MAP_SIZE - 1;

    public int[] tiles = new int[MAP_SIZE*MAP_SIZE];

    private Random random = new Random();

    public Screen(int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int i = 0; i < MAP_SIZE*MAP_SIZE; i++)
        {
            tiles[i] = random.nextInt(0xffffff);
        }

    }

    //Render method to render the screen.
    public void render(int xOffset, int yOffset)
    {
        //We loop through the pixel map.
        for(int y = 0; y < height; y++)
        {
            int yp = y + yOffset;
            if(yp < 0 || yp >= height) continue;
            for(int x = 0; x < width; x++)
            {
                int xp = x + xOffset;
                if(xp < 0 || xp >= width) continue;
                pixels[xp + yp * width] = Sprite.grass.pixels[(x&15) + (y&15) * Sprite.grass.SIZE];
            }
        }
    }

    //To remove old images off the map.
    public void clear()
    {
        //Set old pixels to 0.
        for(int i = 0; i < pixels.length; i++)
        {
            pixels[i] = 0;
        }
    }

}
