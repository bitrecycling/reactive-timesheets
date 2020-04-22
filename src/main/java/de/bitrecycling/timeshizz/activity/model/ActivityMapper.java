package de.bitrecycling.timeshizz.activity.model;

import de.bitrecycling.timeshizz.common.NlpTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    @Mapping(source = "activity.id", target = "activityId")
    ActivityEntryJson toJson(ActivityEntryEntity entity);
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "parseNLP")
    ActivityEntryEntity toEntity(ActivityEntryJson activityEntryJson);
    @Mapping(source = "project.id", target = "projectId")
    ActivityJson toJson(ActivityEntity entity);
    ActivityEntity toEntity(ActivityJson json);
    
    default Optional<LocalDateTime> parseNLP(String nlp){
        return NlpTime.parseTimeFromNlpString(nlp);
    }
}
