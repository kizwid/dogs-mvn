package sandkev.dogs;

import lombok.Builder;
import lombok.Data;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

/**
 * Created by kevsa on 17/02/2019.
 */
@Builder
@Data
public class AppConfig {
    //Flyway flyway;
    DataSource dataSource;
}
