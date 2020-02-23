package de.bitrecycling.timeshizz.task.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "entity.task.id", target = "taskId")
    public TaskEntryJson toJson(TaskEntryEntity entity);
    public TaskEntryEntity toEntity(TaskEntryJson taskEntryJson);
    @Mapping(source = "entity.project.id", target = "projectId")
    public TaskJson toJson(TaskEntity entity);
    public TaskEntity toEntity(TaskJson json);
}
