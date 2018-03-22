package akai.com.todolistapp.data;

import java.util.UUID;

/**
 * Created by xplolel on 22/03/2018.
 */

/**
 * User represents it's basic data in the app - like names.
 * It also stores it's ID for DB purposes and token for API authentication.
 */
public class User {
    private String firstName;
    private String lastName;
    private String loginName;
    private UUID id;

    public User(String firstName, String lastName, String loginName, UUID id, String token) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginName = loginName;
        this.id = id;
        this.token = token;
    }

    private String token;//for api verification

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoginName() {
        return loginName;
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
