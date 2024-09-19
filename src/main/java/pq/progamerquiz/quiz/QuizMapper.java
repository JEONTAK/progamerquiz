package pq.progamerquiz.quiz;


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