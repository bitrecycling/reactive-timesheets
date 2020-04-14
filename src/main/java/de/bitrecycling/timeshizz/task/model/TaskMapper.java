package de.bitrecycling.timeshizz.task.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "id", target = "taskId")
    public TaskEntryJson toJson(TaskEntryEntity entity);
    public TaskEntryEntity toEntity(TaskEntryJson taskEntryJson);
    @Mapping(source = "id", target = "projectId")
    public TaskJson toJson(TaskEntity entity);
    public TaskEntity toEntity(TaskJson json);
}
