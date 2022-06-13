package cli;

import lombok.Getter;
import pet.PetHttpService;
import store.StoreHttpService;
import user.UserHttpService;

import java.util.Scanner;

public class CliService {
    private MenuPlace menuPlace;

    @Getter
    private Scanner scanner;

    @Getter
    private PetHttpService petHttpService;
    private UserHttpService userHttpService;
    private StoreHttpService storeHttpService;


    public CliService(PetHttpService petHttpService, UserHttpService userHttpService, StoreHttpService storeHttpService) {
        this.petHttpService = petHttpService;
        this.userHttpService = userHttpService;
        this.storeHttpService = storeHttpService;

        menuPlace = new MainMenu(this);

        scanner = new Scanner(System.in);

        openMainMenu();
    }


    private void openMainMenu() {
        System.out.println("Press:\n" +
                "1 - to open Pet menu\n" +
                "2 - to open User menu\n" +
                "3 - to open Store menu\n" +
                "4 - exit");
        while (true) {
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    goToPetMenu();
                    break;
                case "2":
                    goToUserMenu();
                    break;
                case "3":
                    goToStoreMenu();
                    break;
                case "4":
                    System.exit(0);
                    break;

                default:
                    unknownCommand(command);
            }
        }
    }

    private void goToStoreMenu() {
        //TODO
    }

    private void goToUserMenu() {
        //TODO
    }

    private void goToPetMenu() {
        menuPlace.goToPetMenu();

    }

    public void unknownCommand(String cmd) {
        System.out.println("Unknown command: " + cmd);
    }

    public void setMenu(MenuPlace menuPlace) {
        this.menuPlace = menuPlace;
        menuPlace.init();
    }
}
