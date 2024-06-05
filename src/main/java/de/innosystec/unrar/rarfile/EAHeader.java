/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.rarfile;

import de.innosystec.unrar.io.Raw;


/**
 * extended archive CRC header
 *
 * @author Edmund Wagner
 * @version 27.11.2007
 */
public class EAHeader extends SubBlockHeader {

    public static final short EAHeaderSize = 10;

    private final int unpSize;

    private byte unpVer;

    private byte method;

    private final int EACRC;

    public EAHeader(SubBlockHeader sb, byte[] eahead) {
        super(sb);
        int pos = 0;
        unpSize = Raw.readIntLittleEndian(eahead, pos);
        pos += 4;
        unpVer |= eahead[pos] & 0xff;
        pos++;
        method |= eahead[pos] & 0xff;
        pos++;
        EACRC = Raw.readIntLittleEndian(eahead, pos);
    }

    /**
     * @return the eACRC
     */
    public int getEACRC() {
        return EACRC;
    }

    /**
     * @return the method
     */
    public byte getMethod() {
        return method;
    }

    /**
     * @return the unpSize
     */
    public int getUnpSize() {
        return unpSize;
    }

    /**
     * @return the unpVer
     */
    public byte getUnpVer() {
        return unpVer;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
        ", unpSize: " + unpSize +
        ", unpVersion: " + unpVer +
        ", method: " + method +
        ", EACRC:" + EACRC;
    }
}
