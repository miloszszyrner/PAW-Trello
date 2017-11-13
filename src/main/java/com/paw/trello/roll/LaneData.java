package com.paw.trello.roll;

import com.paw.trello.card.CardData;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "LANE")
public class LaneData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, insertable = false)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "BOARD_ID")
    private Long boardId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ROLL_ID", referencedColumnName = "ID")
    List<CardData> cards;

    public LaneData() {
    }

    public LaneData(Long id, String title, Long boardId, String label) {
        this.id = id;
        this.title = title;
        this.boardId = boardId;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
