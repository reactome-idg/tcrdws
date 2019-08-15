package org.reactome.tcrd.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Activity {
    @Id
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Target target;
    private String smiles;
    // This should be -log10 of affinity values
    @Column(name = "act_value")
    private Double activityValue;
    @Column(name = "act_type")
    private String activityType;
    private String reference;
    @Column(name = "cmpd_pubchem_cid")
    protected String compoundChEMBLId;
    
    public Activity() {
        
    }

    public String getCompoundChEMBLId() {
        return compoundChEMBLId;
    }

    public void setCompoundChEMBLId(String compoundChEMBLId) {
        this.compoundChEMBLId = compoundChEMBLId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public Double getActivityValue() {
        return activityValue;
    }

    public void setActivityValue(Double activityValue) {
        this.activityValue = activityValue;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}
