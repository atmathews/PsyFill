//Instruction Panel
//www.eca.usp.br
//www.dreamincode.net
//Julie Vrabel, Ashish Matthews, Zach Williams

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SaneInstructions extends JPanel
{
    //uses display 10
    Display10 display;

    public SaneInstructions()
    {
        //set the layout
        setLayout(new BorderLayout());
        //add display
        display = new Display10();
        add(display, BorderLayout.NORTH);
        //add panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel, BorderLayout.SOUTH);
        //add button so that you can play
        JButton playButton = new JButton("I Am Ready To Play!");
        playButton.addActionListener(new PlayButtonListener());
        panel.add(playButton);


    }
    //the button listener for the "im read to play" button
    private class PlayButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            display.playGame();

        }
    }
    public void paintComponent(Graphics g)
    {
        //The typed up instructions on the panel, in the center
        super.paintComponent(g);
        g.setFont(new Font("SansSerif",Font.BOLD,26));
        g.setColor(Color.black.darker());
        g.drawString("INSTRUCTIONS FOR PYSCHEDELIC",5, 40);
        g.drawString("JAZ BALL", 147, 74);
        g.setFont(new Font("SansSerif",Font.BOLD,20));
        g.drawString("OBJECT OF THE GAME:", 7,105);
        g.setFont(new Font("SansSerif",Font.BOLD,15));
        g.drawString("After you have corraled the balls off such that least 80% of the", 15,133);


        g.drawString("field is covered, you move on to the next level.", 17, 150);
        g.drawString("You have 10 tries to complete this task for each level.", 17, 167);
        g.drawString("For the rest of the levels, they will get increasingly harder by", 17, 218);
        g.drawString("increasing the number of balls moving", 22, 233);
        g.drawString("What keys to use and how to use them:", 9, 275);
        g.drawString("Click the right mouse to switch between vertical and horizontal", 17, 292);
        g.drawString("lines when you click on the screen", 17, 309);
        g.drawString("You are able to close off areas by clicking with the left mouse", 17, 326);
        g.drawString("If you click in an area in between the balls, you create a line,", 17, 343);
        g.drawString("if you click in an area where the balls are on the same side,", 17, 360);
        g.drawString("then you create an enclosed area, depending on if your directional", 15, 377);
        g.drawString("wall builder is set to vertical or horizontal", 15, 394);
        g.drawString("Press Shift + click to change the ball's speed and direction", 17, 411);
    }

}