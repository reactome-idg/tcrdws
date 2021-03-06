package org.reactome.tcrd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "expression_type")
public class ExpressionType {
    
    @Id
    private String name;
    @Column(name = "data_type")
    private String dataType;
    private String description;
    @Transient
    private String unit;
    @Transient
    private Boolean hasGender;
    
    public ExpressionType() {
    }

    public Boolean getHasGender() {
        return hasGender;
    }

    public void setHasGender(Boolean hasGender) {
        this.hasGender = hasGender;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
