package de.bitrecycling.timeshizz.report;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.model.TaskEntry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportSerializer extends JsonSerializer<Report> {
    @Override
    public void serialize(Report report, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("client",report.getClient());

        writeProjects(report.getProjects(), jsonGenerator);
        jsonGenerator.writeEndObject();

    }

    private void writeProjects(Map<Project, Map<Task, List<TaskEntry>>> projects, JsonGenerator jsonGenerator) throws IOException{

        jsonGenerator.writeFieldName("projects");
        jsonGenerator.writeStartArray();
        for(Map.Entry projectEntry : projects.entrySet()){
            Project project = (Project) projectEntry.getKey();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", project.getName());
            jsonGenerator.writeStringField("description", project.getDescription());
            jsonGenerator.writeNumberField("rate", project.getRate());
            Map<Task, List<TaskEntry>> tasks = (Map<Task, List<TaskEntry>>) projectEntry.getValue();
            writeTasks(tasks, jsonGenerator);
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }

    private void writeTasks(Map<Task, List<TaskEntry>> tasks, JsonGenerator jsonGenerator) throws IOException {

        jsonGenerator.writeFieldName("tasks");
        jsonGenerator.writeStartArray();

        for(Map.Entry taskEntry: tasks.entrySet()){
            Task task = (Task) taskEntry.getKey();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", task.getName());
            List<TaskEntry> taskEntries = ( List<TaskEntry>)taskEntry.getValue();
            jsonGenerator.writeFieldName("taskEntries");
            jsonGenerator.writeStartArray();
            for (TaskEntry cur : taskEntries){
                jsonGenerator.writeObject(cur);
//                    jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();
    }
}
