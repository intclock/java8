/*
 * Copyright(C) 2014-2015 Java 8 Workshop participants. All rights reserved.
 * https://github.com/Java8Workshop/Exercises
 */
package com.tasktoys.java8ws.mikan.ch3.ex12;

import java.util.function.UnaryOperator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author mikan
 */
public class TransformerApp extends Application {

    private static final String IMAGE_URL = "https://pbs.twimg.com/media/CEDfyQEVEAAkERc.png";

    public static void main(String[] args) {
        TransformerApp.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image(IMAGE_URL);
        Image brightenedImage = transform(image, Color::brighter);
        Image latentImage = LatentImage.from(image)
                .transform(Color::brighter).
                transform(Color::grayscale).
                toImage();
        Image latentImage2 = LatentImage2.from(image)
                .transform((x, y, c) -> c.brighter())
                .transform((x, y, c) -> c.grayscale())
                .toImage();
        primaryStage.setScene(new Scene(new HBox(
                new ImageView(image),
                new ImageView(brightenedImage),
                new ImageView(latentImage),
                new ImageView(latentImage2)
        )));
        primaryStage.show();
    }

    public static Image transform(Image in, UnaryOperator<Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, f.apply(in.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }

    public static ColorTransformer createColorTransformer(UnaryOperator<Color> f) {
        return (x, y, c) -> f.apply(c); // unused: x, y
    }

    @FunctionalInterface
    public interface ColorTransformer {

        Color apply(int x, int y, Color colorAtXY);
    }
}
