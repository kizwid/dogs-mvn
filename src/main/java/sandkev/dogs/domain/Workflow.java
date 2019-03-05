package sandkev.dogs.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Created by kevsa on 04/03/2019.
 */
@Value
@Builder
public class Workflow {
    String id;
    BusinessContext businessContext;
    int valueDate;

}
