package org.reactome.tcrd.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class is used to map the expression data provided by gtex in the same
 * named table.
 * @author wug
 */
@Entity
@Table(name = "gtex")
public class GTEx {
    
    @Id
    private Long id;
    @ManyToOne
    private Protein protein;
    private String tissue;
    private String gender;
    private Double tpm;
    private Double tau;

    public GTEx() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public String getTissue() {
        return tissue;
    }

    public void setTissue(String tissue) {
        this.tissue = tissue;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getTpm() {
        return tpm;
    }

    public void setTpm(Double tpm) {
        this.tpm = tpm;
    }

    public Double getTau() {
        return tau;
    }

    public void setTau(Double tau) {
        this.tau = tau;
    }
    
}
