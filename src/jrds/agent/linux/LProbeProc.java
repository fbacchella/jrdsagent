package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import jrds.agent.LProbe;

public abstract class LProbeProc extends LProbe {

    private File statFile = null; 

    @Override
    public void setProperty(String specific, String value) {
        if("statFile".equals(specific)) {
            statFile = new File(value);
        } else {
            super.setProperty(specific, value);            
        }
    }

    /**
     * @return the statFile
     */
    protected File getStatFile() {
        return statFile;
    }

    public BufferedReader readStatFile() throws FileNotFoundException {
        return new BufferedReader(new FileReader(statFile));
    }

}
