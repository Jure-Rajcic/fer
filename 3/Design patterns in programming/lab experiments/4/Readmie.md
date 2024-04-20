# Vector Graphics Editor in Java - User Guide

This guide provides comprehensive instructions for using the Vector Graphics Editor, a Java application, on macOS. The Graphical User Interface (GUI) provides you with seven main tools to interact with the program.

## Tools Overview

1. **Line:** This tool allows you to create and add new lines to your canvas.

2. **Oval:** Use this tool to create and add new ovals to your canvas.

3. **Select State:** This state allows you to click and select existing graphical objects on your canvas, such as lines and ovals. Selected objects will display their hitboxes and hotpoints. You can modify these graphical objects by dragging the selected hotpoint. In this state, you can group multiple objects by holding the 'Control' key and pressing 'G' after your selection. This creates a 'Group' graphical object which can be grouped with other objects. If needed, groups can be ungrouped by pressing 'U'. You can also move selected objects with arrow keys. Pressing '+' or '-' is equivalent to the "move forward and move backward" commands.

4. **Erase State:** This state lets you erase graphical objects from the canvas. Just hold the mouse button and move the cursor over the objects you want to erase. When you release the button, all objects your cursor passed over will be removed.

5. **SVG Export:** This tool allows you to export the canvas painting as a vector image in SVG format.

6. **Load:** This tool lets you load a canvas state from a custom format file.

7. **Save:** This tool enables you to save your current canvas state into a custom format file.

## Starting the Application

The following steps guide you through building and running the application:

1. Navigate to the root directory of the project, where the 'pom.xml' file is located.

2. Execute the command `mvn clean install` to build the project.

3. Run the application with the command: `java -cp target/lab04-1.0-SNAPSHOT.jar hr.fer.ooup.classes.GUI`.

## Design Patterns Used

In the development of this solution, we will utilize the following design patterns:

1. **Observer:** Defines the relationship between the drawing data model and display components.
2. **Composite:** Allows operations to be carried out transparently on both individual and grouped elements.
3. **Prototype:** Facilitates the creation of a toolbar for inputting new drawing elements (shapes) that is independent of the supported specific geometric shapes.
4. **Factory:** For creating specific shapes based on symbolic name when loading the drawing.
5. **State:** Allows the addition of new tools without changes in the component that processes user input (mouse movements, keyboard input, etc.).
6. **Bridge:** For transparent drawing and exporting to different image formats like SVG format.

