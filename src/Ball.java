//package PsyFill;

import java.awt.*;
public class Ball extends Polkadot
{
    private double dx;       // pixels to move each time step() is called.
    private double dy;
    private int reverseX = 0;
    private int reverseY = 0;
    /**********************************************************************************/

    // constructors

    public Ball()         //default constructor
    {
        super(0, 0, 50, Color.red);
        dx = Math.random() * 14 - 6;          // to move vertically
        dy = Math.random() * 14 - 6;          // to move sideways
    }
    public Ball(double X1, double Y1, double myDiameter, Color myColor)
    {
        super(X1, Y1, myDiameter, myColor);
        dx = Math.random()* 12 - 6;
        dy = Math.random() * 12 - 6;
    }

    /**************************************************************************/
    //modifier methods
    public void setDx(double x)
    {
        dx = x;
    }
    public void setDy(double y)
    {
        dy = y;
    }

    //accessor methods
    public double getDx()
    {
        return dx;
    }
    public double getDy()
    {
        return dy;
    }

    //instance methods
    public void move(double rightEdge, double bottomEdge)
    {

        setX(getX()+ dx);										// x = x + dx
        setY(getY()+ dy);

        // check for left & right edge bounces
        if(getX() >= rightEdge - getRadius())			//hits the right edge
        {
            setX(rightEdge - getRadius());
            dx = dx * -1;
            setRandomColor();
        }
        else if(getX() <= 0 + getRadius())				//left edge
        {
            setX(0 + getRadius());
            dx = dx * -1;
            setRandomColor();
        }
        else if(getY() <= 0+getRadius())					//top edge
        {
            setY(0 + getRadius());
            dy = dy * -1;
            setRandomColor();
        }
        else if(getY() >= bottomEdge - getRadius())	 //bottom edge
        {
            setY(bottomEdge - getRadius());
            dy = dy * -1;
            setRandomColor();
        }
    }


    public void draw(Graphics myBuffer)
    {
        myBuffer.setColor(myColor);
        myBuffer.fillOval((int)(myX - myRadius), (int)(myY-myRadius), (int)myDiameter, (int)myDiameter);

    }

    public void setRandomColor()
    {

        int red = Math.abs((int) (Math.random()  *250));
        int green = Math.abs((int) (Math.random()  *250));
        int blue = Math.abs((int) (Math.random() *250));
        myColor = new Color(red, green, blue);
        //System.out.println("" + red + ", "+green+ ", "+blue);
    }

}