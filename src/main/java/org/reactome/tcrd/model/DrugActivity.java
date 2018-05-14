package org.reactome.tcrd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drug_activity")
public class DrugActivity extends Activity {
    private String drug;
    @Column(name = "action_type")
    private String actionType; // MoA
    // TODO: Need to check the tcrd group: Some of entries have action_type
    // values, but labeled as 0 for has_moa.
    @Column(name = "has_moa")
    private Boolean hasMoa;
    private String source;
    @Column(name = "nlm_drug_info")
    private String nlmDrugInfo;
    
    public DrugActivity() {
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Boolean getHasMoa() {
        return hasMoa;
    }

    public void setHasMoa(Boolean hasMoa) {
        this.hasMoa = hasMoa;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNlmDrugInfo() {
        return nlmDrugInfo;
    }

    public void setNlmDrugInfo(String nlmDrugInfo) {
        this.nlmDrugInfo = nlmDrugInfo;
    }
}
