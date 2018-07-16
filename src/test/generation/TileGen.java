package test.generation;

import java.util.ArrayList;
import java.util.Random;

public class TileGen {

    ArrayList<Integer> replaceable;

    int id;
    int min;
    int max;
    int density;

    public TileGen(int id, int min, int max, int density, ArrayList<Integer> replaceable)
    {
        this.id = id;
        this.min = min;
        this.max = max;
        this.density = density;
        this.replaceable = replaceable;
    }

    public TileGen(int id, int min, int max, int density, int replaceableTile)
    {
        this.id = id;
        this.min = min;
        this.max = max;
        this.density = density;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(replaceableTile);
        this.replaceable = list;
    }

    public int[][] addTile(int[][] map, int mapID, long seed)
    {
        Random r = new Random(seed + mapID);
        replaceable.forEach(x ->
        {
            System.out.println("Replacable int: " + x);
        });
        for(int i = 0; i < density; i++)
        {
            int x = r.nextInt(map.length);
            int y = r.nextInt(map[0].length);
            System.out.println("Trying to replace: " + map[x][y]);
            if(replaceable.contains(map[x][y]))
            {
                map[x][y] = id;
                map = expandTile(map, mapID, seed, x, y);
            }
        }
        return map;

    }

    public int[][] expandTile(int[][] map, int mapID, long seed, int x, int y)
    {
        Random r = new Random(seed + mapID);
        int xP = x;
        int yP = y;
        int dense = r.nextInt(density);
        for(int i = 0; i < dense; i++)
        {
            int diffX = r.nextInt(3) - 1;
            int diffY = r.nextInt(3) - 1;
            if(diffX + xP < 0 || diffX + xP > map.length-1)
            {
                diffX = 0;
            }
            if(diffY + yP < 0 || diffY + yP > map[0].length-1)
            {
                diffY = 0;
            }
            xP += diffX;
            yP += diffY;
            map[xP][yP] = id;
        }
        return map;
    }





}
