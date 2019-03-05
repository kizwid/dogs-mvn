package sandkev.dogs.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Created by kevsa on 04/03/2019.
 */
@Value
@Builder
public class TaskAggregate {
    String key;
    Aggregate startTime;
    Aggregate endTime;
    Aggregate inputSize;
    Aggregate outputSize;
    TaskAggregate add(Task task){

        TaskAggregateBuilder builder = new TaskAggregateBuilder()
                .key(task.getTaskType());

        Aggregate.AggregateBuilder aggregateBuilder = new Aggregate.AggregateBuilder();

        switch (task.getStatus()){
            case STARTED:{
                int newCount = startTime.getCount() + 1;
                long newSum = startTime.getSum() + task.getStartTime();
                aggregateBuilder.count(newCount);
                aggregateBuilder.sum(newSum);
                aggregateBuilder.average(newSum/newCount);
                aggregateBuilder.min(Math.min(startTime.getMin(), task.getStartTime()));
                aggregateBuilder.max(Math.max(startTime.getMax(), task.getStartTime()));
                builder.startTime(aggregateBuilder.build());
                break;
            }
            case RUNNING:{
                //TODO: add time spent in the queue
                break;
            }
            case COMPLETED:{
                int newCount = endTime.getCount() + 1;
                long newSum = endTime.getSum() + task.getEndTime();
                aggregateBuilder.count(newCount);
                aggregateBuilder.sum(newSum);
                aggregateBuilder.average(newSum/newCount);
                aggregateBuilder.min(Math.min(endTime.getMin(), task.getEndTime()));
                aggregateBuilder.max(Math.max(endTime.getMax(), task.getEndTime()));
                builder.endTime(aggregateBuilder.build());
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
        return builder.build();
    }
}
