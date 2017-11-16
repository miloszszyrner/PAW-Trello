package com.paw.trello.board;

import com.paw.trello.roll.LaneData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "BOARD")
public class BoardData {
	
	public static enum Status {
		CRREATED, ARCHIVED, DELETED
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    @NotNull(message = "Board :name cannot be empty")
    private String name;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 8)
    private Status status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "ID")
    List<LaneData> lanes;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    public List<LaneData> getLanes() {
        return lanes;
    }

    public void setLanes(List<LaneData> lanes) {
        this.lanes = lanes;
    }
}
