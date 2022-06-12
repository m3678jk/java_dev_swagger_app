package pet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pet.petEntity.Pet;
import pet.petEntity.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Data
public class PetHttpService {
    private int responseStatus;
    public static final String URL = "https://petstore.swagger.io/";
    public static final Gson GSON = new Gson();
    public static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    //  nok
    //  how to say in request that requestBody it is additionalMetadata, fileInStringFormat - picture to upload?
    //  but with Jsoup it probably works..
    public Response uploadImage(int petId, String additionalData, String pathToFile) throws IOException, InterruptedException {
        final String requestBody = "additionalMetadata="+ additionalData;
        HttpRequest.BodyPublishers bodyPublishers;
        System.out.println("requestBody = " + requestBody);
        String fileInStringFormat = converterFileToBytesToString(pathToFile);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/pet/" + petId + "/uploadImage"))
                .setHeader("accept", "application/json")
                .setHeader("Content-type", "multipart/form-data")
                .method("POST",HttpRequest.BodyPublishers.ofString(requestBody))
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of(pathToFile)))
                .build();
        System.out.println("request = " + request);
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + response.statusCode());
        return GSON.fromJson(response.body(), Response.class);


    }
    //ok
    public Response uploadImageJsoupVersion(int petId, String additionalData, String pathToFile) throws IOException, InterruptedException {
        // magic it seems to be ok
        Connection.Response execute = Jsoup.connect(URL + "v2/pet/" + petId + "/uploadImage")
                .header("Content-Type", "multipart/form-data")
                .header("Accept", "application/json")
                .followRedirects(true)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .data("additionalMetadata", additionalData)
                .data("file", "image.jpg", new FileInputStream(pathToFile))
                .execute();
        System.out.println(execute);
        int statusCode = execute.statusCode();
        // System.out.println(statusCode);
       // System.out.println(execute.body());
        return GSON.fromJson(execute.body(), Response.class);

    }


    //ok
    public HashMap<Pet, Integer> createNewPet(Pet pet) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(pet);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/pet"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        HashMap<Pet, Integer> result = new HashMap<>();
        result.put(GSON.fromJson(response.body(), Pet.class), response.statusCode());
        return result;

    }

    //ok
    public Pet getPetById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "v2/pet/" + id))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        setResponseStatus(response.statusCode());
        if (getResponseStatus() != 200) {
            System.out.println("Pet not found");
            return null;
        }

        return GSON.fromJson(response.body(), Pet.class);
    }

    public Response delete(int id) throws IOException, InterruptedException {
        if(getPetById(id)==null){
            System.out.println("Pet with id "+ id + " not found");
        }
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "v2/pet/" + id))
                .header("api_key", "special-key")
                .setHeader("accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        setResponseStatus(response.statusCode());
        return GSON.fromJson(response.body(), Response.class);

    }

    //ok
    public Pet updatePet(int id, Pet petAfter) throws IOException, InterruptedException {
        if (getPetById(id) == null) {
            System.out.println("Pet with " + id + "does not exist");
            return null;
        }
        final String requestBody = GSON.toJson(petAfter);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/pet"))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        setResponseStatus(response.statusCode());
        return GSON.fromJson(response.body(), Pet.class);

    }

    public Response updatePetNameAndStatus(int id, String name, Pet.Status status) throws IOException, InterruptedException {
        if (getPetById(id) == null) {
            System.out.println("Pet with " + id + "does not exist");
            return null;
        }
        // name=name&status=available
        final String requestBody = "name=" + name + "&status=" + status;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "v2/pet/" + id))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("accept", "application/json")
                .setHeader("Content-type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        setResponseStatus(response.statusCode());

        return GSON.fromJson(response.body(), Response.class);
    }


    //ok
    public List<Pet> getListOfPetByStatus( Pet.Status status) throws IOException, InterruptedException {
        List<Pet> pets = new ArrayList<Pet>();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "v2/pet/findByStatus?status=" + status))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        setResponseStatus(response.statusCode());

        Type typeToken = TypeToken
                .getParameterized(List.class, Pet.class)
                .getType();
        pets = GSON.fromJson(response.body(), typeToken);
        return pets;


    }


    public String converterFileToBytesToString(String ImageName) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of(ImageName));
        String s = Base64.getEncoder().encodeToString(bytes);
        return s;
    }
}

class TestPet {
    public static void main(String[] args) throws IOException, InterruptedException {
        String pathToPic = "C:\\Java\\jm\\SwaggerApp\\src\\main\\resources\\images2.jpg";
        PetHttpService petHTTP = new PetHttpService();
//        String s = petHTTP.converterFileToBytesToString(pathToPic);
//        System.out.println("s = " + s);
        //System.out.println("petHTTP.getPetById(777) = " + petHTTP.getPetById(777));
 //      System.out.println(petHTTP.uploadImageJsoupVersion(96998071, "info", pathToPic));
//        System.out.println(petHTTP.uploadImage(96998071, "info", pathToPic));
//
        //   System.out.println(petHTTP.getPetById(4789));
//        System.out.println(petHTTP.updatePet(768 , new Pet(2222222,
//                new Category(0, "animal"), "Baloo best", new ArrayList<String>(List.of("string")),
//                new ArrayList<Tag>(List.of(new Tag(0, "string"))),
//                Pet.Status.available)));

       // System.out.println(petHTTP.updatePetNameAndStatus(96998071, "newName", Pet.Status.available));
      //  System.out.println(petHTTP.getListOfPetByStatus(Pet.Status.available));
       // https://petstore.swagger.io/v2/pet/findByStatus?status=sold
        System.out.println(petHTTP.delete(111));
    }
}