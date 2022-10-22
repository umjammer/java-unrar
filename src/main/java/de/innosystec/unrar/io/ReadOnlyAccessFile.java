/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * ReadOnlyAccessFile
 *
 * @author Edmund Wagner
 * @version 23.05.2007
 */
public class ReadOnlyAccessFile extends RandomAccessFile implements IReadOnlyAccess {

    /**
     * @param file the file
     * @throws FileNotFoundException
     */
    public ReadOnlyAccessFile(File file) throws FileNotFoundException {
        super(file, "r");
    }

    public int readFully(byte[] buffer, int count) throws IOException {
        assert (count > 0) : count;
        this.readFully(buffer, 0, count);
        return count;
    }

    public long getPosition() throws IOException {
        return this.getFilePointer();
    }

    public void setPosition(long pos) throws IOException {
        this.seek(pos);
    }
}
