
package de.innosystec.unrar;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.innosystec.unrar.rarfile.FileHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import vavi.util.Debug;
import vavi.util.properties.annotation.Property;
import vavi.util.properties.annotation.PropsEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@PropsEntity(url = "file:local.properties")
public class MVTest {

    static boolean localPropertiesExists() {
        return Files.exists(Paths.get("local.properties"));
    }

    @Property(name = "archive.rar.file")
    String file;

    @BeforeEach
    void setup() throws Exception {
        if (localPropertiesExists()) {
            PropsEntity.Util.bind(this);
        }
    }
    @Test
    void test1() throws Exception {
        Path file = Paths.get("src/test/resources/volumes/new-part/test-documents.part1.rar");
        Archive a = new Archive(file.toFile());
Debug.println(a.getMainHeader());
        FileHeader fh = a.nextFileHeader();
        Path dir = Paths.get("tmp");
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        while (fh != null) {
            Path out = dir.resolve(fh.getFileNameString().trim());
            System.out.println(out.toAbsolutePath());
            OutputStream os = Files.newOutputStream(out);
            a.extractFile(fh, os);
            os.close();
            fh = a.nextFileHeader();
        }
    }

    @Test
    public void test0() throws Exception {
        Archive archive = new Archive(new File("src/test/resources/test.rar"));
        int c = 0;
        for (FileHeader fileHeader : archive.getFileHeaders()) {
            System.err.println(fileHeader.getFileName() + "\t" + fileHeader.getMTime());
            c++;
        }
        assertEquals(6, c);
    }

    @Test
    @EnabledIf("localPropertiesExists")
    public void test() throws Exception {
        Archive archive = new Archive(new File(file));
        int c = 0;
        for (FileHeader fileHeader : archive.getFileHeaders()) {
            System.err.println(fileHeader.getFileName() + "\t" + fileHeader.getMTime());
        }
    }
}
