package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.UtilizatorService;
import socialnetwork.ui.ConsoleUserInterface;

public class Main {
    public static void main(String[] args) {
        String fileName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");

        Repository<Long, Utilizator> userFileRepository = new UtilizatorFile(fileName
                , new UtilizatorValidator());

//        userFileRepository.findAll().forEach(System.out::println);

        UtilizatorService userService = new UtilizatorService(userFileRepository);

        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface(userService);

        consoleUserInterface.run();
    }
}


