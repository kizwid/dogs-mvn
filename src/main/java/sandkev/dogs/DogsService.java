package sandkev.dogs;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

//@Component
public class DogsService implements DogDao {
    @Autowired DogsRepository repository;
    public void add(DogDto dto) {
        repository.save(toEntity(dto));
    }
    public void delete(long id) {
        repository.deleteById(id);
    }
    public List<Dog> getDogs() {
        return (List<Dog>) repository.findAll();
    }
    public Dog findDogById(long id) {
        Optional<Dog> optionalDog = repository.findById(id);
        return optionalDog.orElseThrow(() -> new DogNotFoundException("Couldn't find a Dog with id: " + id));
    }
    private Dog toEntity(DogDto dto) {
        return Dog.of(dto.getId(), dto.getName(), dto.getAge());
    }
}