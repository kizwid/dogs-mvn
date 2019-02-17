package sandkev.dogs;

import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;

import java.util.Map;

/**
 * Created by kevsa on 17/02/2019.
 */
public class AppConfigFactory {

    public AppConfig create(){
        return create("application.yml");
    }
    public AppConfig create(String resource){
        Map configMap = YmlReader.readFromResourceAsMap(resource);
        return buildConfig(configMap);
    }
    private AppConfig buildConfig(Map configMap) {
        AppConfig.AppConfigBuilder builder = AppConfig.builder();
        Map<String, String> datasourceMap = (Map<String,String>) configMap.get("datasource");

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(datasourceMap.get("driver-class-name"));
        ds.setUrl(datasourceMap.get("url"));
        ds.setUsername(datasourceMap.get("username"));
        ds.setPassword(datasourceMap.get("password"));

        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        //flyway.migrate();

        builder.dataSource(ds);
        return builder.build();
    }

}
