package org.embl.emblApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Employee entity.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "favorite_color")
    private String favoriteColor;

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Hobby> hobbies = new HashSet<>();

    /**
     * Another side of the same relationship
     */
    @ManyToOne
    @JsonIgnoreProperties("people")
    private Hobby hobby;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public Person age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public Person favoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
        return this;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public Person hobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
        return this;
    }

    public Person addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
        hobby.setPerson(this);
        return this;
    }

    public Person removeHobby(Hobby hobby) {
        this.hobbies.remove(hobby);
        hobby.setPerson(null);
        return this;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public Person hobby(Hobby hobby) {
        this.hobby = hobby;
        return this;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", age=" + getAge() +
            ", favoriteColor='" + getFavoriteColor() + "'" +
            "}";
    }
}
