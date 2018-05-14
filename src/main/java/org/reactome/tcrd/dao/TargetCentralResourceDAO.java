package org.reactome.tcrd.dao;

import java.util.Collection;
import java.util.List;

import org.reactome.tcrd.model.ChEMBLActivity;

public interface TargetCentralResourceDAO {
    
    /**
     * Query ChEBMLActvities for a protein specified by its UniProt id.
     * @param uniprot
     * @return
     */
    public List<ChEMBLActivity> queryChEBMLActitiesForId(String uniprot);
    
    public List<ChEMBLActivity> queryChEMBLActivitesForIds(Collection<String> uniprotIds);
    
}
