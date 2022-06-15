package user;

import com.google.gson.Gson;
import lombok.Data;
import pet.petEntity.Response;
import user.userEntity.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserHttpService {

    public static final String URL = "https://petstore.swagger.io/";
    public static final Gson GSON = new Gson();
    public static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private int responseStatus;


    public int login(String userName, String password) throws IOException, InterruptedException {
        //why it returns xml instead of json - cannot understand it.
        // if 404 it is my problem or servers? everything looks good, but probably does not work
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "user/login?username=" + userName + "&password=" + password))
                .header("accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.body());
        return response.statusCode();
    }

    public int logout() throws IOException, InterruptedException {
        //why it returns xml instead of json - cannot understand it.
        // if 404 it is my problem or servers? everything looks good, but probably does not work
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("accept", "application/json")
                .uri(URI.create(URL + "user/logout"))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());

        return response.statusCode();
    }

    public User getUserByName(String userName) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "v2/user/" + userName))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        setResponseStatus(response.statusCode());
        if (getResponseStatus() != 200) {
            System.out.println("User not found");
            return null;
        }
        return GSON.fromJson(response.body(), User.class);
    }

    public Response updateUser(String name, User user) throws IOException, InterruptedException {

        if (getUserByName(name) == null) {
            System.out.println("User with " + name + "does not exist");
            return null;
        }
        final String requestBody = GSON.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/user/" + name))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), Response.class);

    }

    public Response createListOfUser(List<User> users) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(users);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/user/createWithList"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), Response.class);

    }


    public Response createNewUser(User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/user"))
                .setHeader("accept", "application/json")
                .setHeader("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), Response.class);
    }

    public Response delete(String name) throws IOException, InterruptedException {
        if (getUserByName(name) == null) {
            System.out.println("Order with id " + name + " not found");
        }
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "v2/user/" + name))
                .setHeader("accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        setResponseStatus(response.statusCode());
        return GSON.fromJson(response.body(), Response.class);

    }

}

class UserTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        UserHttpService userHttpService = new UserHttpService();
        List<User> users = new ArrayList<>();
        users.add(new User(
                1, "July", "Fog", "June",
                "rmail", "pass", "111", 0));
        System.out.println(userHttpService.createListOfUser(users));

//        System.out.println(userHttpService.login("lol", "lol"));
        //System.out.println(userHttpService.logout());

        //System.out.println(userHttpService.createNewUser(new User(3455, "lol", "lol", "lol", "lol", "lol", "lol", 1)));
    }

}

