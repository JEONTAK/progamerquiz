package com.pq.progamerquiz.teamInfo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Roster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer top;
    private Integer jgl;
    private Integer mid;
    private Integer adc;
    private Integer sup;

}
