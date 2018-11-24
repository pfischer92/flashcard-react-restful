package ch.fhnw.webfr.flashcardreact.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.fhnw.webfr.flashcardreact.domain.Questionnaire;

public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {
	List<Questionnaire> findByTitle(String title);
}
