package org.example.lr5_2;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    @FXML
    private TextField nameField; // Поле для имени клиента

    @FXML
    private ComboBox<String> productComboBox; // Выпадающий список для выбора товара

    @FXML
    private CheckBox deliveryCheckBox; // Флажок для доставки

    @FXML
    private Button orderButton; // Кнопка оформления заказа

    @FXML
    private TextArea orderSummaryArea; // Текстовая область для отображения сводки заказа

    @FXML
    public void initialize() {
        loadProducts(); // Подгружаем товары при инициализации
        orderButton.setOnAction(event -> handleOrder());
    }

    private void loadProducts() {
        List<String> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        productComboBox.getItems().clear();
        productComboBox.getItems().addAll(products);
    }

    private void handleOrder() {
        String customerName = nameField.getText().trim();
        String selectedProduct = productComboBox.getValue();
        boolean isDelivery = deliveryCheckBox.isSelected();

        if (customerName.isEmpty()) {
            showError("Ошибка", "Имя клиента не может быть пустым.");
            return;
        }

        if (selectedProduct == null) {
            showError("Ошибка", "Пожалуйста, выберите товар.");
            return;
        }

        StringBuilder orderSummary = new StringBuilder();
        orderSummary.append("Имя клиента: ").append(customerName).append("\n");
        orderSummary.append("Выбранный товар: ").append(selectedProduct).append("\n");
        orderSummary.append("Доставка: ").append(isDelivery ? "Да" : "Нет").append("\n");

        orderSummaryArea.setText(orderSummary.toString());
        saveOrder(customerName, selectedProduct, isDelivery);
    }

    private void saveOrder(String name, String product, boolean delivery) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
            writer.write("Имя клиента: " + name + "\n");
            writer.write("Выбранный товар: " + product + "\n");
            writer.write("Доставка: " + (delivery ? "Да" : "Нет") + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
