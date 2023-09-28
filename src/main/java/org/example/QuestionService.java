package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.model.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class QuestionService {

    private final SessionFactory sessionFactory;

    public QuestionService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveQuestionToDb(Question question) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(question);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteQuestionFromDb(Question question) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(question);
            transaction.commit();
            session.evict(question);
            question.setQuestionId(null);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public List<Question> searchQuestionByTopic(String topic) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Question> questions = null;

        try {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
            Root<Question> root = criteriaQuery.from(Question.class);

            criteriaQuery.select(root).where(builder.equal(root.get("topic"), topic));

            Query<Question> query = session.createQuery(criteriaQuery);
            questions = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return questions;
    }

}
