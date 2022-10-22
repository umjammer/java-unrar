/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 *
 * the unrar licence applies to all junrar source and binary distributions
 * you are not allowed to use this source to re-create the RAR compression algorithm
 */

package de.innosystec.unrar.unpack;

import de.innosystec.unrar.unpack.vm.VMPreparedProgram;


/**
 * UnpackFilter
 *
 * @author Edmund Wagner
 * @version 01.06.2007
 */
public class UnpackFilter {

    private int BlockStart;

    private int BlockLength;

    private int ExecCount;

    private boolean NextWindow;

    // position of parent filter in Filters array used as prototype for filter
    // in PrgStack array. Not defined for filters in Filters array.
    private int ParentFilter;

    private VMPreparedProgram Prg = new VMPreparedProgram();

    public int getBlockLength() {
        return BlockLength;
    }

    public void setBlockLength(int blockLength) {
        BlockLength = blockLength;
    }

    public int getBlockStart() {
        return BlockStart;
    }

    public void setBlockStart(int blockStart) {
        BlockStart = blockStart;
    }

    public int getExecCount() {
        return ExecCount;
    }

    public void setExecCount(int execCount) {
        ExecCount = execCount;
    }

    public boolean isNextWindow() {
        return NextWindow;
    }

    public void setNextWindow(boolean nextWindow) {
        NextWindow = nextWindow;
    }

    public int getParentFilter() {
        return ParentFilter;
    }

    public void setParentFilter(int parentFilter) {
        ParentFilter = parentFilter;
    }

    public VMPreparedProgram getPrg() {
        return Prg;
    }

    public void setPrg(VMPreparedProgram prg) {
        Prg = prg;
    }
}
