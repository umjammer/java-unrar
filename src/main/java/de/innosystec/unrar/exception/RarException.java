/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.exception;


/**
 * RarException
 *
 * @author Edmund Wagner
 * @version 30.07.2007
 */
public class RarException extends Exception {

    private RarExceptionType type;

    public RarException(Exception e) {
        super(RarExceptionType.unkownError.name(), e);
        this.type = RarExceptionType.unkownError;
    }

    public RarException(RarException e) {

        super(e.getMessage(), e);
        this.type = e.getType();
    }

    public RarException(RarExceptionType type) {
        super(type.name());
        this.type = type;
    }

    public enum RarExceptionType {
        notImplementedYet,
        crcError,
        notRarArchive,
        badRarArchive,
        unkownError,
        headerNotInArchive,
        wrongHeaderType,
        ioError,
        rarEncryptedException;
    }

    public RarExceptionType getType() {
        return type;
    }

    public void setType(RarExceptionType type) {
        this.type = type;
    }
}
