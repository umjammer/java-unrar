/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.crc;


/**
 * RarCRC
 *
 * @author Edmund Wagner
 * @version 29.05.2007
 */
public abstract class RarCRC {

    private static final int[] crcTab;

    static {
        crcTab = new int[256];
        for (int i = 0; i < 256; i++) {
            int c = i;
            for (int j = 0; j < 8; j++) {
                if ((c & 1) != 0) {
                    c >>>= 1;
                    c ^= 0xEDB88320;
                } else {
                    c >>>= 1;
                }
            }
            crcTab[i] = c;
        }
    }

    public static int checkCrc(int startCrc, byte[] data, int offset, int count) {
        int size = Math.min(data.length - offset, count);

        for (int i = 0; i < size; i++) {

            startCrc = (crcTab[(startCrc ^ data[offset + i]) & 0xff] ^ (startCrc >>> 8));

            //System.out.println(Integer.toHexString(startCrc));
        }
        return (startCrc);
    }

    public static short checkOldCrc(short startCrc, byte[] data, int count) {
        int n = Math.min(data.length, count);
        for (int i = 0; i < n; i++) {
            startCrc = (short) ((short) (startCrc + (short) (data[i] & 0x00ff)) & -1);
            startCrc = (short) (((startCrc << 1) | (startCrc >>> 15)) & -1);
        }
        return (startCrc);
    }
}
