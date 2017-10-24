package com.paw.trello.board;

public class BoardData {

    private Long id;

    private String name;

    public BoardData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
