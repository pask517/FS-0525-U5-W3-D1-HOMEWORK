package andreapascarella.u5d11.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "employees")
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID employeeId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    private String avatarURL;

    private String password;

    public Employee(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatarURL = "https://ui-avatars.com/api?name=" + this.getName() + "+" + this.getSurname();
        this.password = password;
    }
}
