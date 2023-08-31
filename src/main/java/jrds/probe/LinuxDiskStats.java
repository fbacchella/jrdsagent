package jrds.probe;

import java.util.List;

import jrds.factories.ProbeBean;

@ProbeBean({"checkPath"})
public class LinuxDiskStats extends RMIIndexed {

    private boolean checkPath;

    /**
     * @return the checkPath
     */
    public String getCheckPath() {
        return Boolean.toString(checkPath);
    }

    /**
     * @param checkPath the checkPath to set
     */
    public void setCheckPath(String checkPath) {
        this.checkPath = Boolean.parseBoolean(checkPath);
    }



    @Override
    protected void setArgs(List<Object> l) {
        if (! checkPath) {
            l.add(checkPath);
        }
        super.setArgs(l);
    }

}
