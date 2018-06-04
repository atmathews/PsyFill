
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
public class Display10 extends JPanel
{
    private JLabel[] bits;
    public Display10()
    {
        setLayout(new GridLayout(1, 8));
        bits = new JLabel[8];
        for(int x = 0; x < bits.length; x++)
        {
            bits[x] = new JLabel("", SwingConstants.CENTER);
            bits[x].setFont(new Font("Serif", Font.BOLD, 50));
            add(bits[x]);
        }
    }
    public void playGame()
    {
        JFrame frame3 = new JFrame("Psychedelic Jezz Ball.");

        //frame3.setSize(500, 500);
        frame3.setLocation(400, 150);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BumperPanelEX pane = new BumperPanelEX();
        frame3.setContentPane(pane);
        frame3.pack();

        frame3.setVisible(true);

    }

    public void exit()
    {

    }

}