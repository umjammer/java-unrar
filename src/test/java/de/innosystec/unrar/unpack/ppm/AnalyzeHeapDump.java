
package de.innosystec.unrar.unpack.ppm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.file.Files;

import static java.lang.System.getLogger;


/**
 * For debugging purposes only.
 *
 * @author alban
 */
public class AnalyzeHeapDump {

    private static final Logger logger = getLogger(AnalyzeHeapDump.class.getName());

    /** Creates a new instance of AnalyzeHeapDump */
    public AnalyzeHeapDump() {
    }

    public static void main(String[] argv) {
        File cfile = new File("P:\\test\\heapdumpc");
        File jfile = new File("P:\\test\\heapdumpj");
        if (!cfile.exists()) {
            System.err.println("File not found: " + cfile.getAbsolutePath());
            return;
        }
        if (!jfile.exists()) {
            System.err.println("File not found: " + jfile.getAbsolutePath());
            return;
        }
        long clen = cfile.length();
        long jlen = jfile.length();
        if (clen != jlen) {
            logger.log(Level.TRACE, "File size mismatch");
            logger.log(Level.TRACE, "clen = " + clen);
            logger.log(Level.TRACE, "jlen = " + jlen);
        }
        // Do byte comparison
        long len = Math.min(clen, jlen);
        InputStream cin = null;
        InputStream jin = null;
        int bufferLen = 256 * 1024;
        try {
            cin = new BufferedInputStream(Files.newInputStream(cfile.toPath()), bufferLen);
            jin = new BufferedInputStream(Files.newInputStream(jfile.toPath()), bufferLen);
            boolean matching = true;
            boolean mismatchFound = false;
            long startOff = 0L;
            long off = 0L;
            while (off < len) {
                if (cin.read() != jin.read()) {
                    if (matching) {
                        startOff = off;
                        matching = false;
                        mismatchFound = true;
                    }
                } else { // match
                    if (!matching) {
                        printMismatch(startOff, off);
                        matching = true;
                    }
                }
                off++;
            }
            if (!matching) {
                printMismatch(startOff, off);
            }
            if (!mismatchFound) {
                logger.log(Level.TRACE, "Files are identical");
            }
            logger.log(Level.TRACE, "Done");
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        } finally {
            try {
                cin.close();
                jin.close();
            } catch (IOException e) {
                logger.log(Level.ERROR, e.getMessage(), e);
            }
        }
    }

    private static void printMismatch(long startOff, long bytesRead) {
        logger.log(Level.INFO, "Mismatch: off=" + startOff + "(0x" + Long.toHexString(startOff) + "), len=" + (bytesRead - startOff));
    }
}
