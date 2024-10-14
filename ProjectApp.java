import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ProjectApp extends JFrame {
    private BinaryTreeApp binaryTreeApp;
    private AVLTreeApp avlTreeApp;
    public ProjectApp() {
        setTitle("Binary Tree and AVL Tree Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.decode("#2C3E50"));
        JLabel title = new JLabel("Binary Tree and AVL Tree Visualizer", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        headerPanel.add(title, BorderLayout.CENTER);
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(2, 1));
        selectionPanel.setBackground(Color.decode("#ECF0F1"));
        JButton binaryTreeButton = new JButton("Binary Tree Visualizer");
        JButton avlTreeButton = new JButton("AVL Tree Visualizer");
        selectionPanel.add(binaryTreeButton);
        selectionPanel.add(avlTreeButton);
        binaryTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (binaryTreeApp == null) {
                    binaryTreeApp = new BinaryTreeApp();
                }
                binaryTreeApp.setVisible(true);
                setVisible(false);
            }
        });
        avlTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (avlTreeApp == null) {
                    avlTreeApp = new AVLTreeApp();
                }
                avlTreeApp.setVisible(true);
                setVisible(false);
            }
        });
        add(headerPanel, BorderLayout.NORTH);
        add(selectionPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProjectApp();
            }
        });
    }
}