package pet;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;

public class PetHTTP {

    public static final String URL = "https://petstore.swagger.io/";
    public static final Gson GSON = new Gson(); // json
    public static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    public Pet createNewPet(Pet pet) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(pet);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/pet"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type","application/json")
                .build();

        System.out.println("request = " + request);

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() after using sendPost = " + response.statusCode());
        return GSON.fromJson(response.body(), Pet.class);


    }



        public String converterFileToBytesToString (String ImageName) throws IOException {
            byte[] bytes = Files.readAllBytes(Path.of(ImageName));
            String s = Base64.getEncoder().encodeToString(bytes);
            return s;

        }

}

class TestPet{
    public static void main(String[] args) throws IOException, InterruptedException {
        String pathToPic = "C:\\Java\\jm\\SwaggerApp\\src\\main\\java\\images2.jpg";
        //Why there is response status 500?
        PetHTTP petHTTP = new PetHTTP();
        String s = petHTTP.converterFileToBytesToString(pathToPic);
        Pet pet = petHTTP.createNewPet(new Pet(1111113322,
                new Pet.Category(15, "cat"),"cat", s,
                new Pet.Tag(14, "tag"),
                Pet.Status.available));
        System.out.println("pet = " + pet);
    }
}