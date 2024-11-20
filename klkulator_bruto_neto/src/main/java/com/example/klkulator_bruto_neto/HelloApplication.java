package com.example.klkulator_bruto_neto;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        Text mCT = new Text("Metoda obliczeń:");
        mCT.setFont(Font.font(14));

        ToggleGroup mC = new ToggleGroup();
        RadioButton bruttoRadio = new RadioButton("Od netto do brutto");
        bruttoRadio.setToggleGroup(mC);
        bruttoRadio.setSelected(true);
        RadioButton nettoRadio = new RadioButton("Od brutto do netto");
        nettoRadio.setToggleGroup(mC);
        RadioButton vatRadio = new RadioButton("Dopasuj do kwoty VAT");
        vatRadio.setToggleGroup(mC);

        Text dT = new Text("Dane:");
        dT.setFont(Font.font(14));
        Text wBT = new Text("Wartość bazowa:");
        TextField wB = new TextField();
        wB.setPromptText("Wprowadź wartość...");
        Text sVT = new Text("Stawka VAT:");
        ChoiceBox<String> sV = new ChoiceBox<>();
        sV.getItems().addAll("23%", "8%", "5%", "0%");
        sV.setValue("23%");

        Button bO = new Button("Oblicz");
        Button bZ = new Button("Zamknij");
        bZ.setOnAction(e -> stage.close());

        Text wT = new Text("Wyniki:");
        wT.setFont(Font.font(14));
        Label nT = new Label("Netto:");
        Label vT = new Label("VAT:");
        Label bT = new Label("Brutto:");

        Label nResult = new Label("0,00");
        Label vResult = new Label("0,00");
        Label bResult = new Label("0,00");

        bO.setOnAction(e -> {
            try {
                double baseValue = Double.parseDouble(wB.getText());
                double vatRate = Double.parseDouble(sV.getValue().replace("%", "")) / 100;
                double netto, vat, brutto;

                if (bruttoRadio.isSelected()) {
                    netto = baseValue;
                    vat = netto * vatRate;
                    brutto = netto + vat;
                } else if (nettoRadio.isSelected()) {
                    brutto = baseValue;
                    vat = brutto * vatRate / (1 + vatRate);
                    netto = brutto - vat;
                } else {
                    vat = baseValue;
                    netto = vat / vatRate;
                    brutto = netto + vat;
                }

                nResult.setText(String.format("%.2f", netto));
                vResult.setText(String.format("%.2f @ %.0f%%", vat, vatRate * 100));
                bResult.setText(String.format("%.2f", brutto));
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Niepoprawne dane wejściowe");
                alert.setContentText("Proszę wprowadzić prawidłową liczbę w polu 'Wartość bazowa'.");
                alert.showAndWait();
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(mCT, 0, 0);
        gridPane.add(bruttoRadio, 0, 1);
        gridPane.add(nettoRadio, 0, 2);
        gridPane.add(vatRadio, 0, 3);

        gridPane.add(dT, 0, 4);
        gridPane.add(wBT, 0, 5);
        gridPane.add(wB, 1, 5);
        gridPane.add(sVT, 0, 6);
        gridPane.add(sV, 1, 6);

        gridPane.add(bO, 0, 7);
        gridPane.add(bZ, 1, 7);

        gridPane.add(wT, 0, 8);
        gridPane.add(nT, 0, 9);
        gridPane.add(nResult, 1, 9);
        gridPane.add(vT, 0, 10);
        gridPane.add(vResult, 1, 10);
        gridPane.add(bT, 0, 11);
        gridPane.add(bResult, 1, 11);

        Scene scene = new Scene(gridPane, 400, 400);
        stage.setTitle("Kalkulator VAT netto-brutto");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
