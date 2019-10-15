package org.reactome.tcrd.service;

import java.util.Collection;
import java.util.List;

import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
import org.reactome.tcrd.model.ProteinTargetDevLevel;

public interface TargetCentralResourceService {
    
    /**
     * Query ChEBMLActvities for a protein specified by its UniProt id.
     * @param uniprot
     * @return
     */
    public List<ChEMBLActivity> queryChEMBLActivitiesForId(String uniprotId);
    
    public List<ChEMBLActivity> queryChEMBLActivitiesForIds(Collection<String> uniprotIds);
    
    public List<ChEMBLActivity> queryChEMBLActivitiesForGene(String gene);
    
    public List<ChEMBLActivity> queryChEMBLActivitiesForGenes(Collection<String> genes);
    
    public List<DrugActivity> queryDrugActivitiesForId(String uniprot);
    
    public List<DrugActivity> queryDrugActivitiesForIds(Collection<String> uniprotIds);
    
    public List<DrugActivity> queryDrugActivitiesForGene(String gene);
    
    public List<DrugActivity> queryDrugActivitiesForGenes(Collection<String> genes);
    
    public ProteinTargetDevLevel queryProteinTargetLevel(String uniProt);
    
    public List<ProteinTargetDevLevel> queryProteinTargetLevels(Collection<String> uniProts);
}
