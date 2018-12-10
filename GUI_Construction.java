import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUI_Construction extends JFrame{

    JMenuBar mb = new JMenuBar();
    JMenu m1 = new JMenu("Menu");
    JMenu m2 = new JMenu("Options");
    JMenuItem m11 = new JMenuItem("Connect");
    JMenuItem m12 = new JMenuItem("Disconnect");
    JPanel bottomPanel = new JPanel();
    JLabel bottomLabel = new JLabel("Enter Text:");
    JTextField messageBox = new JTextField(150);
    JButton send = new JButton("Send");
    JButton clear = new JButton("Clear");
    public JTextArea messageHistory = new JTextArea();

    GUI_Construction()
    {

        JFrame clientGUI = new JFrame("Client GUI");
        clientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientGUI.setSize(2000,1250);

        messageHistory.setEditable(false);
        mb.add(m1);
        mb.add(m2);
        m1.add(m11);
        m1.add(m12);
        send.setEnabled(false);
        clear.setEnabled(false);
        m12.setEnabled(false);

        clear.addActionListener(this::clearButtonChecker);
        bottomPanel.add(bottomLabel);
        bottomPanel.add(messageBox);
        bottomPanel.add(send);
        bottomPanel.add(clear);

        clientGUI.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        clientGUI.getContentPane().add(BorderLayout.NORTH, mb);
        clientGUI.getContentPane().add(BorderLayout.CENTER, messageHistory);
        clientGUI.setVisible(true);

    }

    public static void main(String args[]) throws IOException
    {

        new GUI_Construction();

    }

    public void clearButtonChecker(ActionEvent c){

        messageBox.setText("");

    }

}
