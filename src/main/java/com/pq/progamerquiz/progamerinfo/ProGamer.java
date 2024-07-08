package com.pq.progamerquiz.progamerinfo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProGamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String player_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String league;
    @Column(nullable = false)
    private String team;
    @Column(nullable = false)
    private Date birth;
    @Column(nullable = false)
    private int position;
    @Column(columnDefinition = "0")
    private int league_win;
    @Column(columnDefinition = "0")
    private int intl_win;
}

