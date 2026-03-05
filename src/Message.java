import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Message {
    JFrame frame = new JFrame();

    //Messages
    public Message(String s) {
        JLabel label = new JLabel(s ,SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setSize(300,100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.add(label,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public Message(JFrame frame1) {
        DatabaseActions db = new DatabaseActions();
        JLabel label = new JLabel("Are you sure you want to exit?" ,SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JButton button = new JButton("Yes");
        JButton button2 = new JButton("No");

        panel.add(button, BorderLayout.EAST);
        panel.add(button2, BorderLayout.WEST);
        button.setMnemonic(KeyEvent.VK_Y);
        button.setPreferredSize(new Dimension(100,20));
        button.setToolTipText("Yes exit the program");
        button.setFont(new Font("Arial",Font.PLAIN, 16));

        button2.setMnemonic(KeyEvent.VK_N);
        button2.setPreferredSize(new Dimension(100,20));
        button2.setToolTipText("No return to menu");
        button2.setFont(new Font("Arial",Font.PLAIN, 16));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame1.setEnabled(true);
                frame.dispose();
            }
        });
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setSize(300,100);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(label,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public Message(ReadJob job,JFrame frame1){

        DatabaseActions db = new DatabaseActions();
        JLabel label = new JLabel("Are you sure you want to delete this Job?" ,SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JButton button = new JButton("Yes");
        JButton button2 = new JButton("No");

        panel.add(button, BorderLayout.EAST);
        panel.add(button2, BorderLayout.WEST);
        button.setMnemonic(KeyEvent.VK_Y);
        button.setPreferredSize(new Dimension(100,20));
        button.setToolTipText("Yes delete the job");
        button.setFont(new Font("Arial",Font.PLAIN, 16));

        button2.setMnemonic(KeyEvent.VK_N);
        button2.setPreferredSize(new Dimension(100,20));
        button2.setToolTipText("No return to menu");
        button2.setFont(new Font("Arial",Font.PLAIN, 16));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                db.delete(job.getId());
                frame1.setEnabled(true);
                Message message = new Message("Job deleted successfully");
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frame1.setEnabled(true);
                Message message = new Message("No job was deleted");
            }
        });

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setSize(300,100);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(label,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}




