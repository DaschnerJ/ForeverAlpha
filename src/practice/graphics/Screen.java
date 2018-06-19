package practice.graphics;

/**
 * Class that is meant to display and fill pixels.
 */
public class Screen {

    private int width, height;
    public int[] pixels;

    public Screen(int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    //Render method to render the screen.
    public void render()
    {
        //We loop through the pixel map.

        for(int y = 0; y < height; y++)
        {
            //Dont really need these checks here unless the values are scale differently
            //or modified.
//            if(yTime >= height && yTime < 0)
//                break;
            for(int x = 0; x < width; x++)
            {
//                if(xTime >= width && xTime < 0)
//                    break;
                pixels[x+y*width] = 0xff00ff;
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
