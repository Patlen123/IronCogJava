package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainMenu extends JFrame implements ActionListener {
    private JButton btnNewEvent;
    private JButton btnExitEvent;
    public MainMenu()   {
        btnNewEvent.addActionListener( this );
    }

    public void actionPerformed (ActionEvent e)  {
        if (e.getSource() == btnNewEvent)   {
            EventWindow window = new EventWindow("");
            window.setVisible(true);
            this.dispose();
        }   else if (e.getSource() == btnExitEvent) {

        }

    }

}
