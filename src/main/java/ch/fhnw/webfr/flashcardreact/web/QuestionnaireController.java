package ch.fhnw.webfr.flashcardreact.web;

import ch.fhnw.webfr.flashcardreact.domain.Questionnaire;
import ch.fhnw.webfr.flashcardreact.persistence.QuestionnaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questionnaires")
public class QuestionnaireController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @GetMapping
    public ResponseEntity<List<Questionnaire>> findAll() {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<Questionnaire> questionnaires = questionnaireRepository.findAll(sort);

        log.debug("Found " + questionnaires.size() + " questionnaires");

        return new ResponseEntity<List<Questionnaire>>(questionnaires, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Questionnaire> findByID(@PathVariable String id) {

        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);

        if (questionnaire.isPresent()){
            log.debug("Found " + questionnaire.get().getId());
            return new ResponseEntity<Questionnaire>(questionnaire.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Questionnaire> create(@Valid @RequestBody Questionnaire q, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        Questionnaire questionnaire = questionnaireRepository.save(q);
        log.debug("Created questionnaire with id=" + questionnaire.getId());

        return new ResponseEntity<Questionnaire>(questionnaire, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Questionnaire> update(@PathVariable String id, @Valid @RequestBody Questionnaire q, BindingResult result){
        if (result.hasErrors()) {
            return new ResponseEntity<Questionnaire>(HttpStatus.BAD_REQUEST);
        }

        Optional<Questionnaire> toBeUpdatedOpt = questionnaireRepository.findById(id);
        if(toBeUpdatedOpt.isPresent()){
            Questionnaire toBeUpdated = toBeUpdatedOpt.get();
            toBeUpdated.setDescription(q.getDescription());
            toBeUpdated.setTitle(q.getTitle());
            questionnaireRepository.save(toBeUpdated);
            return new ResponseEntity<>(toBeUpdated, HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Questionnaire> delete(@PathVariable String id){
        Optional<Questionnaire> toBeUpdatedDeleted = questionnaireRepository.findById(id);
        if(toBeUpdatedDeleted.isPresent()){
            questionnaireRepository.delete(toBeUpdatedDeleted.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
