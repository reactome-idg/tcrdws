package org.reactome.tcrd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gene_attribute_type")
public class GeneAttributeType {
    @Id
    private Long id;
    private String name;
    private String association;
    private String description;
    @Column(name = "resource_group")
    private String resourceGroup;
    private String measurement;
    @Column(name = "attribute_group")
    private String attributeGroup;
    @Column(name = "attribute_type")
    private String attriuteType;
    @Column(name = "pubmed_ids")
    private String pubmedIds;
    private String url;
    
    public GeneAttributeType() {
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

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceGroup() {
        return resourceGroup;
    }

    public void setResourceGroup(String resourceGroup) {
        this.resourceGroup = resourceGroup;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getAttributeGroup() {
        return attributeGroup;
    }

    public void setAttributeGroup(String attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    public String getAttriuteType() {
        return attriuteType;
    }

    public void setAttriuteType(String attriuteType) {
        this.attriuteType = attriuteType;
    }

    public String getPubmedIds() {
        return pubmedIds;
    }

    public void setPubmedIds(String pubmedIds) {
        this.pubmedIds = pubmedIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    

}
