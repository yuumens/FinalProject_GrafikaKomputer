package com.example.grafkom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        primaryStage.setTitle("Mouse Ellipse Drawing");
        primaryStage.show();
    }

    private void redraw(Canvas canvas, GraphicsContext gc) {
        double left = Math.min(startX, endX);
        double top = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeOval(left, top, width, height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}