AVL and Binary Tree Visualizer:-
This project provides a graphical visualizer for AVL trees and Binary Search Trees (BSTs), allowing users to insert, delete, and reset the tree while visualizing the structure and rotations (for AVL trees). The application is built using Java Swing for the graphical user interface and showcases basic concepts of data structures.

Features:-
AVL Tree Visualizer: Automatically balances the tree through rotations and displays the structure.
Binary Search Tree Visualizer: Allows standard operations on a BST without rotations.
Graphical Representation: Nodes and connections between them are represented visually.
Insert/Delete Operations: Users can add and remove nodes in both tree visualizers.
Reset Functionality: Clear and reset the tree to start afresh.

Prerequisites:-
To run this project, ensure you have the following installed:
Java Development Kit (JDK) 8 or higher
Git (optional for cloning the repository)

How to Run:-
Step 1: Clone the Repository
To clone the project to your local machine, open a terminal and run:

git clone https://github.com/your-username/AVL_BST_Tree_Visualizer.git

Step 2: Compile the Code
Navigate to the project directory and compile the Java files:

cd AVL_BST_Tree_Visualizer/Java
javac *.java

Step 3: Create JAR File
After compiling the classes, create an executable JAR file:

jar cfm AVL_BST_Tree.jar MANIFEST.MF *.class

Step 4: Run the JAR
To launch the application, execute the following command:

java -jar AVL_BST_Tree.jar

Project Structure:-
AVLTree.java: Contains the implementation of the AVL Tree data structure, including insertion, deletion, and rotation logic.
BinaryTreeApp.java: Visualizes the Binary Search Tree and handles user interactions.
AVLTreeApp.java: Visualizes the AVL Tree and handles user interactions, including visualizing rotations.
ProjectApp.java: The main entry point of the application, providing a selection between the Binary Search Tree and AVL Tree visualizers.
TreePanel.java: A custom JPanel that draws the trees graphically.

Usage:-
Inserting Values: Enter a numeric value in the input field and press the "Insert" button to add it to the tree.
Deleting Values: Enter a numeric value in the input field and press the "Delete" button to remove it from the tree.
Reset Tree: Press the "Reset" button to clear the tree and start again.
Tree Selection: Choose between Binary Tree and AVL Tree visualization from the main menu.

Contribution:-
Feel free to contribute to this project by opening issues or submitting pull requests. For major changes, please discuss them via an issue first.

License:-
This project is licensed under the MIT License. See the LICENSE file for more details.

Contact:-
If you have any questions or issues, feel free to reach out to me:

Email: harshagar122005@gmail.com
GitHub: harshagar12
