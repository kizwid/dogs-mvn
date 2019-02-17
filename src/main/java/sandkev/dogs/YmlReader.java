package sandkev.dogs;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YmlReader {
    public static final Logger logger = LoggerFactory.getLogger(YmlReader.class);

    public YmlReader() {
    }

    public static <T> T read(InputStream inputStream, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));
        return (T)yaml.load(inputStream);
    }

    public static <T> T readFromClasspath(String path, Class<T> clazz) {
        logger.info("Reading the configuration file from class path {}", path);
        InputStream in = YmlReader.class.getResourceAsStream(path);
        if(in != null) {
            return read(in, clazz);
        } else {
            throw new YmlReader.InvalidYmlPathException("The file " + path + " doesn\'t exit in the classpath");
        }
    }

    public static <T> T readFromFile(String path, Class<T> clazz) {
        if( !(path==null||path.length()==0)) {
            File file = new File(path);
            return readFromFile(file, clazz);
        } else {
            throw new YmlReader.InvalidYmlPathException("The Yml file argument is not valid: " + path);
        }
    }

    public static <T> T readFromFile(File file, Class<T> clazz) {
        if(file != null) {
            try {
                return read(new FileInputStream(file), clazz);
            } catch (FileNotFoundException var3) {
                throw new YmlReader.InvalidYmlPathException("The file " + file.getAbsolutePath() + " doesn\'t exit in the file system");
            }
        } else {
            throw new YmlReader.InvalidYmlPathException("The Yml file argument is null");
        }
    }

    public static Map<String, ?> readFromResourceAsMap(String resource) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        if(inputStream==null){
            throw new IllegalArgumentException("Resource not found: " + resource);
        }
        return read(inputStream, Map.class);
    }

    public static class InvalidYmlPathException extends RuntimeException {
        public InvalidYmlPathException(String s) {
            super(s);
        }

        public InvalidYmlPathException(String s, Throwable throwable) {
            super(s, throwable);
        }
    }
}
