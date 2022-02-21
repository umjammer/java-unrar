/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.rarfile;

import java.util.logging.Logger;

import de.innosystec.unrar.io.Raw;


/**
 * SubBlockHeader.
 *
 * @author Edmund Wagner
 * @version 21.11.2007
 */
public class SubBlockHeader extends BlockHeader {
    private static final Logger logger = Logger.getLogger(SubBlockHeader.class.getName());

    public static final short SubBlockHeaderSize = 3;

    private short subType;

    private byte level;

    public SubBlockHeader(SubBlockHeader sb) {
        super(sb);
        subType = sb.getSubType().getSubblocktype();
        level = sb.getLevel();
    }

    public SubBlockHeader(BlockHeader bh, byte[] subblock) {
        super(bh);
        int position = 0;
        subType = Raw.readShortLittleEndian(subblock, position);
        position += 2;
        level |= subblock[position] & 0xff;
    }

    /** */
    public byte getLevel() {
        return level;
    }

    /** */
    public SubBlockHeaderType getSubType() {
        return SubBlockHeaderType.findSubblockHeaderType(subType);
    }

    public void print() {
        super.print();
        logger.fine("subtype: " + getSubType());
        logger.fine("level: " + level);
    }
}
