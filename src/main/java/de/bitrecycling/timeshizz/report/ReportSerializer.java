package de.bitrecycling.timeshizz.report;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportSerializer extends JsonSerializer<ReportJson> {
    @Override
    public void serialize(ReportJson report, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
//        jsonGenerator.writeObjectField("client",report.getClient());
//
//        writeProjects(report.getProjects(), jsonGenerator);
        jsonGenerator.writeEndObject();

    }

    private void writeProjects(Map<ProjectEntity, Map<ActivityEntity, List<ActivityEntryEntity>>> projects, JsonGenerator jsonGenerator) throws IOException{

        jsonGenerator.writeFieldName("projects");
        jsonGenerator.writeStartArray();
        for(Map.Entry projectEntry : projects.entrySet()){
            ProjectEntity project = (ProjectEntity) projectEntry.getKey();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", project.getName());
            jsonGenerator.writeStringField("description", project.getDescription());
            jsonGenerator.writeNumberField("rate", project.getRate());
            Map<ActivityEntity, List<ActivityEntryEntity>> tasks = (Map<ActivityEntity, List<ActivityEntryEntity>>) projectEntry.getValue();
            writeActivitys(tasks, jsonGenerator);
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }

    private void writeActivitys(Map<ActivityEntity, List<ActivityEntryEntity>> tasks, JsonGenerator jsonGenerator) throws IOException {

        jsonGenerator.writeFieldName("tasks");
        jsonGenerator.writeStartArray();

        for(Map.Entry taskEntry: tasks.entrySet()){
            ActivityEntity activity= (ActivityEntity) taskEntry.getKey();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", activity.getName());
            List<ActivityEntryEntity> taskEntries = ( List<ActivityEntryEntity>)taskEntry.getValue();
            jsonGenerator.writeFieldName("taskEntries");
            jsonGenerator.writeStartArray();
            for (ActivityEntryEntity cur : taskEntries){
                jsonGenerator.writeObject(cur);
//                    jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();
    }
}
