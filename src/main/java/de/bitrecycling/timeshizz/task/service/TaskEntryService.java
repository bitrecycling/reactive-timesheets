package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.task.model.TaskEntryEntity;
import de.bitrecycling.timeshizz.task.repository.TaskEntryRepository;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The task service
 * created by robo
 */
@Service
public class TaskEntryService {

    @Autowired
    private TaskEntryRepository taskEntryRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRespository projectRespository;
    @Autowired
    private ClientRepository clientRepository;

    public Iterable<TaskEntryEntity> all() {
        return taskEntryRepository.findAll();
    }

    public List<TaskEntryEntity> getAllByTaskId(UUID taskId) {
        return taskEntryRepository.findAllByTaskIdOrderByCreationTimeDesc(taskId);
    }

    public List<TaskEntryEntity> getMostRecentByStartTime(Integer count) {
        PageRequest of = PageRequest.of(0, count);
        return taskEntryRepository.findAllByOrderByStartTimeDesc(of);
    }

    public List<TaskEntryEntity> getByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskEntryRepository.findAllByCreationTimeBetween(from, to);
    }

    public List<TaskEntryEntity> getByStartTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskEntryRepository.findAllByStartTimeBetween(from, to);
    }
    
    public List<TaskEntryEntity> getByStartTimeBetween(LocalDateTime from, LocalDateTime to, UUID taskId) {
        return taskEntryRepository.findAllByTaskIdAndStartTimeBetween(taskId, from, to);
    }

    public List<TaskEntryEntity> getPagedAscending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable);
    }

    public List<TaskEntryEntity> getPagedDescending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskEntryRepository.findAllByOrderByCreationTimeDesc(pageable);

    }

    public TaskEntryEntity insert(TaskEntryEntity taskEntry) {
        return taskEntryRepository.save(taskEntry);
    }

    public TaskEntryEntity byId(UUID id) {
        return taskEntryRepository.findById(id).orElseThrow(()->new RuntimeException("TaskEntry["+id+"] not available"));
    }
    
    public TaskEntryEntity save(TaskEntryEntity taskEntry) {
        return taskEntryRepository.save(taskEntry);
    }

    public void delete(UUID taskEntryId) {
        taskEntryRepository.deleteById(taskEntryId);
    }

}
