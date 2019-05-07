package sandkev.dogs;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevsa on 07/05/2019.
 */
public class DogParserTest {

    @Test
    public void canParseDogsXml() throws IOException, XMLStreamException {

        String xml = "" +
                "<pets>" +
                "<cats/>" +
                "<dogs>" +
                "<dog>" +
                "<name>buster</name>" +
                "<id>1</id>" +
                "<age>2</age>" +
                "</dog>" +
                "</dogs>" +
                "</pets>";

        DogParser parser = new DogParser();
        List<DogDto> dogs = parser.parse(xml);
        assertEquals(1,dogs.size());
        assertEquals("buster", dogs.get(0).getName());

    }

}