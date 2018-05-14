package org.reactome.tcrd.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "target")
@JsonIgnoreProperties(value = {"chemblActivities", "drugActivities"})
public class Target {
    @Id
    private Long id;
    private String name;
    @Column(name = "ttype")
    private String targetType; // This is always "Single Protein" so far
    private String description;
    private String comment;
    @Column(name = "tdl")
    private String targetDevLevel;
    // The following annotation is based on hibernate doc:
    // https://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#entity-mapping-association
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t2tc",
    joinColumns = @JoinColumn(name = "target_id"),
    inverseJoinColumns = @JoinColumn(name = "protein_id"))
    private Protein protein;
    @OneToMany(mappedBy = "target")
    private Set<ChEMBLActivity> chemblActivities;
    @OneToMany(mappedBy = "target")
    private Set<DrugActivity> drugActivities;
    
    public Set<DrugActivity> getDrugActivities() {
        return drugActivities;
    }

    public void setDrugActivities(Set<DrugActivity> drugActivities) {
        this.drugActivities = drugActivities;
    }

    public Target() {
    }

    public Set<ChEMBLActivity> getChemblActivities() {
        return chemblActivities;
    }

    public void setChemblActivities(Set<ChEMBLActivity> chemblActivities) {
        this.chemblActivities = chemblActivities;
    }

    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTargetDevLevel() {
        return targetDevLevel;
    }

    public void setTargetDevLevel(String targetDevLevel) {
        this.targetDevLevel = targetDevLevel;
    }
    
}
