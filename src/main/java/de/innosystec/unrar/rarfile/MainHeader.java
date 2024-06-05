/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.rarfile;

import de.innosystec.unrar.io.Raw;


/**
 * The main header of an rar archive. holds information concerning the whole
 * archive (solid, encrypted etc).
 *
 * @author Edmund Wagner
 * @version 22.05.2007
 */
public class MainHeader extends BaseBlock {

    public static final short mainHeaderSizeWithEnc = 7;

    public static final short mainHeaderSize = 6;

    private final short highPosAv;

    private final int posAv;

    private byte encryptVersion;

    public MainHeader(BaseBlock bb, byte[] mainHeader) {
        super(bb);
        int pos = 0;
        highPosAv = Raw.readShortLittleEndian(mainHeader, pos);
        pos += 2;
        posAv = Raw.readIntLittleEndian(mainHeader, pos);
        pos += 4;

        if (hasEncryptVersion()) {
            encryptVersion |= mainHeader[pos] & 0xff;
        }
    }

    /**
     * old cmt block is present
     *
     * @return true if has cmt block
     */
    public boolean hasArchCmt() {
        return (this.flags & BaseBlock.MHD_COMMENT) != 0;
    }

    /**
     * the version the the encryption
     */
    public byte getEncryptVersion() {
        return encryptVersion;
    }

    public short getHighPosAv() {
        return highPosAv;
    }

    public int getPosAv() {
        return posAv;
    }

    /**
     * returns whether the archive is encrypted
     */
    public boolean isEncrypted() {
        return (this.flags & BaseBlock.MHD_PASSWORD) != 0;
    }

    /**
     * return whether the archive is a multivolume archive
     */
    public boolean isMultiVolume() {
        return (this.flags & BaseBlock.MHD_VOLUME) != 0;
    }

    /**
     * if the archive is a multivolume archive this method returns whether this
     * instance is the first part of the multivolume archive
     */
    public boolean isFirstVolume() {
        return (this.flags & BaseBlock.MHD_FIRSTVOLUME) != 0;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "posav: " + getPosAv() +
                "\nhighposav: " + getHighPosAv() +
                "\nhasencversion: " + hasEncryptVersion() + (hasEncryptVersion() ? getEncryptVersion() : "") +
                "\nhasarchcmt: " + hasArchCmt() +
                "\nisEncrypted: " + isEncrypted() +
                "\nisMultivolume: " + isMultiVolume() +
                "\nisFirstvolume: " + isFirstVolume() +
                "\nisSolid: " + isSolid() +
                "\nisLocked: " + isLocked() +
                "\nisProtected: " + isProtected() +
                "\nisAV: " + isAV();
    }

    /**
     * returns whether this archive is solid. in this case you can only extract
     * all file at once
     */
    public boolean isSolid() {
        return (this.flags & MHD_SOLID) != 0;
    }

    public boolean isLocked() {
        return (this.flags & MHD_LOCK) != 0;
    }

    public boolean isProtected() {
        return (this.flags & MHD_PROTECT) != 0;
    }

    public boolean isAV() {
        return (this.flags & MHD_AV) != 0;
    }

    /**
     * the numbering format a multivolume archive
     */
    public boolean isNewNumbering() {
        return (this.flags & MHD_NEWNUMBERING) != 0;
    }
}
