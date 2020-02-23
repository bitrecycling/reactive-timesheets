package de.bitrecycling.timeshizz.report;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.task.model.TaskEntity;
import de.bitrecycling.timeshizz.task.model.TaskEntryEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportSerializer extends JsonSerializer<ReportEntity> {
    @Override
    public void serialize(ReportEntity report, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("client",report.getClient());

        writeProjects(report.getProjects(), jsonGenerator);
        jsonGenerator.writeEndObject();

    }

    private void writeProjects(Map<ProjectEntity, Map<TaskEntity, List<TaskEntryEntity>>> projects, JsonGenerator jsonGenerator) throws IOException{

        jsonGenerator.writeFieldName("projects");
        jsonGenerator.writeStartArray();
        for(Map.Entry projectEntry : projects.entrySet()){
            ProjectEntity project = (ProjectEntity) projectEntry.getKey();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", project.getName());
            jsonGenerator.writeStringField("description", project.getDescription());
            jsonGenerator.writeNumberField("rate", project.getRate());
            Map<TaskEntity, List<TaskEntryEntity>> tasks = (Map<TaskEntity, List<TaskEntryEntity>>) projectEntry.getValue();
            writeTasks(tasks, jsonGenerator);
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }

    private void writeTasks(Map<TaskEntity, List<TaskEntryEntity>> tasks, JsonGenerator jsonGenerator) throws IOException {

        jsonGenerator.writeFieldName("tasks");
        jsonGenerator.writeStartArray();

        for(Map.Entry taskEntry: tasks.entrySet()){
            TaskEntity task = (TaskEntity) taskEntry.getKey();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", task.getName());
            List<TaskEntryEntity> taskEntries = ( List<TaskEntryEntity>)taskEntry.getValue();
            jsonGenerator.writeFieldName("taskEntries");
            jsonGenerator.writeStartArray();
            for (TaskEntryEntity cur : taskEntries){
                jsonGenerator.writeObject(cur);
//                    jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();
    }
}
