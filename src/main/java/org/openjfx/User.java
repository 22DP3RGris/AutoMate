package org.openjfx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;
    private String email;

    public String toString(){
        return "Username: " + username + " Password: " + password + " Email: " + email;
    }
}
