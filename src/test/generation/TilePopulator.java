package test.generation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TilePopulator {

        HashMap<Integer, Integer> colorMap;

        int seed = 0;

        ArrayList<TileGen> gens = new ArrayList<>();
        JFrame frame;
        static TilePopulator tp;

        public TilePopulator(int seed)
        {
            this.seed = seed;
            gens.add(new TileGen(3, 50, 100, 500, 0));
            gens.add(new TileGen(5, 35, 60, 100, 0));
            gens.add(new TileGen(7, 25, 50, 5, 0));

            colorMap = new HashMap<>();
            colorMap.put(0, new Color(255, 0, 0).getRGB());
            colorMap.put(1, new Color(0, 255, 0).getRGB());
            colorMap.put(2, new Color(0, 0, 255).getRGB());
            colorMap.put(3, new Color(255, 255, 0).getRGB());
            colorMap.put(4, new Color(255, 0, 255).getRGB());
            colorMap.put(5, new Color(0, 255, 255).getRGB());
            colorMap.put(6, new Color(255, 255, 255).getRGB());
            colorMap.put(7, new Color(0, 0, 0).getRGB());
            colorMap.put(8, new Color(128, 0, 0).getRGB());
            colorMap.put(9, new Color(0, 128, 0).getRGB());
            colorMap.put(10, new Color(0, 0, 128).getRGB());
            colorMap.put(11, new Color(128, 128, 0).getRGB());
            colorMap.put(12, new Color(128, 0, 128).getRGB());
            colorMap.put(13, new Color(0, 128, 128).getRGB());
            colorMap.put(14, new Color(128, 128, 128).getRGB());
            colorMap.put(15, new Color(64, 0, 0).getRGB());
            colorMap.put(16, new Color(0, 64, 0).getRGB());
            colorMap.put(17, new Color(0, 0, 64).getRGB());
            colorMap.put(18, new Color(64, 64, 0).getRGB());
            colorMap.put(19, new Color(64, 0, 64).getRGB());
            colorMap.put(20, new Color(0, 64, 64).getRGB());
            colorMap.put(21, new Color(64, 64, 64).getRGB());

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
        tp = new TilePopulator(seed);
        int width = 512;
        int height = 512;

        BufferedImage image;
        float[][] map = noise.generateSimplexNoise(width, height);

        tp.frame = new JFrame();

        tp.frame.setVisible(true);
        int[][] m = noise.convert(map);
        int[][] heightMap = tp.copyList(m);
        //tp.print2DArray(m);
        m = tp.simplify(10, 90,0, 1, 10,  m);
        m = tp.addRivers(1, 2, 5, 10, m, heightMap);
        //tp.print2DArray(m);
        m = tp.populateMap(m, seed);
        //tp.print2DArray(m);
        tp.showMap(m);
//      frame.setSize(WIDTH, HEIGHT);
        // Better to DISPOSE than EXIT
        tp.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void showMap(int[][] map)
    {
        frame.add(new JLabel(new ImageIcon(tp.getImage(map))));

        frame.pack();
    }

    private int[][] copyList(int[][] list)
    {
        int[][] newList = new int[list.length][list[0].length];
        for(int i = 0; i < list.length; i++)
        {
            for(int j = 0; j < list[0].length; j++)
            {
                newList[i][j] = list[i][j];
            }
        }
        return newList;
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

    private int[][] simplify(int water, int hill, int hillID, int grassID, int waterID, int[][] map)
    {
        for(int i = 0; i < map.length; i++)
        {
            for(int j = 0; j < map[0].length; j++)
            {
                if(map[i][j] < water)
                {
                    map[i][j] = waterID;
                }
                else if(map[i][j] > hill)
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

    private int[][] addRivers2(int replace, int tile, int min, int max, int[][] riverMap, int[][] heightMap)
    {
        return riverMap;
    }


    private int[][] addRivers(int replace, int tile, int min, int max, int[][] riverMap, int[][] heightMap)
    {
        Random r= new Random(seed);
        int selectAmount = r.nextInt(max+1 - min) + min;
        for(int i = 0; i < selectAmount; i++)
        {
            int x = r.nextInt(heightMap.length);
            int y = r.nextInt(heightMap[0].length);
            Point p = new Point(x, y);
            while(riverMap[x][y] == replace || riverMap[x][y] == tile)
            {
                riverMap[x][y] = tile;
                p = findSmallerNeighour(x, y, heightMap, r);
                //These are the same so we hit the lowest point.
                if(p.x == x && p.y == y)
                    break;
                //We hit a lake or a hill so we must stop.
                if(riverMap[p.x][p.y] != replace && riverMap[p.x][p.y] != tile)
                    break;
                x = p.x;
                y = p.y;
            }
            //showMap(riverMap);
        }
        return riverMap;
    }

    private Point findSmallerNeighour(int x, int y, int[][] heightMap, Random r)
    {
        int sX = x;
        int sY = y;
        int cX = sX;
        int cY = sY;
        ArrayList<Point> visited = new ArrayList<>();
        //System.out.println("");
        //System.out.println("Current X: " + cX + " Y: " + cY + " H: " + heightMap[cX][cY]);
        //System.out.println("");
        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                //If it the same tile skip it.
                if(i == 0 && j == 0)
                    continue;
                //If it some random corner tile, skip it.
                if(i != 0 && j != 0)
                    continue;
                //If it out of bounds, then just break because we hit the border.
                if(cX + i > heightMap.length-1 || cY + j > heightMap[0].length-1 || cX + i < 0 || cY + j < 0)
                    return new Point(x, y);
                //If it out of bounds, then just break because we hit the border.
                if(sX + i > heightMap.length-1 || sY + j > heightMap[0].length-1 || sX + i < 0 || sY + j < 0)
                    return new Point(x, y);
                visited.add(new Point(cX+i, cY+j));
                //If the current Selected tile is greater than the compared new tile, then set it as the selected.
                if(heightMap[sX][sY] > heightMap[cX+i][cY+j])
                {
                   sX = cX + i;
                   sY = cY + j;
                }
                //We have this here to have a random chance to throw an off set value since the vallies are too straight.
//                else
//                {
//                    int chance = 10;
//                    int choose = r.nextInt(100);
//                    if(choose < chance)
//                    {
//                        return new Point(cX + i, cY + j);
//                    }
//                }
                //System.out.println("New X: " + sX + " Y: " + sY  + " H: " + heightMap[sX][sY]);
            }
            //System.out.println("New X: " + sX + " Y: " + sY + " H: " + heightMap[sX][sY]);
        }
        if(sX == x && sY == y)
        {
            int chance = 95;
            int choose = r.nextInt(100);
            if(choose < chance) {
                return visited.get(r.nextInt(visited.size()));
            }
        }
        return new Point(sX, sY);
    }


//    private int[][] addLinks(int seedTile, Integer replace, int tile, int width, int length, int variation, int seed, int[][] map, int[][] heightMap)
//    {
//        ArrayList<Integer> replaceList = new ArrayList<>();
//        replaceList.add(replace);
//        return addLinks(replaceList, tile,length, width, variation, seed, map, heightMap);
//    }
//
//    private int[][] addLinks(ArrayList<Integer> replaceList, int tile, int length, int width, int variation, int seed, int[][] map, int[][] heightMap)
//    {
//        Random r = new Random(seed);
//        return map;
//    }

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

class Point
{
    int x, y;
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
