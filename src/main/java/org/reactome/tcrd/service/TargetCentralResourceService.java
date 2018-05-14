package org.reactome.tcrd.service;

import java.util.Collection;
import java.util.List;

import org.reactome.tcrd.model.ChEMBLActivity;

public interface TargetCentralResourceService {
    
    /**
     * Query ChEBMLActvities for a protein specified by its UniProt id.
     * @param uniprot
     * @return
     */
    public List<ChEMBLActivity> queryChEBMLActitiesForId(String uniprotId);
    
    public List<ChEMBLActivity> queryChEMBLActivitesForIds(Collection<String> uniprotIds);

}
