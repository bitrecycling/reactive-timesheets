package de.bitrecycling.timeshizz.activity.model;

import de.bitrecycling.timeshizz.common.NlpTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    @Mapping(source = "activity.id", target = "activityId")
    public ActivityEntryJson toJson(ActivityEntryEntity entity);
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "parseNLP")
    public ActivityEntryEntity toEntity(ActivityEntryJson activityEntryJson);
    @Mapping(source = "project.id", target = "projectId")
    public ActivityJson toJson(ActivityEntity entity);
    public ActivityEntity toEntity(ActivityJson json);
    
    default LocalDateTime parseNLP(String nlp){
        return NlpTime.parseTimeFromNlpString(nlp);
    }
}
