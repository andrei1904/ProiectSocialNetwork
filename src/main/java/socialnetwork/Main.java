package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.CerereValidator;
import socialnetwork.domain.validators.MessageValidator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.CererePrietenieFile;
import socialnetwork.repository.file.MessageFile;
import socialnetwork.repository.file.PrietenieFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.*;
import socialnetwork.ui.ConsoleUserInterface;

public class Main {
    public static void interfata() {
        String fileName = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileNamePrt = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.friendships");
        String fileNameMessage = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.messages");
        String fileCerere = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.cereri");

        Repository<Long, Utilizator> userFileRepository = new UtilizatorFile(fileName
                , new UtilizatorValidator());
        Repository<Tuple<Long, Long>, Prietenie> prietenieFileRepository = new PrietenieFile(fileNamePrt,
                new PrietenieValidator());
        Repository<Integer, Message> repoFileMessage = new MessageFile(fileNameMessage,
                new MessageValidator());
        Repository<Integer, CererePrietenie> repoFileCererePrietenei = new CererePrietenieFile(fileCerere,
                new CerereValidator());

        UtilizatorService userService = new UtilizatorService(userFileRepository);
        PrietenieService prietenieService = new PrietenieService(prietenieFileRepository);
        PrieteniiService prieteniiService = new PrieteniiService(prietenieService, userService);
        MessageService messageService = new MessageService(prietenieService, userService, repoFileMessage);
        CererePrietenieService cererePrietenieService = new CererePrietenieService(prieteniiService, userService,
                repoFileCererePrietenei);

        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface(userService, prietenieService,
                prieteniiService, messageService, cererePrietenieService);

        consoleUserInterface.run();
    }

    public static void main(String[] args) {

//        interfata();

        MainFX.main(args);
    }
}


