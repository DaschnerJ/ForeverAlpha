package test.generation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class TilePopulator {

        HashMap<Integer, Integer> colorMap;

        int seed = 0;

        ArrayList<TileGen> gens = new ArrayList<>();

        public TilePopulator(int seed)
        {
            this.seed = seed;
            gens.add(new TileGen(3, 10, 100, 20, 1));
            gens.add(new TileGen(5, 10, 100, 60, 1));
            gens.add(new TileGen(4, 10, 100, 10, 0));

            colorMap = new HashMap<>();
            colorMap.put(0, new Color(255, 0, 0).getRGB());
            colorMap.put(1, new Color(0, 255, 0).getRGB());
            colorMap.put(2, new Color(0, 0, 255).getRGB());
            colorMap.put(3, new Color(255, 255, 0).getRGB());
            colorMap.put(4, new Color(255, 0, 255).getRGB());
            colorMap.put(5, new Color(0, 255, 255).getRGB());
        }

        public int[][] populateMap(int[][] map, int seed)
        {
            for(TileGen g : gens)
            {
                map = g.addTile(map, 1, seed);
            }
            return map;
        }

    public static void main(String[] args)
    {
        int seed = 123;
        SimplexNoise noise = new SimplexNoise(seed);
        TilePopulator tp = new TilePopulator(seed);
        int width = 512;
        int height = 512;

        BufferedImage image;
        float[][] map = noise.generateSimplexNoise(width, height);

        JFrame frame = new JFrame();

        frame.setVisible(true);
        int[][] m = noise.convert(map);
        tp.print2DArray(m);
        m = tp.simplify(70, 40, 0, 1, 2, m);
        tp.print2DArray(m);
        m = tp.populateMap(m, seed);
        tp.print2DArray(m);
        frame.add(new JLabel(new ImageIcon(tp.getImage(m))));

        frame.pack();
//      frame.setSize(WIDTH, HEIGHT);
        // Better to DISPOSE than EXIT
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public BufferedImage getImage(int[][] map)
    {
        BufferedImage image = new BufferedImage(map.length, map[0].length, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[0].length; y++) {
                int finalX = x;
                int finalY = y;
                colorMap.keySet().forEach(p ->
                {
                    if(map[finalX][finalY] == p)
                    {
                        image.setRGB(finalX, finalY, colorMap.get(p));
                    }
                });
            }
        }
        //System.out.println("RGB Example: " + new Color(20, 255, 10).getRGB());
        //System.out.println("P Size: " + p.length);
        return image;
    }

    private int[][] simplify(int water, int hill, int waterID, int grassID, int hillID, int[][] map)
    {
        for(int i = 0; i < map.length; i++)
        {
            for(int j = 0; j < map[0].length; j++)
            {
                if(map[i][j] > water)
                {
                    map[i][j] = waterID;
                }
                else if(map[i][j] < hill)
                {
                    map[i][j] = hillID;
                }
                else
                {
                    map[i][j] = grassID;
                }
            }
        }
        return map;
    }

    private void print2DArray(int[][] m)
    {
        System.out.println();
        for(int i = 0; i < m.length; i++)
        {
            for(int j = 0; j < m[0].length; j++)
            {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
