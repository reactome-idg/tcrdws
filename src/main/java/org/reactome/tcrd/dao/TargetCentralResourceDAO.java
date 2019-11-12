package org.reactome.tcrd.dao;

import java.util.Collection;
import java.util.List;

import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
import org.reactome.tcrd.model.ExpressionType;
import org.reactome.tcrd.model.rest.ProteinExpression;
import org.reactome.tcrd.model.rest.ProteinTargetDevLevel;

public interface TargetCentralResourceDAO {
    
    public List<ExpressionType> listExpressionTypes();
    
    public List<String> getTissues(String etype);
    
    /**
     * Query ChEBMLActvities for a protein specified by its UniProt id.
     * @param uniprot
     * @return
     */
    public List<ChEMBLActivity> queryChEBMLActitiesForId(String uniprot);
    
    public List<ChEMBLActivity> queryChEMBLActivitesForIds(Collection<String> uniprotIds);
//    
    public List<ChEMBLActivity> queryChEMBLActivitiesForGene(String gene);
    
    public List<ChEMBLActivity> queryChEMBLActivitiesForGenes(Collection<String> genes);
    
    public List<DrugActivity> queryDrugActivitiesForId(String uniprot);
    
    public List<DrugActivity> queryDrugActivitiesForIds(Collection<String> uniprotIds);
    
    public List<DrugActivity> queryDrugActivitiesForGene(String gene);
    
    public List<DrugActivity> queryDrugActivitiesForGenes(Collection<String> genes);
    
    public ProteinTargetDevLevel queryProteinTargetLevel(String uniProt);
    
    public List<ProteinTargetDevLevel> queryProteinTargetLevels(Collection<String> uniProts);
    
    public List<ProteinExpression> queryProteinExpressions(Collection<String> uniProts,
                                                           Collection<String> tissues,
                                                           Collection<String> etypes);
}
