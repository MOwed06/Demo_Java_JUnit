package demo.mowed;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import demo.mowed.core.ApplicationRunner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] argsG) {
        try {
            LOGGER.info("Starting");
            ApplicationRunner appRunner = new ApplicationRunner();
            boolean continueRunning = false;
            do {
                System.out.printf("%nEnter message data file (or 'Q' to quit): ");
                String userEntry = SCANNER.nextLine();
                var response = appRunner.processRequest(userEntry);
                continueRunning = response.remainActive();
                System.out.println(response.statusMessage());
            } while(continueRunning);
        } catch (Exception ex) {
            System.out.printf("%nSomething bad happened");
            LOGGER.error(ex);
        } finally {
            System.out.printf("%nApplication ended");
            LOGGER.info("Ending");
        }
    }
}
