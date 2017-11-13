package com.paw.trello.roll;

import com.paw.trello.card.CardData;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROLL")
public class RollData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, insertable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BOARD_ID")
    private Long boardId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ROLL_ID", referencedColumnName = "ID")
    List<CardData> cards;

    public RollData() {
    }

    public RollData(Long id, String name, Long boardId) {
        this.id = id;
        this.name = name;
        this.boardId = boardId;
    }

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

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public List<CardData> getCards() {
        return cards;
    }

    public void setCards(List<CardData> cards) {
        this.cards = cards;
    }
}
