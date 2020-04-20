package de.bitrecycling.timeshizz.client.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "entity.projects", target = "projects")
    ClientJson toJson(ClientEntity entity);
    ClientEntity toEntity(ClientJson entity);
    
}
