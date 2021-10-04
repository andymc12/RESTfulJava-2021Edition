package net.andymc12.propdb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Props extends Properties implements AutoCloseable {

    private final Path propsFile;

    Props(Path propsFile) throws IOException {
        super();
        this.propsFile = propsFile;
        load(Files.newInputStream(propsFile));
    }

    @Override
    public void close() throws IOException {
        store(Files.newOutputStream(propsFile), "Written by PropsDB");
    }
    
}
