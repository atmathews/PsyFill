
//import Polkadot;
import java.awt.*;

public class Bumper
{
    //private fields, all ints, for a Bumper
    //hint: the "location" of the bumper begins at its top left corner.
    public int bumpX;
    public int bumpY;
    public int bumpWidth;
    public int bumpHeight;
    public Color bumpCol;

    //constructors
    public Bumper()         //default constructor
    {

        bumpX = 100;
        bumpY = 100;
        bumpWidth = 50;
        bumpHeight = 150;
        bumpCol = Color.blue;
    }
    public Bumper(int x, int y, int xWidth, int yWidth, Color c)
    {
        bumpX = x;
        bumpY = y;
        bumpWidth = xWidth;
        bumpHeight = yWidth;
        bumpCol = c;

    }

    // accessor methods  (one for each field)
    public int getX()
    {
        return bumpX;
    }

    public int getY()
    {
        return bumpY;
    }

    public int getXWidth()
    {
        return bumpWidth;
    }

    public int getYWidth()
    {
        return bumpHeight;
    }

    public Color getColor()
    {
        return bumpCol;
    }

    // modifier methods  (one for each field)

    public void setX(int x)
    {
        bumpX = x;
    }

    public void setY(int y)
    {
        bumpY = y;
    }

    public void setXWidth(int x)
    {
        bumpWidth = x;
    }

    public void setYWidth(int y)
    {
        bumpHeight = y;
    }

    public void setColor(Color c)
    {
        bumpCol = c;
    }

    // instance methods

    // chooses a random (x,y) location.  Bumper stays entirely in the window.
    public void jump(int rightEdge, int bottomEdge)
    {
        // moves location to random (x, y) within the edges

        bumpX = (int)(Math.random() * (rightEdge-getXWidth()));
        bumpY = (int)(Math.random() * (bottomEdge-getYWidth()));

    }

    // draws a rectangular bumper on the buffer
    public void draw(Graphics myBuffer)
    {
        myBuffer.setColor(getColor());
        myBuffer.fillRect(getX(), getY(), getXWidth(), getYWidth());
    }
    // returns true if any part of the polkadot is inside the bumper
    public boolean inBumper(Polkadot dot)
    {
        for(int x = getX(); x <= getX() + getXWidth(); x++)   //starts at upper left corner(x,y)

            for(int y = getY(); y <= getY() + getYWidth(); y++)

                if(distance(x, y, dot.getX(), dot.getY()) <= dot.getRadius() ) //checks every point on the bumper
                    return true;
        return false;
    }
    // returns distance between (x1, y1) and (x2, y2)
    private double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
