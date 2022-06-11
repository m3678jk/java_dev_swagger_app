package pet;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Pet {
    private int id;
    private Category category;
    private String name;
    private String photoUrls; // maybe to fix
    private Tag tags;
    private Status status;

    @Data
    @AllArgsConstructor
    public static class Category {
        private int id;
        private String name;

    }

    @Data
    @AllArgsConstructor
    public static class Tag {
        private int id;
        private String name;
    }

    public enum Status {
        available, pending, sold
    }
}
