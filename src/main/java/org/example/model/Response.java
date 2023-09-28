package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer responseId;
    private String text;
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}