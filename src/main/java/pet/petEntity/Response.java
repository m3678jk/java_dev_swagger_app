package pet.petEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private long code;
    private String type;
    private String message;
}
