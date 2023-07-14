package com.example.grafkom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cliping extends JFrame {
    private final Rectangle clipWindow;
    private final Rectangle shape;

    public Cliping() {
        clipWindow = new Rectangle(63, 63, 150, 150);
        shape = new Rectangle(110, 110, 270, 270);

        setTitle("Clipping Mouse");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                g2d.setColor(Color.GRAY);
                g2d.drawRect(clipWindow.x, clipWindow.y, clipWindow.width, clipWindow.height);


                g2d.setColor(Color.RED);
                g2d.drawRect(shape.x, shape.y, shape.width, shape.height);


                g2d.setClip(clipWindow);
                g2d.setColor(Color.pink);
                g2d.fillRect(shape.x, shape.y, shape.width, shape.height);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();


                clipWindow.setLocation(mouseX - clipWindow.width / 2, mouseY - clipWindow.height / 2);
                panel.repaint();
            }
        });

        getContentPane().add(panel);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Cliping clipping = new Cliping();
                clipping.setVisible(true);
            }
        });
    }
}