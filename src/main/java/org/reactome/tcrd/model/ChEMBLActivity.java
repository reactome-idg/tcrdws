package org.reactome.tcrd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "chembl_activity")
public class ChEMBLActivity extends Activity {
    @Column(name = "cmpd_name_in_ref")
    private String compoundChEMBLNameInRef;
    @Column(name = "pubmed_id")
    private Long pubmedId;
    
    public ChEMBLActivity() {
        
    }

    public String getCompoundChEMBLId() {
        return compoundChEMBLId;
    }

    public void setCompoundChEMBLId(String compoundChEMBLId) {
        this.compoundChEMBLId = compoundChEMBLId;
    }

    public String getCompoundChEMBLNameInRef() {
        return compoundChEMBLNameInRef;
    }

    public void setCompoundChEMBLNameInRef(String compoundChEMBLNameInRef) {
        this.compoundChEMBLNameInRef = compoundChEMBLNameInRef;
    }

    public Long getPubmedId() {
        return pubmedId;
    }

    public void setPubmedId(Long pubmedId) {
        this.pubmedId = pubmedId;
    }

}
