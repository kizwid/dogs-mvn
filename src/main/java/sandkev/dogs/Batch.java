package sandkev.dogs;

import lombok.Builder;
import lombok.Value;

/**
 * Created by kevsa on 02/03/2019.
 */
@Value
@Builder
public class Batch {
    String name;
    String riskGroup;
}
