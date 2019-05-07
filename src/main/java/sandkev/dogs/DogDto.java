package sandkev.dogs;

import lombok.Builder;
import lombok.Data;

/**
 * Created by kevsa on 16/02/2019.
 */
@Data
@Builder
public class DogDto {
    private long id;
    private String name;
    private int age;
}