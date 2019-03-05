package sandkev.dogs.domain;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * Created by kevsa on 04/03/2019.
 */
@Value
@Builder
public class TaskAggregator {
    Map<String, TaskAggregate> tasks;
    TaskAggregate add(Task task){
        TaskAggregate taskAggregate = tasks.get(task.getTaskType());
        if(taskAggregate == null){
            taskAggregate = new TaskAggregate.TaskAggregateBuilder()
                    .key(task.getTaskType())
                    .build();
        }
        return taskAggregate.add(task);
    }
}
