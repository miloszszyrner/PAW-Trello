package com.paw.trello.User;

import com.paw.trello.board.BoardData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "USER")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "USERNAME", nullable = false, updatable = false)
    @NotNull(message = "User:username cannot be empty")
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @NotNull(message = "User:password cannot be empty")
    private String password;

    @Transient
    private String passwordConfirmation;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private List<BoardData> boards;

    public UserData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public List<BoardData> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardData> boards) {
        this.boards = boards;
    }
}