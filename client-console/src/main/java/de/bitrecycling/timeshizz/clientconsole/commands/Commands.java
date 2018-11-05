package de.bitrecycling.timeshizz.clientconsole.commands;

import de.bitrecycling.timeshizz.clientlib.client.model.Client;
import de.bitrecycling.timeshizz.clientlib.client.service.ClientService;
import de.bitrecycling.timeshizz.clientlib.project.service.ProjectService;
import de.bitrecycling.timeshizz.clientlib.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.shell.Shell;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ShellComponent
public class Commands {

    @Autowired
    ClientService clientService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;

    @ShellMethod("create client")
    public String create(String name, String address) {
        Client client = new Client();
        client.setName(name);
        client.setAddress(address);
        return clientService.createClient(client).block().toString();
    }

    @ShellMethod("create client")
    public List<Client> clients() {

        List<Client> block = clientService.loadAllClients().collectList().block();
        return block;
    }

}
