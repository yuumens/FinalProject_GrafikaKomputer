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

public class Eclipse extends Application {
    private double startX, startY, endX, endY;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);

        canvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            endX = event.getX();
            endY = event.getY();
            redraw(canvas, gc);
        });

        canvas.setOnMouseReleased(event -> {
            endX = event.getX();
            endY = event.getY();
            redraw(canvas, gc);
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setTitle("Ellipse - Inputan Mouse");
        primaryStage.show();
    }

    private void redraw(Canvas canvas, GraphicsContext gc) {
        double left = Math.min(startX, endX);
        double top = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        double centerX = left + width / 2;
        double centerY = top + height / 2;
        double radiusX = width / 2;
        double radiusY = height / 2;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawMidpointEllipse(gc, centerX, centerY, radiusX, radiusY);

        // Menambahkan tampilan koordinat
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        gc.fillText("Start Point: (" + startX + ", " + startY + ")", 10, 20);
        gc.fillText("End Point: (" + endX + ", " + endY + ")", 10, 40);
        gc.fillText("Center: (" + centerX + ", " + centerY + ")", 10, 60);
        gc.fillText("Width: " + width, 10, 80);
        gc.fillText("Height: " + height, 10, 100);
        gc.fillText("Radius X: " + radiusX, 10, 120);
        gc.fillText("Radius Y: " + radiusY, 10, 140);
    }

    private void drawMidpointEllipse(GraphicsContext gc, double centerX, double centerY, double radiusX, double radiusY) {
        double a = radiusX;
        double b = radiusY;
        double aSquared = a * a;
        double bSquared = b * b;

        double twoASquared = 2 * aSquared;
        double twoBSquared = 2 * bSquared;

        double x = 0;
        double y = b;

        double p = bSquared - aSquared * b + 0.25 * aSquared;

        drawEllipsePoints(gc, centerX, centerY, x, y);

        while (twoBSquared * x < twoASquared * y) {
            x++;
            if (p < 0) {
                p += bSquared * (2 * x + 1);
            } else {
                y--;
                p += bSquared * (2 * x + 1) - aSquared * (2 * y);
            }
            drawEllipsePoints(gc, centerX, centerY, x, y);
        }

        double p2 = bSquared * (x + 0.5) * (x + 0.5) + aSquared * (y - 1) * (y - 1) - aSquared * bSquared;

        while (y > 0) {
            y--;
            if (p2 > 0) {
                p2 += aSquared * (1 - 2 * y);
            } else {
                x++;
                p2 += bSquared * (2 * x + 1) + aSquared * (1 - 2 * y);
            }
            drawEllipsePoints(gc, centerX, centerY, x, y);
        }
    }

    private void drawEllipsePoints(GraphicsContext gc, double centerX, double centerY, double x, double y) {
        gc.strokeLine(centerX + x, centerY + y, centerX + x, centerY + y);
        gc.strokeLine(centerX - x, centerY + y, centerX - x, centerY + y);
        gc.strokeLine(centerX + x, centerY - y, centerX + x, centerY - y);
        gc.strokeLine(centerX - x, centerY - y, centerX - x, centerY - y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}