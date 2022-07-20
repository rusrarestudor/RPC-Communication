package distributedsystems.a1.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String birthdate;

    @Column(nullable = false, length = 20)
    private String address;

    @Column(nullable = false, length = 20)
    private String role = "CLIENT";

    public User(Long userID, String email, String password, String name, String birthdate, String address, String role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.role = role;
    }



    public User() {
    }

    public Long getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() { return birthdate; }

    public String getAddress() { return address; }

    public void setUserID(Long id) {
        this.userID = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAddress(String address) { this.address = address; }

    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
}
