package org.reactome.tcrd.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "protein")
// One direction export
@JsonIgnoreProperties(value = {"target"})
public class Protein {
    @Id
    private Long id;
    private String name;
    private String description;
    private String uniprot;
    private String sym;
    // The following annotation is based on hibernate doc:
    // https://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#entity-mapping-association
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t2tc",
    joinColumns = @JoinColumn(name = "protein_id"),
    inverseJoinColumns = @JoinColumn(name = "target_id"))
    private Target target;
    
    public Protein() {
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
