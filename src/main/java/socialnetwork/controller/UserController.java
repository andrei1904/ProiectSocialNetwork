package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.domain.DtoPrieten;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.AllService;
import socialnetwork.utils.events.PrietenChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class UserController implements Observer<PrietenChangeEvent> {
    private AllService service;
    Stage stage;
    Utilizator user;
    ObservableList<DtoPrieten> model = FXCollections.observableArrayList();

    @FXML
    Label labelUserName;

    @FXML
    TableView<DtoPrieten> tableView;

    @FXML
    TableColumn<DtoPrieten, String> tableColumnFirstName;

    @FXML
    TableColumn<DtoPrieten, String> tableColumnLastName;

    @FXML
    TableColumn<DtoPrieten, String> tableColumnDate;

    @FXML
    TextField textFieldName;

    public void setService(AllService service, Stage stage, Utilizator user) {
        this.service = service;
        this.stage = stage;
        this.user = user;
        service.addObserver(this);
        init();
        initModel();
    }

    public void init() {
        labelUserName.setText("Welcome back, " + user.getLastName() + " " + user.getFirstName() + "!");

        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        tableView.setItems(model);
    }

    private void initModel() {
        List<DtoPrieten> utilizatori = service.prieteniiService.prieteniUtilizator(user.getId().intValue());
        model.setAll(utilizatori);
    }


    public void handleAddMoreFriends() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../views/addFriendsView.fxml"));

            AnchorPane root = loader.load();

            Stage friendsStage = new Stage();
            friendsStage.setTitle("Users");
            friendsStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            friendsStage.setScene(scene);

            AdaugaPrieteniController adaugaPrieteniController = loader.getController();
            adaugaPrieteniController.setService(service, friendsStage, user);

            friendsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteFriend() {
        DtoPrieten utilizator = tableView.getSelectionModel().getSelectedItem();


        if (utilizator != null) {
            service.deletePrietenie(user.getId().intValue(), utilizator.getId());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No user was selected!");
            alert.setContentText("Select one user!");

            alert.showAndWait();
        }
    }

    @Override
    public void update() {
        initModel();
    }

    public void handleFriendRequests() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../views/friendRequestView.fxml"));

            AnchorPane root = loader.load();

            Stage friendsStage = new Stage();
            friendsStage.setTitle("Friend Requests");
            friendsStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            friendsStage.setScene(scene);

            CereriController adaugaPrieteniController = loader.getController();
            adaugaPrieteniController.setService(service, friendsStage, user);

            friendsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleFilter() {
        String name = textFieldName.getText();

        Predicate<DtoPrieten> byFirstNamePredicate = m -> m.getFirstName().contains(name);

        model.setAll(service.prieteniiService.prieteniUtilizator(user.getId().intValue())
                     .stream()
                     .filter(byFirstNamePredicate)
                     .collect(Collectors.toList()));
    }

    public void handleFilterName() {
        handleFilter();
    }
}
