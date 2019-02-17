package sandkev.dogs;

import java.util.List;

/**
 * Created by kevsa on 16/02/2019.
 */
public interface DogDao {
    List<Dog> getDogs();
    Dog findDogById(long id);
    void add(DogDto dto);
    void delete(long id);

    //Dog save(Dog dog);
}
