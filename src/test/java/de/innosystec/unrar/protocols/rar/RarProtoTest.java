
package de.innosystec.unrar.protocols.rar;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;
import vavi.util.Debug;


// TODO use spi
public class RarProtoTest {

    static {
        Debug.println(System.setProperty("java.protocol.handler.pkgs", "de.innosystec.unrar.protocols"));
    }

    @Test
    void test1() throws Exception {
        Debug.println(System.getProperty("java.protocol.handler.pkgs"));

        File rarFile = new File("src/test/resources/test.rar");
        String inFile = "tmp/ja.properties";
        URL url = new URL("rar:" + rarFile.toURI().toURL() + "!/" + inFile);
        Debug.println(url);
        Debug.printf("Host: %s, proto: %s, path: %s, ref: %s, query: %s%n",
                url.getHost(), url.getProtocol(), url.getPath(), url.getRef(), url.getQuery());

        URLConnection urlc = url.openConnection();
        urlc.connect();
        Debug.println("CONTENT TYPE: " + urlc.getContentType());
        Debug.println("CONTENT LENGTH: " + urlc.getContentLength());

        InputStream is = urlc.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is, "ms932"));
        String aux;
        System.out.println("---- contents ----");
        while ((aux = br.readLine()) != null) {
            System.out.println(aux);
        }
        is.close();
        br.close();
    }
}
