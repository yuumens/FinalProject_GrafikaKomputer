package com.example.grafkom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Line extends Application {
    private double startX, startY, endX, endY;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);

        canvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            endX = event.getX();
            endY = event.getY();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.strokeLine(startX, startY, endX, endY);
        });

        canvas.setOnMouseReleased(event -> {
            endX = event.getX();
            endY = event.getY();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.strokeLine(startX, startY, endX, endY);
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setTitle("Line Drawing With Mouse");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}