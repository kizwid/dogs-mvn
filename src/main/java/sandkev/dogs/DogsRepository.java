package sandkev.dogs;

/**
 * Created by kevsa on 16/02/2019.
 */
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogsRepository extends CrudRepository<Dog, Long>, DogDao {

}
