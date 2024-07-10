package com.pq.progamerquiz.progamerinfo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
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
    private String playerId;
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
    private int age;
    private String imagePath;

    public void setAgeAndImage(){
        this.age = birthToAge(this.birth);
        this.imagePath = setImagePath(this.id);
    }

    private int birthToAge(Date birth) {
        Calendar cur = Calendar.getInstance();
        int age = cur.get(Calendar.YEAR) - birth.getYear();
        if (birth.getMonth() * 100 + birth.getDay() > (cur.get(Calendar.MONTH) + 1) * 100 + cur.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    private String setImagePath(Long id) {
        String path = "../image/content/player/";
        path = path + id + ".jpg";
        return path;
    }
}

