package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;
    private String topic;
    private Integer difficultyRank;
    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Response> responses;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

}

