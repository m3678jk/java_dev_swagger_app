package store.storeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Order {
    private long id;
    private long petId;
    private int quantity;
    private String shipDate; //string?
    private Status status;
    boolean complete;

    public enum Status {
        placed, approved, delivered
    }

}
