/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.rarfile;

import de.innosystec.unrar.io.Raw;


/**
 * Base class of headers that contain data
 *
 * @author Edmund Wagner
 * @version 22.05.2007
 */
public class BlockHeader extends BaseBlock {
    public static final short blockHeaderSize = 4;

    private int dataSize;

    private int packSize;

    public BlockHeader() {
    }

    public BlockHeader(BlockHeader bh) {
        super(bh);
        this.packSize = bh.getDataSize();
        this.dataSize = packSize;
        this.positionInFile = bh.getPositionInFile();
    }

    public BlockHeader(BaseBlock bb, byte[] blockHeader) {
        super(bb);

        this.packSize = Raw.readIntLittleEndian(blockHeader, 0);
        this.dataSize = this.packSize;
    }

    public int getDataSize() {
        return dataSize;
    }

    public int getPackSize() {
        return packSize;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
            "DataSize: " + getDataSize() + " packSize: " + getPackSize();
    }
}
