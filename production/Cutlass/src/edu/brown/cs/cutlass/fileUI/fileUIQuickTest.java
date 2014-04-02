/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs.cutlass.fileUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.GapContent;
import javax.swing.text.PlainDocument;

/**
 *
 * @author miles
 */
public class fileUIQuickTest implements ActionListener{

    private UndoEditorPane u;
    /**
     * @param args the command line arguments
     */
    
    public fileUIQuickTest(){
                //gui stuff, don't touch if you're only interested in messing with
        //the undo editor stuff
        JFrame j = new JFrame("test");
        j.setLayout(new BorderLayout());
        JButton undo = new JButton("undo");
        JButton redo = new JButton("redo");
        j.add(undo, BorderLayout.WEST);
        j.add(redo, BorderLayout.EAST);
        
        // actionlistening done here
        undo.addActionListener(this);
        redo.addActionListener(this);
        
        
        //undo editor instantiation here
        u = new UndoEditorPane("here is some content");
        
        
        //UI creation.
        j.add(u);
        j.setSize(300,500);
        j.setDefaultCloseOperation(3);
        j.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        //just creates an object that does everything
        // was easier this way due to listening constraints
        fileUIQuickTest t = new fileUIQuickTest();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("undo"))
            u.undo();
        else if(e.getActionCommand().equals("redo"))
            u.redo();
    }
    
}
