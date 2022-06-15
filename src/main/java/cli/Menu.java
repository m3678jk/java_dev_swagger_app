package cli;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Menu {
    protected CliService cliService;

//handler - first method which is called when MenuPlace is set vie CliService.setMenu
    public void init() {
    }
    public void goToPetMenu(){
    }

    public void unknownCommand(String cmd) {
        System.out.println("unknown command: " + cmd);
    }

    public void returnToMainMenu() {
        cliService.openMainMenu();
    }

    public void goToStoreMenu() {
    }

    public void goToUserMenu() {
    }
}
