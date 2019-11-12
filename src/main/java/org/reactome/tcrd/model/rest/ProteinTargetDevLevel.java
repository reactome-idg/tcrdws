package org.reactome.tcrd.model.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This class is used to provide target dev level information in the restful API.
 * @author wug
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProteinTargetDevLevel extends ProteinProperty {
    
    // enum('Tclin+','Tclin','Tchem+','Tchem','Tbio','Tgray','Tdark') 
    private String targetDevLevel;
    
    public ProteinTargetDevLevel() {
    }

    public String getTargetDevLevel() {
        return targetDevLevel;
    }

    public void setTargetDevLevel(String targetDevLevel) {
        this.targetDevLevel = targetDevLevel;
    }

}
