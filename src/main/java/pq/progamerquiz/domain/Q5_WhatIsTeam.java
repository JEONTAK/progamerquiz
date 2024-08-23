package pq.progamerquiz.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Q5")
@Getter
@Setter
public class Q5_WhatIsTeam extends Quiz{
}
