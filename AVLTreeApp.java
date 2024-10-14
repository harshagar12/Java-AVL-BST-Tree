/*import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
class AVLTree<T extends Comparable<T>> {
    private Node<T> root;
    private String rotationInfo = "";
    public AVLTree() {
        root = null;
    }
    public String getRotationInfo() {
        return rotationInfo;
    }
    public void insert(T value) {
        root = insert(root, value);
    }
    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (value.compareTo(node.left.value) < 0) {
                rotationInfo = "Right Rotation on " + node.value;
                return rotateRight(node);
            } else {
                rotationInfo = "Left-Right Rotation on " + node.value;
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balanceFactor < -1) {
            if (value.compareTo(node.right.value) > 0) {
                rotationInfo = "Left Rotation on " + node.value;
                return rotateLeft(node);
            } else {
                rotationInfo = "Right-Left Rotation on " + node.value;
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        rotationInfo = "";
        return node;
    }
    private int height(Node<T> node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }
    private int getBalanceFactor(Node<T> node) {
        return height(node.left) - height(node.right);
    }
    private Node<T> rotateLeft(Node<T> node) {
        Node<T> pivot = node.right;
        node.right = pivot.left;
        pivot.left = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        pivot.height = Math.max(height(pivot.left), height(pivot.right)) + 1;
        return pivot;
    }
    private Node<T> rotateRight(Node<T> node) {
        Node<T> pivot = node.left;
        node.left = pivot.right;
        pivot.right = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        pivot.height = Math.max(height(pivot.left), height(pivot.right)) + 1;
        return pivot;
    }
    public Node<T> getRoot() {
        return root;
    }
    public void clear() {
        root = null;
        rotationInfo = "";
    }
    public static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;
        int height;
        Node(T value) {
            this.value = value;
            left = null;
            right = null;
            height = 0;
        }
    }
}
public class AVLTreeApp extends JFrame {
    private AVLTree<Integer> tree;
    private JTextField inputField;
    private TreePanel treePanel;
    private JLabel statusLabel;
    public AVLTreeApp() {
        tree = new AVLTree<>();
        setTitle("AVL Tree Visualization with Rotations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.decode("#2C3E50"));
        JLabel title = new JLabel("AVL Tree Visualizer", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        headerPanel.add(title, BorderLayout.CENTER);
        inputField = new JTextField(10);
        inputField.setMargin(new Insets(10, 10, 10, 10));
        inputField.setBackground(Color.decode("#F7F7F7"));
        inputField.setForeground(Color.decode("#333333"));
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel label = new JLabel("Enter value:");
        label.setForeground(Color.decode("#333333"));
        JButton insertButton = new JButton("Insert");
        JButton resetButton = new JButton("Reset Tree");
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.decode("#ECF0F1"));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(label);
        inputPanel.add(inputField);
        inputPanel.add(insertButton);
        inputPanel.add(resetButton);
        statusLabel = new JLabel("Welcome! Insert values to visualize the AVL Tree.");
        statusLabel.setForeground(Color.decode("#333333"));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(statusLabel, BorderLayout.SOUTH);
        treePanel = new TreePanel();
        treePanel.setPreferredSize(new Dimension(600, 400));
        treePanel.setBackground(Color.decode("#F7F7F7"));
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    tree.insert(value);
                    inputField.setText("");
                    String rotationMsg = tree.getRotationInfo().isEmpty() ? "No rotation." : tree.getRotationInfo();
                    statusLabel.setText("Inserted value: " + value + ". " + rotationMsg);
                    treePanel.repaint(); 
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Error: Please enter a valid integer.");
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tree.clear();
                statusLabel.setText("Tree has been reset.");
                treePanel.repaint(); 
            }
        });
        add(headerPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(treePanel, BorderLayout.CENTER);
        setVisible(true);
    }
    private class TreePanel extends JPanel {
        public TreePanel() {
            setBackground(Color.decode("#F7F7F7"));
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, tree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
        }
        private void drawTree(Graphics g, AVLTree.Node<Integer> node, int x, int y, int xOffset) {
            if (node == null) return;
            g.setColor(Color.decode("#3498DB"));
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.WHITE);
            String text = node.value.toString();
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g.drawString(text, x - (textWidth / 2), y + 5);
            if (node.left != null) {
                g.setColor(Color.decode("#3498DB"));
                g.drawLine(x, y, x - xOffset, y + 60);
                drawTree(g, node.left, x - xOffset, y + 60, xOffset / 2);
            }
            if (node.right != null) {
                g.setColor(Color.decode("#3498DB"));
                g.drawLine(x, y, x + xOffset, y + 60);
                drawTree(g, node.right, x + xOffset, y + 60, xOffset / 2);
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AVLTreeApp());
    }
}*/

import java.awt.*;
import javax.swing.*;

class AVLTree<T extends Comparable<T>> {
    private Node<T> root;
    private String rotationInfo = "";

    public AVLTree() {
        root = null;
    }

    public String getRotationInfo() {
        return rotationInfo;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // No duplicates allowed
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return balance(node, value);
    }

    private int height(Node<T> node) {
        return node == null ? -1 : node.height;
    }

    private int getBalanceFactor(Node<T> node) {
        return height(node.left) - height(node.right);
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> pivot = node.right;
        node.right = pivot.left;
        pivot.left = node;
        updateHeight(node);
        updateHeight(pivot);
        return pivot;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> pivot = node.left;
        node.left = pivot.right;
        pivot.right = node;
        updateHeight(node);
        updateHeight(pivot);
        return pivot;
    }

