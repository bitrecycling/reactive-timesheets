package de.bitrecycling.timeshizz.management.api;

import de.bitrecycling.timeshizz.management.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(source = "project.client.id", target = "clientId")
    ProjectJson toJson(Project project);

    Project toEntity(ProjectJson project);
}
