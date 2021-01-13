package pl.edu.pjwstk.jaz.authorizationjpa;

import javax.persistence.*;
import java.io.Serializable;


@Entity
    @Table(name="users")
    public class UserEntity {

        @Column
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Long id;


    @Column
        private String role;



    @Column
        private String username;


    @Column
        private String password;


    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
            return id;
        }

    public String getUsername() {
        return username;
    }

        public String getRole() {
            return role;
        }

        public String getPassword() {
            return password;
        }

    }

