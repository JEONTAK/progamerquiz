package pq.progamerquiz.mapper;


import pq.progamerquiz.domain.Quiz;
import pq.progamerquiz.dto.QuizDto;

public class QuizMapper {

    public static Quiz toEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDto.getId());
        quiz.setUrl(quizDto.getUrl());
        quiz.setImageUrl(quizDto.getImageUrl());
        quiz.setTitle(quizDto.getTitle());
        quiz.setAltText(quizDto.getAltText());
        quiz.setDescription(quizDto.getDescription());
        return quiz;
    }
}