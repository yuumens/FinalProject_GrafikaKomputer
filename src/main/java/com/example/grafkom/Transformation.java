package com.example.grafkom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class Transformation extends JFrame {
    private Point initialMousePosition;
    private final AffineTransform transform;
    private final JLabel transformedPositionLabel;

    public Transformation() {
        setTitle("Transformasi - Inputan Mouse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        transform = new AffineTransform();
        transformedPositionLabel = new JLabel();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialMousePosition = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                initialMousePosition = null;
            }
        });

        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    transform.rotate(-Math.toRadians(10));
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    transform.rotate(Math.toRadians(10));
                } else if (keyCode == KeyEvent.VK_UP) {
                    transform.scale(1.1, 1.1);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    transform.scale(0.9, 0.9);
                }
                updateTransformedPosition();
                repaint();
            }

            public void keyReleased(KeyEvent e) {
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (initialMousePosition != null) {
                    int dx = e.getX() - initialMousePosition.x;
                    int dy = e.getY() - initialMousePosition.y;
                    transform.translate(dx, dy);
                    updateTransformedPosition();
                    repaint();

                    initialMousePosition = e.getPoint();
                }
            }
        });

        // Menambahkan transformedPositionLabel ke content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(transformedPositionLabel, BorderLayout.NORTH);
    }

    private void updateTransformedPosition() {
        int transformedX = 250 + (int) transform.getTranslateX();
        int transformedY = 250 + (int) transform.getTranslateY();
        transformedPositionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        transformedPositionLabel.setText("Transformed Position: (" + transformedX + ", " + transformedY + ")");
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(transform);

        // Draw a rectangle at the transformed position
        g2d.setColor(Color.BLACK);
        drawBresenhamRectangle(g2d, 250, 250, 250, 250);
    }

    private void drawBresenhamRectangle(Graphics2D g, int x, int y, int width, int height) {
        int x1 = x;
        int y1 = y;
        int x2 = x + width;
        int y2 = y;
        int x3 = x + width;
        int y3 = y + height;
        int x4 = x;
        int y4 = y + height;

        drawBresenhamLine(g, x1, y1, x2, y2);
        drawBresenhamLine(g, x2, y2, x3, y3);
        drawBresenhamLine(g, x3, y3, x4, y4);
        drawBresenhamLine(g, x4, y4, x1, y1);
    }

    private void drawBresenhamLine(Graphics2D g, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            g.drawLine(x1, y1, x1, y1);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int err2 = 2 * err;
            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Transformation app = new Transformation();
            app.setVisible(true);
            app.updateTransformedPosition();
        });
    }
}