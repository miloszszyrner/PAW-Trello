package com.paw.trello.board;

import com.paw.trello.roll.RollData;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BOARD")
public class BoardData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "ID")
    List<RollData> rolls;

    public BoardData() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<com.paw.trello.roll.RollData> getRolls() {
        return rolls;
    }

    public void setRolls(List<com.paw.trello.roll.RollData> rolls) {
        this.rolls = rolls;
    }
}
