/*import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;
    public BinaryTree() {
        root = null;
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
        }
        return node;
    }
    public Node<T> getRoot() {
        return root;
    }
    public void clear() {
        root = null;
    }
    public ArrayList<T> inOrderTraversal() {
        ArrayList<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }
    private void inOrderTraversal(Node<T> node, ArrayList<T> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.value);
            inOrderTraversal(node.right, result);
        }
    }
    public ArrayList<T> preOrderTraversal() {
        ArrayList<T> result = new ArrayList<>();
        preOrderTraversal(root, result);
        return result;
    }
    private void preOrderTraversal(Node<T> node, ArrayList<T> result) {
        if (node != null) {
            result.add(node.value);
            preOrderTraversal(node.left, result);
            preOrderTraversal(node.right, result);
        }
    }
    public ArrayList<T> postOrderTraversal() {
        ArrayList<T> result = new ArrayList<>();
        postOrderTraversal(root, result);
        return result;
    }
    private void postOrderTraversal(Node<T> node, ArrayList<T> result) {
        if (node != null) {
            postOrderTraversal(node.left, result);
            postOrderTraversal(node.right, result);
            result.add(node.value);
        }
    }
    public static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;
        Node(T value) {
            this.value = value;
            left = null;
            right = null;
        }
    }
}
public class BinaryTreeApp extends JFrame {
    private BinaryTree<Integer> tree;
    private JTextField inputField;
    private JTextArea traversalOutput;
    private TreePanel treePanel;
    public BinaryTreeApp() {
        tree = new BinaryTree<>();
        setTitle("Binary Tree Traversal and Visualization");
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
        traversalOutput = new JTextArea(5, 30);
        traversalOutput.setEditable(false);
        traversalOutput.setMargin(new Insets(10, 10, 10, 10));
        traversalOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(traversalOutput);
        JPanel traversalPanel = new JPanel();
        traversalPanel.setLayout(new BorderLayout());
        traversalPanel.add(scrollPane, BorderLayout.CENTER);
        traversalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        treePanel = new TreePanel();
        treePanel.setPreferredSize(new Dimension(800, 400));  // Adjust to your liking
        treePanel.setBackground(Color.decode("#F7F7F7"));
        inputField = new JTextField(10);
        inputField.setMargin(new Insets(10, 10, 10, 10));
        inputField.setBackground(Color.decode("#F7F7F7"));
        inputField.setForeground(Color.decode("#333333"));
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton insertButton = new JButton("Insert");
        JButton resetButton = new JButton("Reset Tree");
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));  // For compact spacing
        inputPanel.add(new JLabel("Enter value:"));
        inputPanel.add(inputField);
        inputPanel.add(insertButton);
        inputPanel.add(resetButton);
        JButton inOrderButton = new JButton("In-Order");
        JButton preOrderButton = new JButton("Pre-Order");
        JButton postOrderButton = new JButton("Post-Order");
        JPanel traversalButtonPanel = new JPanel();
        traversalButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        traversalButtonPanel.add(inOrderButton);
        traversalButtonPanel.add(preOrderButton);
        traversalButtonPanel.add(postOrderButton);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(traversalButtonPanel, BorderLayout.SOUTH);
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    tree.insert(value);
                    inputField.setText("");
                    treePanel.repaint();  
                } catch (NumberFormatException ex) {
                    traversalOutput.setText("Error: Please enter a valid integer.");
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tree.clear();
                traversalOutput.setText("");
                treePanel.repaint();  
            }
        });
        inOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> result = tree.inOrderTraversal();
                traversalOutput.setText("In-Order: " + result.toString());
            }
        });
        preOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> result = tree.preOrderTraversal();
                traversalOutput.setText("Pre-Order: " + result.toString());
            }
        });
        postOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> result = tree.postOrderTraversal();
                traversalOutput.setText("Post-Order: " + result.toString());
            }
        });
        add(headerPanel, BorderLayout.NORTH);     
        add(traversalPanel, BorderLayout.NORTH);  
        add(treePanel, BorderLayout.CENTER);       
        add(bottomPanel, BorderLayout.SOUTH);   

        setVisible(true);
    }
    private class TreePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, tree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
        }
        private void drawTree(Graphics g, BinaryTree.Node<Integer> node, int x, int y, int xOffset) {
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BinaryTreeApp();
            }
        });
    }
}*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;

class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;

    public BinaryTree() {
        root = null;
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
        }
        return node;
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        int cmp = value.compareTo(node.value);

        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            // Node to be deleted found
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            Node<T> temp = findMin(node.right);
            node.value = temp.value;
            node.right = delete(node.right, temp.value); // Delete the inorder successor
        }

        return node;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void clear() {
        root = null;
    }

    public ArrayList<T> inOrderTraversal() {
        ArrayList<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(Node<T> node, ArrayList<T> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.value);
            inOrderTraversal(node.right, result);
        }
    }

    public ArrayList<T> preOrderTraversal() {
        ArrayList<T> result = new ArrayList<>();
        preOrderTraversal(root, result);
        return result;
    }

    private void preOrderTraversal(Node<T> node, ArrayList<T> result) {
        if (node != null) {
            result.add(node.value);
            preOrderTraversal(node.left, result);
            preOrderTraversal(node.right, result);
        }
    }

    public ArrayList<T> postOrderTraversal() {
        ArrayList<T> result = new ArrayList<>();
        postOrderTraversal(root, result);
        return result;
    }

    private void postOrderTraversal(Node<T> node, ArrayList<T> result) {
        if (node != null) {
            postOrderTraversal(node.left, result);
            postOrderTraversal(node.right, result);
            result.add(node.value);
        }
    }

    public static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            left = null;
            right = null;
        }
    }
}

public class BinaryTreeApp extends JFrame {
    private BinaryTree<Integer> tree;
    private JTextField inputField;
    private JTextArea traversalOutput;
    private TreePanel treePanel;
    private JTextField deleteField;  // Field for deletion input
    private JButton deleteButton;    // Button to delete a node

    public BinaryTreeApp() {
        tree = new BinaryTree<>();
        setTitle("Binary Tree Traversal and Visualization");
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

        traversalOutput = new JTextArea(5, 30);
        traversalOutput.setEditable(false);
        traversalOutput.setMargin(new Insets(10, 10, 10, 10));
        traversalOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(traversalOutput);

        JPanel traversalPanel = new JPanel();
        traversalPanel.setLayout(new BorderLayout());
        traversalPanel.add(scrollPane, BorderLayout.CENTER);
        traversalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        treePanel = new TreePanel();
        treePanel.setPreferredSize(new Dimension(800, 400));  
        treePanel.setBackground(Color.decode("#F7F7F7"));

        inputField = new JTextField(10);
        inputField.setMargin(new Insets(10, 10, 10, 10));
        inputField.setBackground(Color.decode("#F7F7F7"));
        inputField.setForeground(Color.decode("#333333"));
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton insertButton = new JButton("Insert");
        JButton resetButton = new JButton("Reset Tree");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));  
        inputPanel.add(new JLabel("Enter value:"));
        inputPanel.add(inputField);
        inputPanel.add(insertButton);
        inputPanel.add(resetButton);

        JButton inOrderButton = new JButton("In-Order");
        JButton preOrderButton = new JButton("Pre-Order");
        JButton postOrderButton = new JButton("Post-Order");

        JPanel traversalButtonPanel = new JPanel();
        traversalButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        traversalButtonPanel.add(inOrderButton);
        traversalButtonPanel.add(preOrderButton);
        traversalButtonPanel.add(postOrderButton);

        // Adding delete input and button
        deleteField = new JTextField(10);
        deleteField.setMargin(new Insets(10, 10, 10, 10));
        deleteField.setBackground(Color.decode("#F7F7F7"));
        deleteField.setForeground(Color.decode("#333333"));
        deleteField.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteButton = new JButton("Delete");

        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        deletePanel.add(new JLabel("Delete value:"));
        deletePanel.add(deleteField);
        deletePanel.add(deleteButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(traversalButtonPanel, BorderLayout.SOUTH);
        bottomPanel.add(deletePanel, BorderLayout.CENTER);  // Add delete panel to bottom panel

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    tree.insert(value);
                    inputField.setText("");
                    treePanel.repaint();  
                } catch (NumberFormatException ex) {
                    traversalOutput.setText("Error: Please enter a valid integer.");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tree.clear();
                traversalOutput.setText("");
                treePanel.repaint();  
            }
        });

        inOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> result = tree.inOrderTraversal();
                traversalOutput.setText("In-Order: " + result.toString());
            }
        });

        preOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> result = tree.preOrderTraversal();
                traversalOutput.setText("Pre-Order: " + result.toString());
            }
        });

        postOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> result = tree.postOrderTraversal();
                traversalOutput.setText("Post-Order: " + result.toString());
            }
        });

        // Action listener for delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(deleteField.getText());
                    tree.delete(value);
                    deleteField.setText("");
                    treePanel.repaint();  
                } catch (NumberFormatException ex) {
                    traversalOutput.setText("Error: Please enter a valid integer.");
                }
            }
        });

        add(headerPanel, BorderLayout.NORTH);     
        add(traversalPanel, BorderLayout.NORTH);  
        add(treePanel, BorderLayout.CENTER);       
        add(bottomPanel, BorderLayout.SOUTH);   

        setVisible(true);
    }

    private class TreePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (tree.getRoot() != null) {
                drawTree(g, tree.getRoot(), getWidth() / 2, 40, getWidth() / 4);
            }
        }

        private void drawTree(Graphics g, BinaryTree.Node<Integer> node, int x, int y, int xOffset) {
            if (node != null) {
                g.setColor(Color.BLACK);
                g.fillOval(x - 15, y - 15, 30, 30);
                g.setColor(Color.WHITE);
                g.drawString(node.value.toString(), x - 7, y + 4);

                if (node.left != null) {
                    g.setColor(Color.BLACK);
                    g.drawLine(x - 5, y + 15, x - xOffset + 5, y + 50 - 15);
                    drawTree(g, node.left, x - xOffset, y + 50, xOffset / 2);
                }
                if (node.right != null) {
                    g.setColor(Color.BLACK);
                    g.drawLine(x + 5, y + 15, x + xOffset - 5, y + 50 - 15);
                    drawTree(g, node.right, x + xOffset, y + 50, xOffset / 2);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BinaryTreeApp());
    }
}
