/*
 * Copyright (c) 2021 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package de.innosystec.unrar.crc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RarCRCTest {

    @Test
    void test() {
        // byte[] data = { 0x72, 0x21, 0x1A, 0x07, 0x00};

        byte[] data = {
            0x73, 0x00, 0x00, 0x0D, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        int result = RarCRC.checkCrc(0xFFFFffff, data, 0, data.length);
        assertEquals(0x90cf, ~result & 0xffff);
    }
}

/* */
