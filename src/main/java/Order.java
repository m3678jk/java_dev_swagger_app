import lombok.Data;

import java.time.LocalDate;

@Data
public class Order {
    private int id;
    private int petId;
    private int quantity;
    private LocalDate shipDate; //string?
    private Status status;
    boolean complete;

    public enum Status {
        placed, approved, delivered
    }

}
