/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.rarfile;

import de.innosystec.unrar.io.Raw;


/**
 * Mac File attribute header
 *
 * @author Edmund Wagner
 * @version 26.11.2007
 */
public class MacInfoHeader extends SubBlockHeader {

    public static final short MacInfoHeaderSize = 8;

    private int fileType;

    private int fileCreator;

    public MacInfoHeader(SubBlockHeader sb, byte[] macHeader) {
        super(sb);
        int pos = 0;
        fileType = Raw.readIntLittleEndian(macHeader, pos);
        pos += 4;
        fileCreator = Raw.readIntLittleEndian(macHeader, pos);
    }

    /**
     * @return the fileCreator
     */
    public int getFileCreator() {
        return fileCreator;
    }

    /**
     * @param fileCreator the fileCreator to set
     */
    public void setFileCreator(int fileCreator) {
        this.fileCreator = fileCreator;
    }

    /**
     * @return the fileType
     */
    public int getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                ", filetype: " + fileType +
                ", creator :" + fileCreator;
    }
}
