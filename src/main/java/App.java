
import cli.CliService;
import com.google.gson.Gson;
import pet.PetHttpService;
import store.StoreHttpService;
import user.UserHttpService;

import java.net.http.HttpClient;


public class App {
    public static final String URL = "https://petstore.swagger.io/";
    public static final Gson GSON = new Gson(); // json
    public static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    public enum Method{
        GET, POST, DELETE, PUT
    }
    public static void main(String[] args) {
        new CliService(new PetHttpService(), new UserHttpService(), new StoreHttpService());

    }
}

