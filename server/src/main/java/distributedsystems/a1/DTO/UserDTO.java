package distributedsystems.a1.DTO;

import org.springframework.hateoas.RepresentationModel;

public class UserDTO extends RepresentationModel<UserDTO>{

    public UserDTO() {
    }

    public UserDTO(Long userID, String email, String password, String name, String birthdate, String address, String role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.role = role;
    }

    public UserDTO(String name, String email, String password, String role) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    private Long userID;

    private String email;

    private String password;

    private String name;

    private String birthdate;

    private String address;

    private String role = "CLIENT";

    public Long getUserID() { return userID; }

    public void setUserID(Long userID) { this.userID = userID; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBirthdate() { return birthdate; }

    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }


}
