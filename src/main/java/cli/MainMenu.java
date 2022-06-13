package cli;

public class MainMenu extends MenuPlace {
    public MainMenu(CliService cliService) {
        super(cliService);
    }

    @Override
    public void unknownCommand(String cmd) {
        System.out.println("Unknown command: " + cmd);
    }

    public void goToPetMenu() {
        cliService.setMenu(new PetMenuPlace(cliService));
    }
}
