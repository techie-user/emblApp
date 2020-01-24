package org.embl.emblApp.service.mapper;

import org.embl.emblApp.domain.*;
import org.embl.emblApp.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {HobbyMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    @Mapping(source = "hobby.id", target = "hobbyId")
    PersonDTO toDto(Person person);

    @Mapping(target = "hobbies", ignore = true)
    @Mapping(target = "removeHobby", ignore = true)
    @Mapping(source = "hobbyId", target = "hobby")
    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
