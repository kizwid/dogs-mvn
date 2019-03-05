package sandkev.dogs.domain;

import java.util.Map;

/**
 * Created by kevsa on 04/03/2019.
 */
public class TaskTracker {
    Map<String, Task> taskById;

    void update(Task task){

        switch (task.getStatus()){
            case STARTED:{
                break;
            }
            case RUNNING:{
                break;
            }
            case COMPLETED:{
                break;
            }
            case CANCELLED:{
                break;
            }
            case FAILED:{
                break;
            }
            default:{
                throw new IllegalStateException("Unknown task status " + task.getStatus());
            }

        }

    }
}
