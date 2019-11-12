package org.reactome.tcrd.model.rest;

public abstract class ProteinProperty {

    private String uniprot;
    private String sym;

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

}
