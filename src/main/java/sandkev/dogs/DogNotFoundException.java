package sandkev.dogs;

/**
 * Created by kevsa on 16/02/2019.
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException(String message) {
        super(message);
    }
}
