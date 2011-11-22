package jrds.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public abstract class LProbe {
    private File statFile;

    public LProbe() {
        super();
    }

    public Boolean configure() {
        return true;
    }

    public abstract Map<String, Number> query();

    /**
     * Used to generate an uniq name for the local instance of the probe
     * can be meaningless (not intend to human use), but must be
     * persistent accross reboot
     * @return the probe instance name
     */
    public abstract String getName();

    /**
     * @return the statFile
     */
    protected File getStatFile() {
        return statFile;
    }

    /**
     * @param statFile the statFile to set
     */
    public void setStatFile(File statFile) {
        this.statFile = statFile;
    }

    public BufferedReader readStatFile() throws FileNotFoundException {
        return new BufferedReader(new FileReader(getStatFile()));
    }

}
