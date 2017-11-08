package com.paw.trello.roll;

import javax.persistence.*;

@Entity
@Table(name = "ROLL")
public class RollData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, insertable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BOARD_ID")
    private Long boardId;

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
}
