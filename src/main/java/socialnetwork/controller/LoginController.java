package socialnetwork.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.AllService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;


public class LoginController {
    private AllService service;


    @FXML
    TextField textFieldUserId;

    public void setService(AllService service) {
        this.service = service;
    }


    public void closeStage() {
        Platform.exit();
    }

    public void handleClickLogin() {
        int idUser = 0;

        try {
            idUser = Integer.parseInt(textFieldUserId.getText());
        } catch (NumberFormatException e) {
            textFieldUserId.setText("");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Eroare la introducerea datelor!");
            alert.setContentText("Introduceti doar numere intregi!");

            alert.showAndWait();
        }

        if (service.utilizatorService.existaUtilizator(idUser)) {
            showUserInterfaceDialog(service.utilizatorService.getOne(idUser));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nu exista acest utilizator!");
            alert.setContentText("Verificati datele introduse!");

            alert.showAndWait();
        }
    }

    public void showUserInterfaceDialog(Utilizator user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../views/userView.fxml"));

            AnchorPane root = loader.load();

            Stage userStage = new Stage();
            userStage.setTitle("User profile");
            userStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            userStage.setScene(scene);

            UserController cererePrietenieController = loader.getController();
            cererePrietenieController.setService(service, userStage, user);

            userStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
