package org.embl.emblApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hobby.
 */
@Entity
@Table(name = "hobby")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    /**
     * A relationship
     */
    @OneToMany(mappedBy = "hobby")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Person> people = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("hobbies")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Hobby title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public Hobby people(Set<Person> people) {
        this.people = people;
        return this;
    }

    public Hobby addPerson(Person person) {
        this.people.add(person);
        person.setHobby(this);
        return this;
    }

    public Hobby removePerson(Person person) {
        this.people.remove(person);
        person.setHobby(null);
        return this;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public Person getPerson() {
        return person;
    }

    public Hobby person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hobby)) {
            return false;
        }
        return id != null && id.equals(((Hobby) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hobby{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
