package com.test.DesignPatterns.Adapter;

import org.aspectj.org.eclipse.jdt.internal.core.SourceType;

/**
 * Created by tanzepeng on 2016/3/7.
 */
public class NoteBook {

    public ThreePlug threePlug;

    public NoteBook(ThreePlug threePlug) {
        this.threePlug = threePlug;
    }

    public void toPower() {
        threePlug.power();
    }

    public static void main(String[] args) {
        TwoPlug twoPlug = new TwoPlug();
        ThreePlug threePlug = new TwoToThreePlugAdapter(twoPlug);
        NoteBook noteBook = new NoteBook(threePlug);
        noteBook.toPower();
    }
}
