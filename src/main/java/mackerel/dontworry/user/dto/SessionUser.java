package mackerel.dontworry.user.dto;

import lombok.Getter;
import mackerel.dontworry.user.domain.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
