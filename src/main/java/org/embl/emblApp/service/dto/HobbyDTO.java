package org.embl.emblApp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.embl.emblApp.domain.Hobby} entity.
 */
public class HobbyDTO implements Serializable {

    private Long id;

    private String title;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HobbyDTO hobbyDTO = (HobbyDTO) o;
        if (hobbyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hobbyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", personId=" + getPersonId() +
            "}";
    }
}