    private void updateHeight(Node<T> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private Node<T> balance(Node<T> node, T value) {
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (value.compareTo(node.left.value) < 0) {
                rotationInfo = "Right Rotation on " + node.value;
                return rotateRight(node);
            } else {
                rotationInfo = "Left-Right Rotation on " + node.value;
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balanceFactor < -1) {
            if (value.compareTo(node.right.value) > 0) {
                rotationInfo = "Left Rotation on " + node.value;
                return rotateLeft(node);
            } else {
                rotationInfo = "Right-Left Rotation on " + node.value;
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        rotationInfo = "";
        return node;
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) return null;
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            // Node to be deleted found
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node<T> minNode = findMin(node.right);
                node.value = minNode.value;
                node.right = delete(node.right, minNode.value);
            }
        }
        if (node == null) return null;
        updateHeight(node);
        return balanceAfterDelete(node);
    }

    private Node<T> balanceAfterDelete(Node<T> node) {
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) >= 0) {
                rotationInfo = "Right Rotation on " + node.value;
                return rotateRight(node);
            } else {
                rotationInfo = "Left-Right Rotation on " + node.value;
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) <= 0) {
                rotationInfo = "Left Rotation on " + node.value;
                return rotateLeft(node);
            } else {
                rotationInfo = "Right-Left Rotation on " + node.value;
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        rotationInfo = "";
        return node;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void clear() {
        root = null;
        rotationInfo = "";
    }

    public static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;
        int height;

        Node(T value) {
            this.value = value;
            left = null;
            right = null;
            height = 0;
        }
    }
}

public class AVLTreeApp extends JFrame {
    private AVLTree<Integer> tree;
    private JTextField inputField, deleteField;
    private TreePanel treePanel;
    private JLabel statusLabel;

    public AVLTreeApp() {
        tree = new AVLTree<>();
        setTitle("AVL Tree Visualization with Rotations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.decode("#2C3E50"));
        JLabel title = new JLabel("AVL Tree Visualizer", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        headerPanel.add(title, BorderLayout.CENTER);

        // Input and Buttons Panel
        inputField = new JTextField(10);
        deleteField = new JTextField(10);
        JButton insertButton = new JButton("Insert");
        JButton deleteButton = new JButton("Delete");
        JButton resetButton = new JButton("Reset Tree");

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.decode("#ECF0F1"));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Insert value:"));
        inputPanel.add(inputField);
        inputPanel.add(insertButton);
        inputPanel.add(new JLabel("Delete value:"));
        inputPanel.add(deleteField);
        inputPanel.add(deleteButton);
        inputPanel.add(resetButton);

        // Status Label
        statusLabel = new JLabel("Welcome! Insert values to visualize the AVL Tree.");
        statusLabel.setForeground(Color.decode("#333333"));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Tree Panel
        treePanel = new TreePanel();
        treePanel.setPreferredSize(new Dimension(600, 400));
        treePanel.setBackground(Color.decode("#F7F7F7"));

        // Button Listeners
        insertButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                tree.insert(value);
                inputField.setText("");
                String rotationMsg = tree.getRotationInfo().isEmpty() ? "No rotation." : tree.getRotationInfo();
                statusLabel.setText("Inserted value: " + value + ". " + rotationMsg);
                treePanel.repaint();
            } catch (NumberFormatException ex) {
                statusLabel.setText("Error: Please enter a valid integer.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(deleteField.getText());
                tree.delete(value);
                deleteField.setText("");
                statusLabel.setText("Deleted value: " + value + ". " + tree.getRotationInfo());
                treePanel.repaint();
            } catch (NumberFormatException ex) {
                statusLabel.setText("Error: Please enter a valid integer.");
            }
        });

        resetButton.addActionListener(e -> {
            tree.clear();
            statusLabel.setText("Tree has been reset.");
            treePanel.repaint();
        });

        // Layout Setup
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(statusLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(treePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Tree Drawing Panel
    private class TreePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (tree.getRoot() != null) {
                drawTree(g, getWidth() / 2, 50, tree.getRoot(), getWidth() / 4);
            }
        }
    
        private void drawTree(Graphics g, int x, int y, AVLTree.Node<Integer> node, int gap) {
            if (node == null) return;
    
            // Draw left subtree
            if (node.left != null) {
                // Set line color to black before drawing the line
                g.setColor(Color.BLACK);
                g.drawLine(x - gap, y + 50, x, y); // Line to the left child
                drawTree(g, x - gap, y + 50, node.left, gap / 2);
            }
    
            // Draw right subtree
            if (node.right != null) {
                // Set line color to black before drawing the line
                g.setColor(Color.BLACK);
                g.drawLine(x + gap, y + 50, x, y); // Line to the right child
                drawTree(g, x + gap, y + 50, node.right, gap / 2);
            }
    
            // Draw the node (as a filled circle)
            int nodeRadius = 20;
            g.setColor(Color.decode("#2980B9")); // Blue color for the node
            g.fillOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius); // Draw filled circle
    
            // Draw the border of the node
            g.setColor(Color.BLACK);
            g.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius); // Draw circle border
    
            // Draw the node value inside the circle
            g.setColor(Color.WHITE); // Text color (white)
            String valueStr = String.valueOf(node.value);
            FontMetrics fm = g.getFontMetrics();
            int valueWidth = fm.stringWidth(valueStr);
            int valueHeight = fm.getAscent();
            g.drawString(valueStr, x - valueWidth / 2, y + valueHeight / 4); // Center the text inside the node
        }
    }    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AVLTreeApp());
    }
}