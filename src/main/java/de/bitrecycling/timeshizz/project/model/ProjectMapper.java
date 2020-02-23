package de.bitrecycling.timeshizz.project.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectEntity toEntity(ProjectJson project);
    ProjectJson toEntity(ProjectEntity project);
}
