package com.example.grafkom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Line extends Application {
    private List<Integer> coordinates;
    private boolean isDrawing;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);

        coordinates = new ArrayList<>();
        isDrawing = false;

        canvas.setOnMousePressed(event -> {
            if (!isDrawing) {
                startDrawing((int) event.getX(), (int) event.getY());
                isDrawing = true;
                redraw(canvas, gc);
                isDrawing = false;
            }
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setTitle("Membuat Garis - Inputan Mouse");
        primaryStage.show();
    }

    private void startDrawing(int x, int y) {
        coordinates.add(x);
        coordinates.add(y);
    }

    private void bresenhamLine(int x1, int y1, int x2, int y2, GraphicsContext gc) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            gc.strokeLine(x1, y1, x1, y1);

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
            System.out.println(x1);
            System.out.println(y1);
        }
    }

    private void redraw(Canvas canvas, GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < coordinates.size() - 2; i += 2) {
            int startX = coordinates.get(i);
            int startY = coordinates.get(i + 1);
            int endX = coordinates.get(i + 2);
            int endY = coordinates.get(i + 3);

            bresenhamLine(startX, startY, endX, endY, gc);
        }

        // Menampilkan koordinat setiap titik
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        for (int i = 0; i < coordinates.size() - 1; i += 2) {
            int x = coordinates.get(i);
            int y = coordinates.get(i + 1);
            gc.fillText(String.format("Titik - %d", i / 2 + 1), x + 5, y - 20);
            gc.fillText(String.format("(%d, %d)", x, y), x + 5, y - 5);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}