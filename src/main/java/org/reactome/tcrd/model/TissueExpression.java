package org.reactome.tcrd.model;

/**
 * This class is used to provide tissue specific expression infor to the restful API
 * @author brunsont
 *
 */

public class TissueExpression {
	
	private String uniprot;
	private String sym;
	private String tissue;
	//Expression Type
	private String etype;
	private Double numberVal;

	public TissueExpression() {/*Nothing Here*/}

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

	public String getTissue() {
		return tissue;
	}

	public void setTissue(String tissue) {
		this.tissue = tissue;
	}

	public String getEtype() {
		return etype;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

	public Double getNumberVal() {
		return numberVal;
	}

	public void setNumberVal(Double numberVal) {
		this.numberVal = numberVal;
	}
	
}
