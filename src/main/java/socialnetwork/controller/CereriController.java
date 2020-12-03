package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import socialnetwork.domain.*;
import socialnetwork.service.AllService;

import java.util.List;

public class CereriController {
    private AllService service;
    Stage stage;
    Utilizator user;
    ObservableList<DtoCererePrietenie> model = FXCollections.observableArrayList();
    ObservableList<DtoCererePrietenie> modelSent = FXCollections.observableArrayList();

    @FXML
    TableView<DtoCererePrietenie> tableView;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnFirstName;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnLastName;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnStatus;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnDate;

    @FXML
    TableView<DtoCererePrietenie> tableViewSent;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnFirstNameSent;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnLastNameSent;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnStatusSent;

    @FXML
    TableColumn<DtoCererePrietenie, String> tableColumnDateSent;


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
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("dateString"));

        tableColumnFirstNameSent.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnLastNameSent.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnStatusSent.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnDateSent.setCellValueFactory(new PropertyValueFactory<>("dateString"));

        tableView.setItems(model);
        tableViewSent.setSelectionModel(null);
        tableViewSent.setItems(modelSent);

    }

    private void initModel() {
        List<DtoCererePrietenie> cereriPrietenie = service.cererePrietenieService.getRecivedForUser(user.getId().intValue());
        List<DtoCererePrietenie> cererePrietenieSent = service.cererePrietenieService.getSentForUser(user.getId().intValue());

        model.setAll(cereriPrietenie);
        modelSent.setAll(cererePrietenieSent);
    }


    public void handleAcceptFriendRequest() {
        DtoCererePrietenie cererePrietenie = tableView.getSelectionModel().getSelectedItem();

        if (cererePrietenie != null) {
            if (cererePrietenie.getIdFrom() != user.getId().intValue()) {
                service.acceptPrietenie(cererePrietenie.getId());
                stage.close();

            } else {
                MessageAlert.showErrorMessage(null, "You sent this request!");
            }
        } else {
            MessageAlert.showErrorMessage(null, "Select a row!");
        }
    }

    public void handleDeclineFriendRequest() {
        DtoCererePrietenie cererePrietenie = tableView.getSelectionModel().getSelectedItem();

        if (cererePrietenie != null) {
            if (cererePrietenie.getIdFrom() != user.getId()) {
                service.declinePrietenie(cererePrietenie.getId());
                stage.close();
            } else {
                MessageAlert.showErrorMessage(null, "You sent this request!");
            }
        } else {
            MessageAlert.showErrorMessage(null, "Select a row!");
        }
    }
}
