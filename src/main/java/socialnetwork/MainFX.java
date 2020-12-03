package socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import socialnetwork.config.ApplicationContext;
import socialnetwork.controller.LoginController;
import socialnetwork.domain.CererePrietenie;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.CerereValidator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.CererePrietenieFile;
import socialnetwork.repository.file.PrietenieFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.*;

import java.io.IOException;

public class MainFX extends Application {

    AllService allService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fileName = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        Repository<Long, Utilizator> userFileRepository = new UtilizatorFile(fileName
                , new UtilizatorValidator());

        String fileNamePrt = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.friendships");
        Repository<Tuple<Long, Long>, Prietenie> prietenieFileRepository = new PrietenieFile(fileNamePrt,
                new PrietenieValidator());

        String fileCerere = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.cereri");
        Repository<Integer, CererePrietenie> repoFileCererePrietenei = new CererePrietenieFile(fileCerere,
                new CerereValidator());

        UtilizatorService userService = new UtilizatorService(userFileRepository);
        PrietenieService prietenieService = new PrietenieService(prietenieFileRepository);
        PrieteniiService prieteniiService = new PrieteniiService(prietenieService, userService);

        CererePrietenieService cererePrietenieService = new CererePrietenieService(prieteniiService, userService, repoFileCererePrietenei);

        allService = new AllService(userService, prietenieService, cererePrietenieService, prieteniiService);

        initView(primaryStage);
        primaryStage.show();

    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();

        loginLoader.setLocation(getClass().getResource("../views/loginView.fxml"));
        AnchorPane layout = loginLoader.load();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new Scene(layout));

        LoginController loginController = loginLoader.getController();
        loginController.setService(allService);

    }
}
