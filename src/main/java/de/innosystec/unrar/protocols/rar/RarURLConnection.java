
package de.innosystec.unrar.protocols.rar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.nio.file.Files;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;

import static java.lang.System.getLogger;


/**
 * @author Luca Santarelli luca.santarelli@gmail.com
 */
public class RarURLConnection extends URLConnection {

    private static final Logger logger = getLogger(RarURLConnection.class.getName());

    /**
     * Will contain file data after a successful connection.
     */
    private InputStream is = null;

    private int contentLength = -1;

    private String contentType;

    private URL rarURL = null;

    private String entry = null;

    protected RarURLConnection(URL url) throws MalformedURLException {
        super(url);

        String[] aux = url.getPath().split("!/");
        if (aux.length > 2) {
            throw new MalformedURLException();
        } else {
            rarURL = new URL(aux[0]);
            if (aux.length == 2) {
                entry = aux[1];
            }
        }
    }

    @Override
    public void connect() throws IOException {
logger.log(Level.DEBUG, "Requested to connect; url is " + url);

        try {
            File rarFile;
            if (rarURL.getProtocol().compareTo("file") == 0) {
                rarFile = new File(rarURL.toURI());
            } else {
                throw new IOException("Cannot open rar files on protocol " + rarURL.getProtocol() + " (yet)");
            }

            if (!rarFile.exists()) {
                throw new FileNotFoundException("File not found: " + rarFile.toURI());
            }

            // The following line raises a RarException if the file is not a
            // true rar file.
            Archive rarArchive = new Archive(rarFile);
            if (entry == null) {
                contentLength = (int) rarFile.length();
                contentType = "application/x-rar-compressed";
                is = Files.newInputStream(rarFile.toPath());
            } else {
                FileHeader fileHeader = null;
                while ((fileHeader = rarArchive.nextFileHeader()) != null) {
                    if (!fileHeader.isDirectory()) {
logger.log(Level.DEBUG, fileHeader.getFileName() + ", " + entry);
                        if (fileHeader.getFileName().replace('\\', '/').compareTo(entry) == 0) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            rarArchive.extractFile(fileHeader, baos);
                            baos.flush();
                            contentLength = baos.size();
                            contentType = "unknown";
                            is = new RarEntryInputStream(rarArchive, baos.toByteArray());
                            break;
                        }
                    }
                }
                if (is == null) {
                    throw new FileNotFoundException("Entry " + entry + " not found in archive " + rarFile.toURI());
                }
            }
            connected = true;
        } catch (URISyntaxException | RarException usex) {
            logger.log(Level.ERROR, usex.getMessage(), usex);
            IOException ioex = new IOException(usex.getMessage());
            ioex.setStackTrace(usex.getStackTrace());
            throw ioex;
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (!connected) {
            connect();
        }
        if (is == null) {
            throw new IOException("File " + entry + " does not exist in archive " + rarURL);
        }
        return is;
    }

    @Override
    public int getContentLength() {
        return contentLength;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    /* Methods with no meaning. */
    @Override
    public OutputStream getOutputStream() throws UnknownServiceException {
        throw new UnknownServiceException();
    }
}
