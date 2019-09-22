/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esi.atl.g39801.fx.bmr;

import javafx.application.Application;
import static javafx.collections.FXCollections.observableArrayList;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

/**
 *
 * @author kamal
 */
public class BMR extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("BMR application");
        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);

        HBox root1 = new HBox(20);
        root1.setAlignment(Pos.TOP_LEFT);

        //label titre des gridpane
        Label donnee = new Label("Données");
        donnee.setUnderline(true);
        Label result = new Label("Résultats");
        result.setUnderline(true);

        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.TOP_LEFT);
        grid1.setHgap(10);
        grid1.setVgap(15);

        GridPane grid2 = new GridPane();
        grid1.setAlignment(Pos.TOP_RIGHT);
        grid2.setHgap(10);
        grid2.setVgap(15);

        grid1.add(donnee, 0, 0);
        grid2.add(result, 0, 0);
        Label labelTaille = new Label("Taille (cm)");
        TextField textTaille = new TextField();
        grid1.add(labelTaille, 0, 1);
        grid1.add(textTaille, 1, 1);

        Label labelPoids = new Label("Poinds (Kg)");
        TextField textPoids = new TextField();
        grid1.add(labelPoids, 0, 2);
        grid1.add(textPoids, 1, 2);

        Label labelAge = new Label("Age (années)");
        TextField textAge = new TextField();
        grid1.add(labelAge, 0, 3);
        grid1.add(textAge, 1, 3);

        Label labelSex = new Label("Sexe");

        //radio de sex
        ToggleGroup sexe = new ToggleGroup();
        RadioButton sexHomme = new RadioButton("Homme");
        sexe.getToggles().add(sexHomme);

        RadioButton sexFemme = new RadioButton("Femme");
        sexe.getToggles().add(sexFemme);

        grid1.add(labelSex, 0, 4);
        grid1.add(sexHomme, 1, 4);
        grid1.add(sexFemme, 1, 5);

        Label labelSv = new Label("Style de vie");
        ChoiceBox boite = new ChoiceBox(observableArrayList("Sédentaire",
                "Peu actif", "Actif", "Fort actif", "Extrêmement actif"));

        grid1.add(labelSv, 0, 6);
        grid1.add(boite, 1, 6);

        Label labelBmr = new Label("BMR");
        TextField textBmr = new TextField();
        grid2.add(labelBmr, 0, 1);
        grid2.add(textBmr, 1, 1);

        Label labelCal = new Label("Calories");
        TextField textCal = new TextField();
        grid2.add(labelCal, 0, 2);
        grid2.add(textCal, 1, 2);

        //bouton calcul bmr
        Button btnBmr = new Button("calcule du BMR");
        btnBmr.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                double poid = Double.parseDouble(textPoids.getText());
                int taille = Integer.parseInt(textTaille.getText());
                int age = Integer.parseInt(textAge.getText());
                double bmr = 0, calories = 0;
                if (isvalide(textTaille, textPoids, textAge, sexe, boite)) {
                    textBmr.setText("erreur");
                    textCal.setText("erreur");
                    System.out.println("beug");
                } else {
                
                String sex = sexFemme.isSelected() ? "Femme" : 
                        sexHomme.isSelected() ? "Homme" : "";
                
                switch (sex) {

                    case "Femme":

                        bmr = calculBmr(sexe, poid, taille, age);
                        calories = calculCalories(bmr, boite);
                        break;

                    case "Homme":
                        bmr = calculBmr(sexe, poid, taille, age);
                        calories = calculCalories(bmr, boite);
                        break;
                }
                textBmr.setText(Double.toString(bmr));
                textCal.setText(Double.toString(calories));
            }
            }
        });

        //affichage
        root1.getChildren().addAll(grid1, grid2);
        root.getChildren().add(root1);
        root.getChildren().add(btnBmr);
        root1.setAlignment(Pos.TOP_LEFT);
        Scene scene = new Scene(root, 520, 380);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double calculCalories(double bmr, ChoiceBox boite) {
        double calories = 0;
        double result = 0;

        switch (boite.getValue().toString()) {
            case "Sédentaire":
                calories = bmr * 1.2;
                break;
            case "Peu actif":
                calories = bmr * 1.375;
                break;
            case "Actif":
                calories = bmr * 1.55;
                break;
            case "Fort actif":
                calories = bmr * 1.725;
                break;
            case "Extrêmement actif":
                calories = bmr * 1.9;
                break;
        }
        result = bmr * calories;
        return result;
    }

    private double calculBmr(ToggleGroup sex, double poids, double taille, 
            int age) {
        double resultat = 0;
        if ("Femme".equals(sex.getSelectedToggle())) {

            resultat = 9.6 * poids + 1.8 * taille - 4.7 * age + 655;

        } else {

            resultat = 13.7 * poids + 5 * taille - 6.8 * age + 66;
        }

        return resultat;
    }

    private boolean isvalide(TextField taille, TextField poids, TextField age,
            ToggleGroup sex, ChoiceBox boite) {

        return taille.getText().isEmpty() && poids.getText().isEmpty()
                && age.getText().isEmpty() && 
                sex.getSelectedToggle().isSelected()
                && boite.getSelectionModel().isEmpty();

    }
    
    

}
