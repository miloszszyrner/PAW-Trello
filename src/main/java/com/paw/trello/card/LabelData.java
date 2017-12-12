package com.paw.trello.card;

import javax.persistence.*;

@Entity
@Table(name = "LABEL")
public class LabelData {

    public enum Color {
        GREEN, YELLOW, ORANGE, RED, PURPLE, BLUE, PINK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "COLOR")
    private Color color;

    @Column(name = "CARD_ID", nullable = false, updatable = false)
    private Long cardId;

    public LabelData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
