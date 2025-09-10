
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
    String file = "src/test/resources/test.rar";

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
        if (!Files.exists(dir)) Files.createDirectories(dir);
        while (fh != null) {
            Path out = dir.resolve(fh.getFileName().trim());
            System.out.println(out.toAbsolutePath());
            OutputStream os = Files.newOutputStream(out);
            a.extractFile(fh, os);
            os.close();
            fh = a.nextFileHeader();
        }
    }

    @Test
    public void test0() throws Exception {
        Archive archive = new Archive(Path.of(file).toFile());
        int c = 0;
        for (FileHeader fileHeader : archive.getFileHeaders()) {
            System.err.printf("%-40s  %s%n", fileHeader.getFileName(), fileHeader.getMTime());
            c++;
        }
        assertEquals(6, c);
        archive.close();
    }

    @Test
    @EnabledIf("localPropertiesExists")
    public void test() throws Exception {
        Archive archive = new Archive(Path.of(file).toFile());
        for (FileHeader fileHeader : archive.getFileHeaders()) {
            System.err.printf("%-40s  %s%n", fileHeader.getFileName(), fileHeader.getMTime());
        }
        archive.close();
    }

    @Test
    @EnabledIf("localPropertiesExists")
    public void test2() throws Exception {
        Path in = Path.of(file);
        Path dir = Path.of("tmp").resolve(in.getFileName());
        Archive archive = new Archive(new File(file));
        if (!Files.exists(dir)) Files.createDirectories(dir);
        for (FileHeader fileHeader : archive.getFileHeaders()) {
            Path out = dir.resolve(fileHeader.getFileName().replace("\\","/"));
            if (!Files.exists(out.getParent())) Files.createDirectories(out.getParent());
            archive.extractFile(fileHeader, Files.newOutputStream(out));
        }
        archive.close();
    }
}
