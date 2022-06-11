import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Base64;

public class ConverterFileToBytes {

    public String extractBytes (String ImageName) throws IOException {
                byte[] bytes = Files.readAllBytes(Path.of(ImageName));
        String s = Base64.getEncoder().encodeToString(bytes);
        return s;

    }
}

class TestConverter{
    public static void main(String[] args) throws IOException {
        ConverterFileToBytes con = new ConverterFileToBytes();
        System.out.println("con.extractBytes(\"C:\\\\Java\\\\jm\\\\SwaggerApp\\\\src\\\\main\\\\java\\\\images2.jpg\") = " +
                con.extractBytes("C:\\Java\\jm\\SwaggerApp\\src\\main\\java\\images2.jpg"));


    }
}

