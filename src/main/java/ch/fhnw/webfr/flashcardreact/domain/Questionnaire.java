package ch.fhnw.webfr.flashcardreact.domain;

import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document(collection="questionnaires")
public class Questionnaire {

    @Id
    private String id;

    @Size(min = 2, max = 30)
    private String title;

    @Size(min = 10, max = 50)
    private String description;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.title, this.description);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Questionnaire) {
            final Questionnaire other = (Questionnaire) obj;
            return Objects.equal(id, other.id)
                    && Objects.equal(title, other.title)
                    && Objects.equal(description, other.description);
        } else {
            return false;
        }
    }
}
