package com.example.grafkom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Windowing extends JFrame {
    private final Rectangle clipWindow;
    private final Rectangle viewport;

    public Windowing() {
        clipWindow = new Rectangle(63, 63, 500, 500);
        viewport = new Rectangle(174, 174, 300, 300);

        setTitle("Windowing - Inputan Mouse");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Menggambar clip window
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(clipWindow.x, clipWindow.y, clipWindow.width, clipWindow.height);

                // Menggambar viewport
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(viewport.x, viewport.y, viewport.width, viewport.height);

                // Menggambar objek di dalam viewport
                g2d.setColor(Color.BLUE);
                drawBresenhamRectangle(g2d, viewport.x, viewport.y, viewport.width, viewport.height);

                // Menambahkan tampilan koordinat
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.drawString("Clip Window: (" + clipWindow.x + ", " + clipWindow.y + ")", 10, 20);
                g2d.drawString("Viewport: (" + viewport.x + ", " + viewport.y + ")", 10, 40);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Update viewport berdasarkan posisi klik mouse
                if (clipWindow.contains(mouseX, mouseY)) {
                    int viewportX = mouseX - viewport.width / 2;
                    int viewportY = mouseY - viewport.height / 2;
                    viewport.setLocation(viewportX, viewportY);
                    panel.repaint();
                }
            }
        });

        getContentPane().add(panel);
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
            Windowing windowing = new Windowing();
            windowing.setVisible(true);
        });
    }
}