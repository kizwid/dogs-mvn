package sandkev.dogs.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Created by kevsa on 04/03/2019.
 */
@Value
@Builder
public class BusinessContext {
    String region;
    String domain;
    String group;
    String batchName;
    String batchType;
}
