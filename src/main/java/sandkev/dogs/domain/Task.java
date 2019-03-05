package sandkev.dogs.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Created by kevsa on 04/03/2019.
 */
@Value
@Builder
public class Task {
    String id;
    String taskType;
    String parent;
    long startTime;
    long endTime;
    long inputSize;
    long outputSize;
    TaskStatus status;
}
