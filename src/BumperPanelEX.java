import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class BumperPanelEX extends JPanel
{
    private static final int frame = 800;

    //private Dimension d = new Dimension(frame, frame);
    private BufferedImage myImage;
    private Graphics myBuffer;
    private int percent;

    private Ball[] ball = new Ball[45];
    private Bumper[] bumper = new Bumper[11];
    private Timer timer;

    private int xTerritoryStart = 0;
    private int yTerritoryStart = 0;
    private int xTerritoryFinish = 0;
    private int yTerritoryFinish = 0;
    private JLabel percentLabel;
    private JLabel horizVertLabel;
    private JLabel triesLabel;

    private Boolean vertIsTrue = true;
    private Boolean verClicked = false;
    private Boolean horClicked = false;
    private String horizVert = "Vertical";

    private Boolean ballOnRight = false;
    private Boolean ballOnBot = false;

    private Boolean ballOnLeft = false;
    private Boolean ballOnTop = false;

    private Boolean bumperOnRight = false;
    private Boolean bumperOnLeft = false;
    private Boolean bumperOnTop = false;
    private Boolean bumperOnBottom = false;


    private Boolean ballOnLeftAndRight = false;
    private Boolean ballOnTopAndBot = false;
    private Boolean hasBall = false;

    private Boolean justClicked;
    private int count = 0;

    private int limit = 0;
    private int level = 1;

    private int ballNum = 2;

    public BumperPanelEX()
    {
        setLayout(new BorderLayout());

        // adds label for percent
        percentLabel = new JLabel("Level: "+ level + "  Percentage covered: " + 0);
        percentLabel.setForeground(Color.white);
        add(percentLabel, BorderLayout.SOUTH);

        // adds  label for mode
        horizVertLabel = new JLabel("" + horizVert);
        horizVertLabel.setForeground(Color.white);
        add(horizVertLabel, BorderLayout.NORTH);

        // improves accuracy
        setPreferredSize(new Dimension(frame, frame));

        //creates image drawers
        myImage =  new BufferedImage(frame, frame, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();

        // create ball and jump
        for(int x = 0; x < ballNum; x++)
        {
            ball[x] = new Ball(0, 0, 15, Color.blue);
            ball[x].jump(frame, frame);
        }

        // add bumpers
        for(int x = 0; x < bumper.length; x++)
            bumper[x] = new Bumper(0, 0, 0, 0, Color.red);

        //set other varibales and instatiate timer

        limit = bumper.length - 1;
        timer = new Timer(10, new Listener());
        timer.start();

        addMouseListener(new Mouse());
    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(myImage, 0, 0, null);
    }

    private class Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            // clears ball trails
            myBuffer.setColor(Color.black);
            for(int x = 0; x < ballNum; x++)
            {
                myBuffer.fillOval( (int) ( ball[x].getX() - ball[x].getRadius() ),  (int) ( ball[x].getY() - ball[x].getRadius() ), (int) ( ball[x].getDiameter() ), (int) ( ball[x].getDiameter() ));
            }

            // psychadelix bumpers
            for(int x = 0; x < bumper.length; x++)
                randomColor(bumper[x]);

            // random color for rectangle
            randomColorBuffer(myBuffer);

            // move ball
            for(int y = 0; y < ballNum; y++)
                ball[y].move(frame, frame);

            // check collison
            for(int x = 0; x < bumper.length; x++)
                for(int y = 0; y < ballNum; y++)
                {
                    BumperCollision.collide(bumper[x], ball[y]);
                }

            // draw ball and bumper
            for(int x = 0; x < ballNum; x++)
                ball[x].draw(myBuffer);
            for(int x = 0; x < bumper.length; x++)
                bumper[x].draw(myBuffer);

            // draw rectangle is it is Vertical
            if(verClicked)
            {
                if(!ballOnLeftAndRight)
                    if(ballOnRight)
                        for(int x = 0; x < bumper.length; x++)
                            myBuffer.fill3DRect(0, 0, bumper[count].getX(), frame, true);
                    else if(ballOnLeft)

                        myBuffer.fill3DRect(bumper[count].getX(), 0, frame, frame, false);
                if(justClicked)
                {
                    System.out.println("The percentage is " + percentage() + " %");
                    justClicked = false;
                }
            }
            // draw rectangle is it is Horizontal
            if(horClicked)
            {
                if(!ballOnTopAndBot)
                    if(ballOnBot)
                        for(int x = 0; x < bumper.length; x++)
                            myBuffer.fill3DRect(0, 0, frame, bumper[count].getY(), true);
                    else if(ballOnTop)

                        myBuffer.fill3DRect(0, bumper[count].getY(), frame, frame,true);
                if(justClicked)
                {
                    System.out.println("The percentage is " + percentage() + " %");
                    justClicked = false;
                }
            }

            // make sure you can clear the ball trails (precaution)
            myBuffer.setColor(Color.black);

            //set all booleans false

            ballOnRight = false;
            ballOnBot = false;

            ballOnLeft = false;
            ballOnTop = false;

            ballOnLeftAndRight = false;
            ballOnTopAndBot = false;

            // execute everything
            percentLabel.setText("Level: " + level + "  Percentage Filled: " + percentage());
            horizVertLabel.setText(horizVert);
            repaint();
            // check if next level is met
            if(percentage() >= 80)
            {
                nextLevel();
            }
        }
    }

    // checks to see if the ball & dot collide
    // if so, increments hits & relocates dot

    private double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private class Mouse extends MouseAdapter
    {
        public void mouseClicked( MouseEvent e)
        {
            if (e.isMetaDown())
            {
                vertIsTrue = (!vertIsTrue);
                //sets label string
                if(vertIsTrue)
                {
                    horizVert = "Vertical";
                }
                else
                {
                    horizVert = "Horizontal";
                }
            }

            else if (e.isShiftDown()) // randomies balls
                crazyBall(ball);

            else if (vertIsTrue)
            {
                justClicked = true;
                if(count<limit)
                    count ++;
                else
                {
                    System.out.println("You Failed");
                    System.exit(0);
                }
                //create bumper at click point
                bumper[count].setX(e.getX());
                bumper[count].setY(0);

                bumper[count].setXWidth(5);
                bumper[count].setYWidth(frame);
                bumper[count].setColor(Color.red);

                verClicked = true;
                horClicked = false;
                // see where ball is
                for(int x = 0; x < ballNum; x++)
                {
                    if((ball[x].getX() - bumper[count].getX()) >= 0)
                    {
                        ballOnLeftAndRight = false;
                        ballOnRight = true;
                    }

                    if((ball[x].getX() - bumper[count].getX()) <= 0)
                    {
                        ballOnLeftAndRight = false;
                        ballOnLeft = true;
                    }
                }
                // if ball is on both sides then no filling boolean activated
                if(ballOnRight && ballOnLeft)
                {
                    ballOnLeft = false;
                    ballOnRight = false;
                    ballOnLeftAndRight = true;
                }

                // sees if you lose
                if(count == limit)
                    System.out.println("Sorry. Game over click again to end") ;
                else
                    System.out.println("Number of tries left  : " + (limit - count));
            }
            else
            {
                justClicked = true;
                if(count<limit)
                    count ++;
                else
                {
                    System.out.println("You Failed");
                    System.exit(0);
                }

                bumper[count].setX(0);
                bumper[count].setY(e.getY());

                bumper[count].setXWidth(frame);
                bumper[count].setYWidth(5);
                bumper[count].setColor(Color.red);

                horClicked = true;
                verClicked = false;
                for(int x = 0; x < ballNum; x++)
                {
                    if((ball[x].getY() - bumper[count].getY()) >= 0)
                    {
                        ballOnTopAndBot = false;
                        ballOnBot = true;
                    }
                    if((ball[x].getY() - bumper[count].getY()) <= 0)
                    {
                        ballOnTopAndBot = false;
                        ballOnTop = true;
                    }
                }
                if(ballOnBot && ballOnTop)
                {
                    ballOnTop = false;
                    ballOnBot = false;
                    ballOnTopAndBot = true;
                }

                if(count == limit)
                    System.out.println("Sorry. Game over click again to end") ;
                else
                    System.out.println("Number of tries left : " + (limit - count ) ) ;
            }

            percentLabel.setText("Level: " + level + "  Percentage Filled: " + percentage());
            horizVertLabel.setText(horizVert);
        }
    }

    public void randomColor(Bumper obj)
    {
        int red = Math.abs((int) (Math.random()  *250));
        int green = Math.abs((int) (Math.random()  *250));
        int blue = Math.abs((int) (Math.random() *250));
        obj.setColor(new Color(red, green, blue));
    }

    public void randomColorBuffer(Graphics obj)
    {

        //sets random color for bumper
        int red = Math.abs((int) (Math.random()  *250));
        int green = Math.abs((int) (Math.random()  *250));
        int blue = Math.abs((int) (Math.random() *250));
        obj.setColor(new Color(red, green, blue));
        //System.out.println("" + red + ", "+green+ ", "+blue);
    }

    public int percentage()
    {
        int pixelsColored = 0;
        int  percentage = 0;

        // goes pixel by pixel and sees if it is colored or not
        for(int x =0; x <frame; x++)
            for(int y =0; y <frame; y++)
                if(myImage.getRGB(x,y) != -16777216 )
                {
                    pixelsColored ++;
                }

         /*for(int a = 0; a < (frame / 40); a++)
            {
            for(int b = 0; b < (frame / 40); b++)
               {
               if(!hasBall(a * 10, b * 10, ball))
                  {
                  pixelsColored = pixelsColored  + (100);
                  }
               }
            }
               /*else
               {
                  pixelsColored = pixelsColored + findEmpty(x, y);
               }*/


        // converts to percenatge colored
        double complete = frame * frame;

        double percentage1 = (pixelsColored/complete);
        percentage = (int)(percentage1 * 100);
        return percentage;
    }

    public void crazyBall(Ball[] ball)
    {
        for(int x = 0; x < ballNum; x++)
        {
            ball[x].setDx((Math.random()* 13)-5 );
            ball[x].setDy((Math.random()* 13)-5 );
        }
    }


    public void nextLevel()
    {
        // increases level, decreases balls
        ballNum++;
        count = 0;
        level++;
        System.out.println("n\n\n\n\nCongratulations: You are now on level " + level+"\nYou have " + limit + " tries left");

        ballNum = ballNum;
        // ball = new Ball[ballNum];
        // randomises balls
        for(int x = 0; x < ballNum; x++)
        {
            ball[x] = new Ball(0, 0, 15, Color.blue);
            ball[x].jump(frame, frame);
        }

        //sets all bumpers to corner
        for(int x = 0; x < bumper.length; x ++)
        {
            bumper[x].setX(0);
            bumper[x].setY(0);
            bumper[x].setXWidth(0);
            bumper[x].setYWidth(0);
        }
        //myBuffer.setColor(Color.black);
        //myBuffer.drawRect(0,0,frame,frame);

        // sets booleans false
        myBuffer.clearRect(0,0,frame,frame);
        ballOnRight = false;
        ballOnBot = false;

        ballOnLeft = false;
        ballOnTop = false;

        ballOnLeftAndRight = false;
        ballOnTopAndBot = false;

        repaint();
    }
}

