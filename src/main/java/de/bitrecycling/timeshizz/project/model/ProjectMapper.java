package de.bitrecycling.timeshizz.project.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(source = "project.client.id", target = "clientId")
    ProjectJson toJson(ProjectEntity project);
    ProjectEntity toEntity(ProjectJson project);
}
