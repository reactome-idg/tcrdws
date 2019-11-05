package org.reactome.tcrd.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.reactome.tcrd.model.Activity;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
import org.reactome.tcrd.model.Protein;
import org.reactome.tcrd.model.ProteinTargetDevLevel;
import org.reactome.tcrd.model.TissueExpression;
import org.reactome.tcrd.model.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TargetCentralResourceDAOImp implements TargetCentralResourceDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public TargetCentralResourceDAOImp() {
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitiesForGene(String gene) {
        return queryActivitiesForGene(gene, ChEMBLActivity.class);
    }
    
    @Override
    public List<DrugActivity> queryDrugActivitiesForGene(String gene) {
        return queryActivitiesForGene(gene, DrugActivity.class);
    }
    
    private <T extends Activity> List<T> queryActivitiesForGene(String gene, Class<T> cls) {
        Session session = sessionFactory.getCurrentSession();
        List<T> activities = session.createQuery("SELECT a FROM " + cls.getSimpleName() + " a WHERE a.target.protein.sym = :gene", cls)
                                    .setParameter("gene", gene)
                                    .getResultList();
        return activities;
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitiesForGenes(Collection<String> genes) {
        return queryActivitiesForGenes(genes, ChEMBLActivity.class);
    }
    
    @Override
    public List<DrugActivity> queryDrugActivitiesForGenes(Collection<String> genes) {
        return queryActivitiesForGenes(genes, DrugActivity.class);
    }
    
    private <T extends Activity> List<T> queryActivitiesForGenes(Collection<String> genes, Class<T> cls) {
        Session session = sessionFactory.getCurrentSession();
        List<T> activities = session.createQuery("SELECT a FROM " + cls.getSimpleName() + " a WHERE a.target.protein.sym in :syms", cls)
                .setParameter("syms", genes)
                .getResultList();
        return activities;
    }

    @Override
    public List<ChEMBLActivity> queryChEBMLActitiesForId(String uniprot) {
        return queryActivitiesForId(uniprot, ChEMBLActivity.class);
    }
    
    @Override
    public List<DrugActivity> queryDrugActivitiesForId(String uniprot) {
        return queryActivitiesForId(uniprot, DrugActivity.class);
    }
    
    private <T extends Activity> List<T> queryActivitiesForId(String uniprot, Class<T> cls) {
        Session session = sessionFactory.getCurrentSession();
        List<T> activities = session.createQuery("SELECT a FROM " + cls.getSimpleName() + " a WHERE a.target.protein.uniprot = :uniprot", cls)
                                                 .setParameter("uniprot", uniprot)
                                                 .getResultList();
        return activities;
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitesForIds(Collection<String> uniprotIds) {
        return queryActivitiesForIds(uniprotIds, ChEMBLActivity.class);
    }
    
    @Override
    public List<DrugActivity> queryDrugActivitiesForIds(Collection<String> uniprotIds) {
        return queryActivitiesForIds(uniprotIds, DrugActivity.class);
    }
    
    private <T extends Activity> List<T> queryActivitiesForIds(Collection<String> uniprotIds, Class<T> cls) {
        Session session = sessionFactory.getCurrentSession();
        List<T> activities = session.createQuery("SELECT a FROM " + cls.getSimpleName() + " a WHERE a.target.protein.uniprot in :uniprot", cls)
                .setParameter("uniprot", uniprotIds)
                .getResultList();
        return activities;
    }
    
    @Override
    public ProteinTargetDevLevel queryProteinTargetLevel(String uniProt) {
        List<ProteinTargetDevLevel> list = queryProteinTargetLevels(Collections.singletonList(uniProt));
        if (list.size() == 0)
            return null;
        return list.get(0); // We expect one object only.
    }
    
    @Override
    public List<ProteinTargetDevLevel> queryProteinTargetLevels(Collection<String> uniProts) {
        Session session = sessionFactory.getCurrentSession();
        List<Protein> proteins = session.createQuery("SELECT a FROM " + Protein.class.getSimpleName() + " a WHERE a.uniprot in :uniprots", Protein.class)
                .setParameter("uniprots", uniProts)
                .getResultList();
        if (proteins == null || proteins.size() == 0)
            return new ArrayList<>();
        List<ProteinTargetDevLevel> rtn = new ArrayList<>();
        for (Protein protein : proteins) {
            ProteinTargetDevLevel devLevel = new ProteinTargetDevLevel();
            // Just use the first protein
            devLevel.setUniprot(protein.getUniprot());
            devLevel.setSym(protein.getSym());
            devLevel.setTargetDevLevel(protein.getTarget().getTargetDevLevel());
            rtn.add(devLevel);
        }
        return rtn;
    }

	@Override
	public List<TissueExpression> queryExpression(String uniProt) {
		List<TissueExpression> rtn = queryExpressions(Collections.singletonList(uniProt));
		if(rtn.size() == 0)
			return null;
		return rtn;
	}

	@Override
	public List<TissueExpression> queryExpressions(Collection<String> uniProts) {
		Session session = sessionFactory.getCurrentSession();
		List<Expression> expressions = session.createQuery("SELECT a FROM " 
				+ Expression.class.getSimpleName() 
				+ " a WHERE a.etype.name IN ('HPM Protein','HCA RNA') AND a.protein.uniprot in :uniprot", Expression.class)
				.setParameter("uniprot", uniProts)
				.getResultList();
		if(expressions == null  || expressions.size()==0)
			return new ArrayList<>();
		List<TissueExpression> rtn = new ArrayList<>();
		for(Expression expression : expressions) {
			TissueExpression tissueExp = new TissueExpression();
			tissueExp.setSym(expression.getProtein().getSym());
			tissueExp.setEtype(expression.getEtype().getName());
			tissueExp.setTissue(expression.getTissue());
			tissueExp.setUniprot(expression.getProtein().getUniprot());
			tissueExp.setNumberVal(expression.getNumberValue());
			rtn.add(tissueExp);
		}
		return rtn;
	}
}
