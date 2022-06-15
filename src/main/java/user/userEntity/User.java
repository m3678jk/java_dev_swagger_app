package user.userEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class User {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
}
