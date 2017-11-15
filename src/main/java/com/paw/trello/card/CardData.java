package com.paw.trello.card;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CARD")
public class CardData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    @NotNull(message = "Card :title cannot be empty")
    private String title;

    @Column(name = "ROLL_ID", nullable = false)
    private Long rollId;

    @Column(name ="LABEL")
    private String label;

    @Column(name = "DESCRIPTION")
    private String description;

    public CardData() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRollId() {
        return rollId;
    }

    public void setRollId(Long rollId) {
        this.rollId = rollId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
