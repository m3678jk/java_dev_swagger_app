package store.storeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Inventory {
     private int sold;
     private int string;
     private int soldOut;
     private int pending;
     private int available;
     private int notAvailable;
     private int status;

}


