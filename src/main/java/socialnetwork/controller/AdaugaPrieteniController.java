package socialnetwork.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import socialnetwork.domain.DtoPrieten;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.RepoException;
import socialnetwork.service.AllService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdaugaPrieteniController {
    private AllService service;
    Stage stage;
    Utilizator user;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    @FXML
    TableView<Utilizator> tableView;

    @FXML
    TableColumn<Utilizator, String> tableColumnFirstName;

    @FXML
    TableColumn<Utilizator, String> tableColumnLastName;

    public void setService(AllService service, Stage stage, Utilizator user) {
        this.service = service;
        this.stage = stage;
        this.user = user;
        init();
        initModel();
    }

    public void init() {
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<Utilizator> utilizatori = service.utilizatorService.getAll();

        List<Utilizator> utilizatorList = StreamSupport.stream(utilizatori.spliterator(), false)
                .collect(Collectors.toList());
        List<DtoPrieten> prieteni = service.prieteniiService.prieteniUtilizator(user.getId().intValue());

        utilizatorList.removeIf(x -> (x.getId().equals(user.getId())));

        for (DtoPrieten prieten : prieteni) {
            utilizatorList.removeIf(x -> (x.getId().intValue() == prieten.getId()));
        }

        model.setAll(utilizatorList);
    }

    public void handleAddFriend(ActionEvent actionEvent) {
        Utilizator utilizator = tableView.getSelectionModel().getSelectedItem();

        if (utilizator != null) {
            try {
                service.addPrietenie(user.getId().intValue(), utilizator.getId().intValue());
                stage.close();
            } catch (RepoException e) {
                MessageAlert.showErrorMessage(null, "There is already one request!");
            }
        } else {
            MessageAlert.showErrorMessage(null, "No user was selected!");
        }
    }
}
