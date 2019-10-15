package org.reactome.tcrd.model;

/**
 * This class is used to provide target dev level information in the restful API.
 * @author wug
 *
 */
public class ProteinTargetDevLevel {
    
    private String uniprot;
    private String sym;
    // enum('Tclin+','Tclin','Tchem+','Tchem','Tbio','Tgray','Tdark') 
    private String targetDevLevel;
    
    public ProteinTargetDevLevel() {
        
    }

    public String getUniprot() {
        return uniprot;
    }

    public void setUniprot(String uniprot) {
        this.uniprot = uniprot;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getTargetDevLevel() {
        return targetDevLevel;
    }

    public void setTargetDevLevel(String targetDevLevel) {
        this.targetDevLevel = targetDevLevel;
    }

}
