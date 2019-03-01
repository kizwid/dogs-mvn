package sandkev.dogs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor(staticName = "of")
public class WorkflowSummary {
    @Id
    String workflowId;
    Date startTime;
    Date endTime;
    String name;
    int runningTaskCount;
    int completedTaskCount;
    int failedTaskCount;
    int totalElapsedTaskExecutionTime;
    int averageElapsedTaskExecutionTime;
    int minElapsedTaskExecutionTime;
    int maxElapsedTaskExecutionTime;
    int totalElapsedTaskQueuingTime;
    int averageElapsedTaskQueuingTime;
    int minElapsedTaskQueuingTime;
    int maxElapsedTaskQueuingTime;
    int totalCpuRuningTime;
}

