/*
 * Copyright (c) 2025 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package de.innosystec.unrar.unpack.unsigned;

import static de.innosystec.unrar.unsigned.UnsignedByte.add;
import static de.innosystec.unrar.unsigned.UnsignedByte.sub;


/**
 * UnsignedByteTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2025-07-19 nsano initial version <br>
 */
class UnsignedByteTest {

    public static void main(String[] args) {
        //tests unsigned (signed)
        //add
        System.out.println(add((byte) 0xfe, (byte) 0x01)); //255 (-1)
        System.out.println(add((byte) 0xff, (byte) 0x01)); //0 (0)
        System.out.println(add((byte) 0x7f, (byte) 0x01)); //128 (-128)
        System.out.println(add((byte) 0xff, (byte) 0xff)); //254 (-2)

        //sub
        System.out.println(sub((byte) 0xfe, (byte) 0x01)); //253 (-3)
        System.out.println(sub((byte) 0x00, (byte) 0x01)); //255 (-1)
        System.out.println(sub((byte) 0x80, (byte) 0x01)); //127 (127)
        //mul
        System.out.println((byte) -1 * (byte) -1);
    }
}
