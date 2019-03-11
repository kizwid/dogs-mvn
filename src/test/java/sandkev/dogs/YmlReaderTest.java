package sandkev.dogs;

import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * Created by kevsa on 10/03/2019.
 */
public class YmlReaderTest {

    @Test
    public void canBuildFromInputStream() {
        String yaml = "fooWidgets:\n" +
                "  - name: blah\n" +
                "  - name: meh\n" +
                "name: bob";
        BarWidget service = new Yaml().loadAs(new StringReader(yaml), BarWidget.class);
        System.out.println(service);

        yaml = "things:\n" +
                "  - name: blah\n" +
                "    age: 3000\n" +
                "    silly: true\n" +
                "  - name: meh\n" +
                "    age: 13939\n" +
                "    silly: false\n" +
                "uuid: 1938484\n" +
                "active: false";
        MyConfig myConfig = MyConfig.fromYaml(yaml);
        System.out.println(myConfig);

    }

    @Data
    public static class Widget {
        String name;
        Integer age;
        boolean silly;
    }

    @Data
    //@Builder
    public static class MyConfig {
        List<Widget> things;
        String uuid;
        boolean isActive;

        public static MyConfig fromYaml(String yaml) {
            Constructor c = new Constructor(MyConfig.class);
            TypeDescription t = new TypeDescription(MyConfig.class);
            t.addPropertyParameters("things", Widget.class);
            c.addTypeDescription(t);

            return new Yaml(c).load(yaml);
//            return new Yaml().loadAs(yaml, MyConfig.class);
        }
    }

    //@Builder
    @Data
    public static class FooWidget {
        String name;
    }

    @Data
    public static class BarWidget {
        String name;
        List<FooWidget> fooWidgets;
    }

    @Test
    public void canLoadServiceFromLoadAs() {

        String yaml = "" +
                "hostName: 134.37.74.171\n" +
                "port: 8080\n" +
                "components: \n" +
                "    - name: cmp1\n" +
                "      logDir: C:\\\\cmp1\\\\logs\n" +
                "      logFile: cmp1.log\n" +
                "      componentProperties:\n" +
                "          no_of_repeats: 4\n" +
                "          batch_size: 100\n" +
                "    - name: cmp2\n" +
                "      logDir: C:\\\\cmp2\\\\logs\n" +
                "      logFile: cmp2.log\n" +
                "      componentProperties:\n" +
                "          no_of_repeats: 3\n" +
                "          batch_size: 200\n" +
                "componentProperties:\n" +
                " -\n" +
                "  - no_of_repeats: 4\n" +
                "  - batch_size: 100\n" +
                " -\n" +
                "  - no_of_repeats: 3\n" +
                "  - batch_size: 200";

        Service service = new Yaml().loadAs(new StringReader(yaml), Service.class);
        System.out.println(service);

    }

    @Data
    public static class Component {
        String name;
        String logDir;
        String logFile;
        Map<String, String> componentProperties;
    }

    @Data
    public static class Service {
        String hostName;
        String port;
        List<Component> components;
        List<List<String>> componentProperties;
    }

}