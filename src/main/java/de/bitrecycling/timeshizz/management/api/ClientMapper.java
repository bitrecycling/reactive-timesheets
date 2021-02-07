package de.bitrecycling.timeshizz.management.api;

import de.bitrecycling.timeshizz.management.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "entity.projects", target = "projects")
    ClientJson toJson(Client entity);

    Client toEntity(ClientJson entity);

}
