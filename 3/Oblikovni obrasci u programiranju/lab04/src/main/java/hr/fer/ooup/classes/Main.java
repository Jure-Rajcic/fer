// package hr.fer.ooup.classes;

// import javax.sound.sampled.Line;
// import javax.swing.*;
// import javax.swing.filechooser.FileNameExtensionFilter;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.io.File;
// import java.util.Scanner;

// public class Main {
//     public static void main(String[] args) throws Exception{
//         // Create and set up the window.
//         JFrame frame = new JFrame("File Chooser Demo");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(300, 200);

//         // Create the button
//         JButton button = new JButton("Select a File");

//         // Add action listener to the button
//         button.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 JFileChooser fileChooser = new JFileChooser(new File("data/models/"));
//                 fileChooser.setFileFilter(new FileNameExtensionFilter("Model Files", "model"));

//                 int returnValue = fileChooser.showOpenDialog(null);
//                 if (returnValue == JFileChooser.APPROVE_OPTION) {
//                     File selectedFile = fileChooser.getSelectedFile();
//                     System.out.println("Selected file: " + selectedFile.getAbsolutePath());
//                     // read all lines from the file
//                     try (Scanner sc = new Scanner(selectedFile)) {
//                         while (sc.hasNextLine()) {
//                             System.out.println(sc.nextLine());
//                         }
//                     } catch (Exception ex) {
//                         ex.printStackTrace();
//                     }
//                 }
//             }
//         });

//         // Add button to the frame
//         frame.getContentPane().add(button, BorderLayout.CENTER);

//         // Display the window.
//         frame.setVisible(true);
//     }
// }
