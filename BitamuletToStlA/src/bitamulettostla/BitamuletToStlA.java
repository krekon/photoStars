/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitamulettostla;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author abaoubas
 */
public class BitamuletToStlA extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        Label lb_response = new Label();
        TextField tf_date = new TextField();
        tf_date.setText("2012-10-18 23:13:26");
        TextField tf_lat = new TextField();
        tf_lat.setText("37.9838100");
        TextField tf_lng = new TextField();
        tf_lng.setText("23.7275390");
        
        Button btn = new Button();
        btn.setText("Create!!!");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.from(f.parse(tf_date.getText())); //
                long runOnDate = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
                double lat = Double.parseDouble(tf_lat.getText());
                double lng = Double.parseDouble(tf_lat.getText());
                try {
                    String response = bitamulettostl.BitamuletToStl.Create3Models(lat, lng, runOnDate);
                    lb_response.setText(response);
                } catch (IOException ex) {
                    Logger.getLogger(BitamuletToStlA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        VBox root = new VBox();
        //root.setVgap(8);

        root.getChildren().add(new Label("Ημερομηνία και ώρα"));
        root.getChildren().add(tf_date);
        root.getChildren().add(new Label("Latitude"));
        root.getChildren().add(tf_lat);
        root.getChildren().add(new Label("Longtitude"));
        root.getChildren().add(tf_lng);
        root.getChildren().add(btn);
        root.getChildren().add(lb_response);
        

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
