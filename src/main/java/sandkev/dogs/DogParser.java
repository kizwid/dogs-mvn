package sandkev.dogs;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * simple example of sTax parser.
 */
public class DogParser {

    private static final String ELEMENT_DOG = "dog";
    private static final String ELEMENT_NAME = "name";
    private static final String ELEMENT_ID = "id";
    private static final String ELEMENT_AGE = "age";

    private final XMLInputFactory factory;

    public DogParser() {
        factory = XMLInputFactory.newInstance();
    }

    public List<DogDto> parse(final String xml) throws IOException, XMLStreamException {
        List<DogDto> dogs = new ArrayList();
        try (final InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            //try (final ZipInputStream zip = new ZipInputStream(stream)) {
            final XMLEventReader reader = factory.createXMLEventReader(stream);
            while (reader.hasNext()) {
                final XMLEvent event = reader.nextEvent();
                if (event.isStartElement() && event.asStartElement().getName()
                        .getLocalPart().equals(ELEMENT_DOG)) {
                    dogs.add(parseDog(reader, ELEMENT_DOG));
                }
            }
            //}
        }
        return dogs;
    }

    private DogDto parseDog(final XMLEventReader reader, String endElement) throws XMLStreamException {
        DogDto.DogDtoBuilder builder = DogDto.builder();
        while (reader.hasNext()) {
            final XMLEvent event = reader.nextEvent();
            if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(endElement)) {
                return builder.build();
            }
            if (event.isStartElement()) {
                final StartElement element = event.asStartElement();
                final String elementName = element.getName().getLocalPart();
                switch (elementName) {
                    case ELEMENT_NAME:
                        builder.name(reader.getElementText());
                        break;
                    case ELEMENT_ID:
                        builder.id(Integer.parseInt(reader.getElementText()));
                        break;
                    case ELEMENT_AGE:
                        builder.age(Integer.parseInt(reader.getElementText()));
                        break;
                }
            }
        }
        return builder.build();
    }


}
