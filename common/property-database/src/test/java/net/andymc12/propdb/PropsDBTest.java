package net.andymc12.propdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PropsDBTest {

    private static Path tmpDir;
    private Path tmpFile;

    @BeforeAll
    public static void classSetup() throws IOException {
        tmpDir = Files.createTempDirectory("propsDBTest");
    }

    @AfterAll
    public static void classTeardown() throws IOException {
        Files.delete(tmpDir);
    }

    @AfterEach
    public void methodTeardown() throws IOException {
        PropsDB.instances.clear();
        if (tmpFile != null) {
            //System.out.println(tmpFile.toString());
            Files.delete(tmpFile);
        }
    }

    @Test
    public void sameFileReturnsSameDB() throws Exception {
        PropsDB db1 = PropsDB.getDB("/tmp/abc.txt");
        assertSame(db1, PropsDB.getDB("/tmp/abc.txt"));
    }

    @Test
    public void differentFileReturnsDifferentDB() throws Exception {
        PropsDB db1 = PropsDB.getDB("/tmp/abc.txt");
        assertNotSame(db1, PropsDB.getDB("/tmp/xyz.txt"));
    }

    @Test
    public void closeRemovesInstances() throws Exception {
        assertEquals(0, PropsDB.instances.size());
        PropsDB.getDB("/tmp/abc.txt");
        assertEquals(1, PropsDB.instances.size());
        PropsDB.getDB("/tmp/abc.txt");
        assertEquals(1, PropsDB.instances.size());
        PropsDB.getDB("/tmp/xyz.txt");
        assertEquals(2, PropsDB.instances.size());
        PropsDB.getDB("/tmp/abc.txt").close();
        assertEquals(1, PropsDB.instances.size());
        PropsDB.getDB("/tmp/xyz.txt").close();
        assertEquals(0, PropsDB.instances.size());
    }

    // Create Tests
    @Test
    public void createAndRead() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "createAndRead", "txt").toAbsolutePath();
        PropsDB.getDB(tmpFile.toString()).createProperty("foo", "bar");
        assertEquals("bar", PropsDB.getDB(tmpFile.toString()).readProperty("foo"));
    }

    @Test
    public void createDuplicatePropThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "createDuplicatePropThrowsException", "txt").toAbsolutePath();
        PropsDB.getDB(tmpFile.toString()).createProperty("foo", "bar");
        assertThrows(DuplicateKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).createProperty("foo", "bar"));
    }

    @Test
    public void createNullKeyThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "createNullKeyThrowsException", "txt").toAbsolutePath();
        assertThrows(InvalidKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).createProperty(null, "bar"));
    }

    @Test
    public void createNullValueThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "createNullValueThrowsException", "txt").toAbsolutePath();
        assertThrows(InvalidValueException.class, () -> PropsDB.getDB(tmpFile.toString()).createProperty("foo", null));
    }


    // Read tests
    // basic read test accomplished above in createAndRead()
    @Test
    public void readNullKeyThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "readNullKeyThrowsException", "txt").toAbsolutePath();
        assertThrows(InvalidKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).readProperty(null));
    }

    @Test
    public void readUnkownKeyThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "readUnkownKeyThrowsException", "txt").toAbsolutePath();
        assertThrows(UnknownKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).readProperty("foo"));
    }

    // Update tests
    @Test
    public void updateProperty() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "updateProperty", "txt").toAbsolutePath();
        PropsDB.getDB(tmpFile.toString()).createProperty("foo", "bar");
        assertEquals("bar", PropsDB.getDB(tmpFile.toString()).updateProperty("foo", "baz"));
        assertEquals("baz", PropsDB.getDB(tmpFile.toString()).readProperty("foo"));
    }

    @Test
    public void updateUnknownKeyThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "updateUnknownKeyThrowsException", "txt").toAbsolutePath();
        assertThrows(UnknownKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).updateProperty("foo", "bar"));
    }

    @Test
    public void updateNullKeyThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "updateNullKeyThrowsException", "txt").toAbsolutePath();
        assertThrows(InvalidKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).updateProperty(null, "bar"));
    }

    @Test
    public void updateNullValueThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "updateNullValueThrowsException", "txt").toAbsolutePath();
        PropsDB.getDB(tmpFile.toString()).createProperty("foo", "bar");
        assertThrows(InvalidValueException.class, () -> PropsDB.getDB(tmpFile.toString()).updateProperty("foo", null));
    }

    // Delete tests
    @Test
    public void deleteProperty() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "deleteProperty", "txt").toAbsolutePath();
        PropsDB.getDB(tmpFile.toString()).createProperty("foo", "bar");
        assertEquals("bar", PropsDB.getDB(tmpFile.toString()).deleteProperty("foo"));
        assertThrows(UnknownKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).readProperty("foo"));
    }

    @Test
    public void deleteNullKeyThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "deleteNullKeyThrowsException", "txt").toAbsolutePath();
        PropsDB.getDB(tmpFile.toString()).createProperty("foo", "bar");
        assertThrows(InvalidKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).deleteProperty(null));
    }

    @Test
    public void deleteUnkownKeyThrowsException() throws Exception {
        tmpFile = Files.createTempFile(tmpDir, "deleteUnkownKeyThrowsException", "txt").toAbsolutePath();
        assertThrows(UnknownKeyException.class, () -> PropsDB.getDB(tmpFile.toString()).deleteProperty("foo"));
    }

}
