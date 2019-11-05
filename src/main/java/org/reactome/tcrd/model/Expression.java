package org.reactome.tcrd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expression")
public class Expression {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "etype")
    private ExpressionType etype;
    // This is not supported yet.
    // Comment out for the time being
//    @ManyToOne
//    private Target target;
    @ManyToOne
    private Protein protein;
    private String tissue;
    @Column(name = "qual_value")
    private String qualValue;
    @Column(name = "number_value")
    private Double numberValue;
    @Column(name = "boolean_value")
    private Boolean booleanValue;
    @Column(name = "string_value")
    private String stringValue;
    @Column(name = "pubmed_id")
    private Long pubmedId;
    private String evidence;
    private Double zscore;
    private Double conf;
    private String oid;
    private Boolean confidence;
//    private String age;
    private String url;
    @Column(name = "uberon_id")
    private String uberonId;
    
    public Expression() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpressionType getEtype() {
        return etype;
    }

    public void setEtype(ExpressionType etype) {
        this.etype = etype;
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

    public String getQualValue() {
        return qualValue;
    }

    public void setQualValue(String qualValue) {
        this.qualValue = qualValue;
    }

    public Double getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Double numberValue) {
        this.numberValue = numberValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getPubmedId() {
        return pubmedId;
    }

    public void setPubmedId(Long pubmedId) {
        this.pubmedId = pubmedId;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Double getZscore() {
        return zscore;
    }

    public void setZscore(Double zscore) {
        this.zscore = zscore;
    }

    public Double getConf() {
        return conf;
    }

    public void setConf(Double conf) {
        this.conf = conf;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Boolean getConfidence() {
        return confidence;
    }

    public void setConfidence(Boolean confidence) {
        this.confidence = confidence;
    }

//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUberonId() {
        return uberonId;
    }

    public void setUberonId(String uberonId) {
        this.uberonId = uberonId;
    }
    
}
