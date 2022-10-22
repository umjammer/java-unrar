
package de.innosystec.unrar.protocols.rar;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;


public class RarProtoTest {

    static {
        System.out.println(System.setProperty("java.protocol.handler.pkgs", "de.innosystec.unrar.protocols"));
    }

    @Test
    void test1() throws Exception {
        System.out.println(System.getProperty("java.protocol.handler.pkgs"));

        File rarFile = new File("src/test/resources/test.rar");
        String inFile = "tmp/ja.properties";
        URL url = new URL("rar:" + rarFile.toURI().toURL() + "!/" + inFile);
        System.out.println(url);
        System.out.println("Host: " + url.getHost() + ", proto: " + url.getProtocol() + ", path: " + url.getPath() + ", ref: "
                           + url.getRef() + ", query: " + url.getQuery());

        URLConnection urlc = url.openConnection();
        urlc.connect();
        System.out.println("CONTENT TYPE: " + urlc.getContentType());
        System.out.println("CONTENT LENGTH: " + urlc.getContentLength());

        InputStream is = urlc.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        @SuppressWarnings("unused")
        String aux = null;
        while ((aux = br.readLine()) != null) {
            // System.out.println(aux);
        }
        is.close();
        br.close();
    }

}
