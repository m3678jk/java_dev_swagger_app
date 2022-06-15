package cli;

import lombok.Getter;
import pet.PetHttpService;
import store.StoreHttpService;
import user.UserHttpService;

import java.util.Scanner;

public class CliService {
    private Menu menu;

    @Getter
    private Scanner scanner;

    @Getter
    private PetHttpService petHttpService;
    @Getter
    private UserHttpService userHttpService;
    @Getter
    private StoreHttpService storeHttpService;


    public CliService(PetHttpService petHttpService, UserHttpService userHttpService, StoreHttpService storeHttpService) {
        this.petHttpService = petHttpService;
        this.userHttpService = userHttpService;
        this.storeHttpService = storeHttpService;

        menu = new MainMenu(this);

        scanner = new Scanner(System.in);

        openMainMenu();
    }


    public void openMainMenu() {
        System.out.println("Press:\n" +
                "1 - open Pet menu\n" +
                "2 - open User menu\n" +
                "3 - open Store menu\n" +
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


    public void goToStoreMenu() {
        menu.goToStoreMenu();
        setMenu(new StoreMenu(menu.cliService));
    }

    public void goToUserMenu() {
        menu.goToUserMenu();
        setMenu(new UserMenu(menu.cliService));
    }

    public void goToPetMenu() {
        menu.goToPetMenu();
        setMenu(new PetMenu(menu.cliService));
    }

    public void unknownCommand(String cmd) {
        System.out.println("Unknown commandCLI: " + cmd);
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        menu.init();
    }
}
