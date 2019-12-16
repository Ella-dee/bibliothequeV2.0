package com.musers.microserviceusers.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "users")
public class User {
    @Id
    @Column (name="id")
    @GeneratedValue(strategy = GenerationType. IDENTITY )
    private Integer id;

    @NotEmpty
    @Column (name="username")
    private String userName;

    @NotEmpty
    @Column (name="first_name")
    private String firstName;

    @NotEmpty
    @Column (name="last_name")
    private String lastName;

    @NotEmpty
    @Column(name="email", unique=true)
    @Email(message="Merci de saisir un email valide")
    private String email;

    @NotEmpty
    @Column(name="password")
    private String password;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_date")
    private String tokenDate;

    @JsonSerialize(using = RoleSerializer.class)
    @ManyToOne //plusieurs user pour un seul role
    @JoinColumn(name = "id_role")
    private Role userRole;

    public User() {
    }

    public String getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(String tokenDate) {
        this.tokenDate = tokenDate;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
