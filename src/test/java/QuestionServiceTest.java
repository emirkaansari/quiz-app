import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.example.QuestionService;
import org.example.model.Question;
import org.example.model.Quiz;
import org.example.model.Response;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionServiceTest {

    private QuestionService questionService;
    private SessionFactory sessionFactory;
    private Quiz quiz;
    private Question question;
    private Question question2;
    private Response response1;
    private Response response2;

    @BeforeEach
    void setUp() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        questionService = new QuestionService(sessionFactory);

        quiz = new Quiz();

        question = new Question();
        question.setTopic("Sample Topic");
        question.setDifficultyRank(1);
        question.setContent("Sample Content");
        question.setQuiz(quiz);

        question2 = new Question();
        question2.setTopic("Sample Topic");
        question2.setDifficultyRank(2);
        question2.setContent("Sample Content");
        question2.setQuiz(quiz);

        quiz.setQuestions(Arrays.asList(question, question2));

        response1 = new Response();
        response1.setText("Response 1");
        response1.setCorrect(true);
        response1.setQuestion(question);
        response2 = new Response();
        response2.setText("Response 2");
        response2.setCorrect(false);
        response2.setQuestion(question);
        question.setResponses(Arrays.asList(response1, response2));
    }


    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    void testSaveQuestionToDb() {
        questionService.saveQuestionToDb(question);

        assertNotNull(question.getQuestionId());
    }

    @Test
    void testDeleteQuestionFromDb() {
        questionService.saveQuestionToDb(question);

        questionService.deleteQuestionFromDb(question);

        assertNull(question.getQuestionId());
    }


    @Test
    void testSearchQuestionByTopic() {

        questionService.saveQuestionToDb(question);
        questionService.saveQuestionToDb(question2);

        List<Question> result = questionService.searchQuestionByTopic("Sample Topic");

        assertEquals(2, result.size());
    }
}

