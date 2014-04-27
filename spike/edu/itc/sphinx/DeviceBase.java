package edu.itc.sphinx;

import java.io.File;

public abstract class DeviceBase implements IDevice {

    /**
     * Abstract play to be implemented
     */
    public abstract void play(File file);

    @Override
    public void deallocate() { }
}
