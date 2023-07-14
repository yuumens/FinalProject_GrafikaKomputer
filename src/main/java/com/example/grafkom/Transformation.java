package com.example.grafkom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class Transformation extends JFrame {
    private Point initialMousePosition;
    private AffineTransform transform;

    public Transformation() {
        setTitle("Transformasi Mouse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        transform = new AffineTransform();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialMousePosition = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                initialMousePosition = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (initialMousePosition != null) {
                    int dx = e.getX() - initialMousePosition.x;
                    int dy = e.getY() - initialMousePosition.y;
                    transform.translate(dx, dy);
                    repaint();

                    initialMousePosition = e.getPoint();
                }
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(transform);

        // Draw a rectangle at the transformed position
        g2d.drawRect(250, 250, 250, 250);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Transformation app = new Transformation();
                app.setVisible(true);
            }
        });
    }
}
