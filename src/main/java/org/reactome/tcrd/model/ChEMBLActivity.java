package org.reactome.tcrd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cmpd_activity")
public class ChEMBLActivity extends Activity {
    @Column(name = "cmpd_name_in_src")
    private String compoundChEMBLNameInRef;
    @Column(name = "pubmed_ids")
    private String pubmedIds;
    
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

    public String getPubmedIds() {
        return pubmedIds;
    }

    public void setPubmedIds(String pubmedIds) {
        this.pubmedIds = pubmedIds;
    }

}
