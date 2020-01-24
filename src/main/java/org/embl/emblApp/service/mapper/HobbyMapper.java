package org.embl.emblApp.service.mapper;

import org.embl.emblApp.domain.*;
import org.embl.emblApp.service.dto.HobbyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hobby} and its DTO {@link HobbyDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface HobbyMapper extends EntityMapper<HobbyDTO, Hobby> {

    @Mapping(source = "person.id", target = "personId")
    HobbyDTO toDto(Hobby hobby);

    @Mapping(target = "people", ignore = true)
    @Mapping(target = "removePerson", ignore = true)
    @Mapping(source = "personId", target = "person")
    Hobby toEntity(HobbyDTO hobbyDTO);

    default Hobby fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hobby hobby = new Hobby();
        hobby.setId(id);
        return hobby;
    }
}
