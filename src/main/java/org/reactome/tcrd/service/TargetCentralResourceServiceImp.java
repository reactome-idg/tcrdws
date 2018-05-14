package org.reactome.tcrd.service;

import java.util.Collection;
import java.util.List;

import org.reactome.tcrd.dao.TargetCentralResourceDAO;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TargetCentralResourceServiceImp implements TargetCentralResourceService {
    @Autowired
    private TargetCentralResourceDAO tcrdDAO;
    
    public TargetCentralResourceServiceImp() {
    }

    @Override
    public List<ChEMBLActivity> queryChEBMLActitiesForId(String uniprotId) {
        return tcrdDAO.queryChEBMLActitiesForId(uniprotId);
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitesForIds(Collection<String> uniprotIds) {
        return tcrdDAO.queryChEMBLActivitesForIds(uniprotIds);
    }

}
