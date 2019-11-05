package org.reactome.tcrd.service;

import java.util.Collection;
import java.util.List;

import org.reactome.tcrd.dao.TargetCentralResourceDAO;
import org.reactome.tcrd.model.Activity;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
import org.reactome.tcrd.model.Expression;
import org.reactome.tcrd.model.ProteinTargetDevLevel;
import org.reactome.tcrd.model.TissueExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TargetCentralResourceServiceImp implements TargetCentralResourceService {
    @Autowired
    private TargetCentralResourceDAO tcrdDAO;
    
    public TargetCentralResourceServiceImp() {
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitiesForId(String uniprotId) {
        return tcrdDAO.queryChEBMLActitiesForId(uniprotId);
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitiesForIds(Collection<String> uniprotIds) {
        return tcrdDAO.queryChEMBLActivitesForIds(uniprotIds);
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitiesForGene(String gene) {
        return tcrdDAO.queryChEMBLActivitiesForGene(gene);
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitiesForGenes(Collection<String> genes) {
        return tcrdDAO.queryChEMBLActivitiesForGenes(genes);
    }

    @Override
    public List<DrugActivity> queryDrugActivitiesForId(String uniprot) {
        return tcrdDAO.queryDrugActivitiesForId(uniprot);
    }

    @Override
    public List<DrugActivity> queryDrugActivitiesForIds(Collection<String> uniprotIds) {
        return tcrdDAO.queryDrugActivitiesForIds(uniprotIds);
    }

    @Override
    public List<DrugActivity> queryDrugActivitiesForGene(String gene) {
        return tcrdDAO.queryDrugActivitiesForGene(gene);
    }

    @Override
    public List<DrugActivity> queryDrugActivitiesForGenes(Collection<String> genes) {
        return tcrdDAO.queryDrugActivitiesForGenes(genes);
    }
    
    @Override
    public ProteinTargetDevLevel queryProteinTargetLevel(String uniProt) {
        return tcrdDAO.queryProteinTargetLevel(uniProt);
    }
    
    @Override
    public List<ProteinTargetDevLevel> queryProteinTargetLevels(Collection<String> uniProts) {
        return tcrdDAO.queryProteinTargetLevels(uniProts);
    }

	@Override
	public List<TissueExpression> queryExpression(String uniProt) {
		return tcrdDAO.queryExpression(uniProt);
	}

	@Override
	public List<TissueExpression> queryExpressions(Collection<String> ids) {
		return tcrdDAO.queryExpressions(ids);
	}
	
	
}
