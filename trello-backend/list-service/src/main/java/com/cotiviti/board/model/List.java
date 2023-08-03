package com.cotiviti.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private int listIndex;

    @Column(nullable = false)
    private boolean archived = false;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    @JsonIgnore
    private Board board;

    @JsonIgnoreProperties("list")
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    private java.util.List<Card> cards;

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
