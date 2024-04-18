package mackerel.dontworry.user.domain;

import jakarta.persistence.*;
import lombok.*;
import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.schedule.domain.Schedule;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String provider;

    private String providerId;

    private boolean activated;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccountBook> accountList = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private User(String name, String email, String provider, String providerId){
        this.name = name;
        this.email = email;
        this.role = UserRole.USER;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static User of(String name, String email, String provider, String providerId){
        return User.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
    public User updateEmail(String username){
        this.email = username;
        return this;
    }
    public void updateProvider(String provider){
        this.provider = provider;
    }

    public String getRoleValue(){
        return this.getRole().getValue();
    }
}
