/*
 * Copyright(C) 2014-2015 Java 8 Workshop participants. All rights reserved.
 * https://github.com/aosn/java8
 */
package com.tasktoys.java8ws.mikan.ch3.ex10;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;

/**
 * @author mikan
 */
public class UnaryComposeType extends Application {

    private static final String IMAGE_URL = "https://pbs.twimg.com/media/CEDfyQEVEAAkERc.png";

    public static void main(String[] args) {
        UnaryComposeType.launch();
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        UnaryOperator<Color> op = Color::brighter;
        Image image = new Image(IMAGE_URL);
        // ↓同じメソッドがあるが、型が違うのではじかれてしまう (ノミナル型)
        //
        // 不適合な型: 型変数Vのインスタンスが存在しないので、Function<V,Color>はUnaryOperator<Color>に適合しません
        //  V,T,Rが型変数の場合:
        //    メソッド <V>compose(Function<? super V,? extends T>)で宣言されているVはObjectを拡張します
        //    インタフェース Functionで宣言されているTはObjectを拡張します
        //    インタフェース Functionで宣言されているRはObjectを拡張します
        // Image brightenedImage = transform(image, op.compose(Color::grayscale));
        primaryStage.setScene(new Scene(new HBox(
                new ImageView(image)
        )));
        primaryStage.show();
    }
}
