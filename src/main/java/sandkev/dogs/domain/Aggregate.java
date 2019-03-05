package sandkev.dogs.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Created by kevsa on 04/03/2019.
 */
@Value
@Builder
public class Aggregate {
    int count;
    long sum;
    long average;
    long min;
    long max;
}
