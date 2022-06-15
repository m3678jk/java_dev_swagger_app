
import cli.CliService;
import pet.PetHttpService;
import store.StoreHttpService;
import user.UserHttpService;

public class App {
    public static void main(String[] args) {
        new CliService(new PetHttpService(), new UserHttpService(), new StoreHttpService());

    }
}

