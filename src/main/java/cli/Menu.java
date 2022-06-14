package cli;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MenuPlace {
    protected CliService cliService;


    public void init() {

    }
    public void goToPetMenu(){

    }

    public void unknownCommand(String cmd) {
        System.out.println("unknown command: " + cmd);
    }

}
