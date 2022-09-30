
package de.innosystec.unrar;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import de.innosystec.unrar.rarfile.FileHeader;


public class MVTest {

    @Test
    void test1() throws Exception {
        Path file = Paths.get("src/test/resources/volumes/new-part/test-documents.part1.rar");
        Archive a = new Archive(file.toFile());
        a.getMainHeader().print();
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
}
