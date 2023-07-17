package com.example.grafkom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cliping extends JFrame {
    private final Rectangle clipWindow;
    private final Rectangle shape;

    private final JLabel clipPositionLabel;

    public Cliping() {
        clipWindow = new Rectangle(63, 63, 150, 150);
        shape = new Rectangle(110, 110, 270, 270);

        setTitle("Clipping - Inputan Mouse");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(clipWindow.x, clipWindow.y, clipWindow.width, clipWindow.height);

                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(shape.x, shape.y, shape.width, shape.height);

                g2d.setClip(clipWindow);
                g2d.setColor(new Color(255, 180, 180));
                g2d.fillRect(shape.x, shape.y, shape.width, shape.height);

                // Menampilkan tampilan koordinat yang di-clip
                int clippedX = Math.max(shape.x, clipWindow.x);
                int clippedY = Math.max(shape.y, clipWindow.y);
                int clippedWidth = Math.min(shape.x + shape.width, clipWindow.x + clipWindow.width) - clippedX;
                int clippedHeight = Math.min(shape.y + shape.height, clipWindow.y + clipWindow.height) - clippedY;

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.drawString("Clipped Area: (" + clippedX + ", " + clippedY + ", " + clippedWidth + ", " + clippedHeight + ")", 10, 20);

                // Menggambar batas area yang di-clip dengan algoritma Bresenham
                g2d.setColor(Color.BLUE);
                drawBresenhamLine(g2d, clippedX, clippedY, clippedX + clippedWidth, clippedY);
                drawBresenhamLine(g2d, clippedX, clippedY, clippedX, clippedY + clippedHeight);
                drawBresenhamLine(g2d, clippedX + clippedWidth, clippedY, clippedX + clippedWidth, clippedY + clippedHeight);
                drawBresenhamLine(g2d, clippedX, clippedY + clippedHeight, clippedX + clippedWidth, clippedY + clippedHeight);
            }
        };

        clipPositionLabel = new JLabel();
        clipPositionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        clipPositionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        clipPositionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                clipWindow.setLocation(mouseX - clipWindow.width / 2, mouseY - clipWindow.height / 2);
                panel.repaint();
                updateClipPositionLabel();
            }
        });

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(clipPositionLabel, BorderLayout.NORTH);
        contentPane.add(panel, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    private void updateClipPositionLabel() {
        int clippedX = Math.max(shape.x, clipWindow.x);
        int clippedY = Math.max(shape.y, clipWindow.y);
        int clippedWidth = Math.min(shape.x + shape.width, clipWindow.x + clipWindow.width) - clippedX;
        int clippedHeight = Math.min(shape.y + shape.height, clipWindow.y + clipWindow.height) - clippedY;

        clipPositionLabel.setText("Clipped Area: (" + clippedX + ", " + clippedY + ", " + clippedWidth + ", " + clippedHeight + ")");
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
            Cliping cliping = new Cliping();
            cliping.setVisible(true);
        });
    }
}