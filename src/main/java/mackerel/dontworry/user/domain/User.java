package mackerel.dontworry.user.domain;

import jakarta.persistence.*;
import lombok.*;
import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.budgetguide.domain.FixedEX;
import mackerel.dontworry.challenge.domain.WithFriends;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToOne(mappedBy = "user")
    private FixedEX fixedEx;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccountBook> accountList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "FRIENDSHIP",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    private Set<WithFriends> withFriends = new HashSet<>();


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
