package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.PrietenieFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.PrieteniiService;
import socialnetwork.service.UtilizatorService;
import socialnetwork.ui.ConsoleUserInterface;

public class Main {
    public static void main(String[] args) {
        String fileName = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileNamePrt = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.friendships");

        Repository<Long, Utilizator> userFileRepository = new UtilizatorFile(fileName
                , new UtilizatorValidator());
        Repository<Tuple<Long, Long>, Prietenie> prietenieFileRepository = new PrietenieFile(fileNamePrt,
                new PrietenieValidator());

        UtilizatorService userService = new UtilizatorService(userFileRepository);
        PrietenieService prietenieService = new PrietenieService(prietenieFileRepository);
        PrieteniiService prieteniiService = new PrieteniiService(prietenieFileRepository, userFileRepository);

        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface(userService, prietenieService,
                prieteniiService);

        consoleUserInterface.run();
    }
}


