package sandkev.dogs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor(staticName = "of")
public class Dog {
    @Id
    @GeneratedValue
    long id;
    String name;
    int age;
}

