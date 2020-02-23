package de.bitrecycling.timeshizz.client.model;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientJson toJson(ClientEntity entity);
    ClientEntity toEntity(ClientJson entity);
    
}
