package com.paw.trello.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paw.trello.roll.LaneData;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BOARD")
public class BoardData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "ID")
    List<LaneData> lanes;

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

    public List<LaneData> getLanes() {
        return lanes;
    }

    public void setLanes(List<LaneData> lanes) {
        this.lanes = lanes;
    }
}
