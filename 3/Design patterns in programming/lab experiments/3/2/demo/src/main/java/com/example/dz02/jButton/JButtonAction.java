
package com.example.dz02.jButton;

import javax.swing.JButton;

import com.example.dz02.TextEditor;
import com.example.dz02.jMenuItems.JMenuItemBase;

public class JButtonAction extends JButton {

    public JButtonAction(String text, TextEditor editor, JMenuItemBase jMenuItemBase) {
        super(text);
        this.addActionListener(e -> {
            jMenuItemBase.templateMethod();
            editor.requestFocusInWindow();
        });
    }

}
