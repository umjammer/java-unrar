/*
 * Copyright (c) 2021 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package de.innosystec.unrar.tika;

import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.jupiter.api.Test;
import org.xml.sax.ContentHandler;


class RARParserTest {

    @Test
    void test() throws Exception {
        InputStream is = RARParserTest.class.getResourceAsStream("/test.rar");
//        Parser parser = new AutoDetectParser(); // TODO got `Tika Zip bomb detected!`
        Parser parser = new RARParser();

        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        parser.parse(is, handler, metadata, new ParseContext());
    }
}

/* */
