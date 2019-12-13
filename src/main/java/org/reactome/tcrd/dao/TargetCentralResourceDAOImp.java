package org.reactome.tcrd.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.map.HashedMap;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.reactome.tcrd.model.Activity;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
import org.reactome.tcrd.model.Expression;
import org.reactome.tcrd.model.ExpressionType;
import org.reactome.tcrd.model.GTEx;
import org.reactome.tcrd.model.Protein;
import org.reactome.tcrd.model.rest.ProteinExpression;
import org.reactome.tcrd.model.rest.ProteinTargetDevLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TargetCentralResourceDAOImp implements TargetCentralResourceDAO {
    private static final Logger logger = Logger.getLogger(TargetCentralResourceDAOImp.class);
    private static final String TARGET_DEV_LEVEL = "Target Development Level";
    
    @Autowired
    private SessionFactory sessionFactory;
    // Cached values
    private Map<String, List<String>> etypeToTissues;
    
    public TargetCentralResourceDAOImp() {
    }
    
    @Override
    public List<String> getTissues(String etype) {
        if (etypeToTissues == null) 
            loadEtypeToTissues();
        List<String> rtn = null;
        if (etypeToTissues != null)
            rtn = etypeToTissues.get(etype);
        if (rtn == null)
            rtn = new ArrayList<>();
        return rtn; // Make sure we have returned something even though it is empty to avoid null exception on the server-side.
    }
    
    private void loadEtypeToTissues() {
        etypeToTissues = new HashedMap<>();
        // Hard-coded for a pre-generated file dumped from the TCRD database
        String fileName = "expression_type_tisses.txt"; 
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");
                etypeToTissues.compute(tokens[0], (key, list) -> {
                    if (list == null)
                        list = new ArrayList<>();
                    list.add(tokens[1]);
                    return list;
                });
            }
            br.close();
            reader.close();
            is.close();
        }
        catch(IOException e) {
            logger.error("Error in loadEtypeToTissues: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<ExpressionType> listExpressionTypes() {
        Session session = sessionFactory.getCurrentSession();
        List<ExpressionType> types = session.createQuery("FROM " + ExpressionType.class.getSimpleName(), ExpressionType.class)
                                            .getResultList();
        // This is a hack so that target development level can be listed together with
        // other data soruces
        ExpressionType targetLevelType = new ExpressionType();
        targetLevelType.setDataType("String");
        targetLevelType.setName(TARGET_DEV_LEVEL);
        targetLevelType.setDescription("TCRD target development level");
        types.add(0, targetLevelType);
        // Hard-code this for 
        for (ExpressionType etype : types) {
            if (etype.getName().equals("JensenLab Experiment HPA")) {
                etype.setDataType("Number");
            }
            else if (etype.getName().equals("GTEx")) {
                etype.setHasGender(Boolean.TRUE);
            }
        }
        return types;
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
    public List<ProteinExpression> queryProteinExpressions(Collection<String> uniProts,
                                                           Collection<String> tissues,
                                                           Collection<String> etypes) {
        Session session = sessionFactory.getCurrentSession();
        // Perform something special for GTEx
        List<ProteinExpression> gteExpressions = null;
        // In case etypes cannot be modified
        List<String> etypesList = new ArrayList<>(etypes);
        List<ProteinExpression> targetDevList = null;
        for (Iterator<String> it = etypesList.iterator(); it.hasNext();) {
            String etype = it.next();
            if (etype.equals("GTEx")) {
                it.remove();
                gteExpressions = queryGTExExpression(uniProts, tissues, session);
            }
            else if (etype.equals(TARGET_DEV_LEVEL)) {
                it.remove();
                targetDevList = queryProteinTargetLevels(uniProts, session);
            }
        }
        List<ProteinExpression> rtn = new ArrayList<>();
        if (etypesList.size() > 0) {
            List<Expression> expressions = session.createQuery("SELECT e FROM " + Expression.class.getSimpleName() +
                                                               " e WHERE e.protein.uniprot in :uniprots " + 
                                                               " AND e.tissue in :tissues " + 
                                                               " AND e.etype.name in :etypes",
                                                               Expression.class)
                    .setParameter("uniprots", uniProts)
                    .setParameter("tissues", tissues)
                    .setParameter("etypes", etypesList)
                    .getResultList();
            for (Expression expression : expressions) {
                ProteinExpression pe = new ProteinExpression();
                pe.setUniprot(expression.getProtein().getUniprot());
                pe.setSym(expression.getProtein().getSym());
                pe.setBooleanValue(expression.getBooleanValue());
                pe.setEtype(expression.getEtype().getName());
                if (expression.getEtype().getName().equals("JensenLab Experiment HPA")) 
                    pe.setNumberValue(expression.getConf());
                else {
                    pe.setNumberValue(expression.getNumberValue());
                    pe.setStringValue(expression.getStringValue());
                    pe.setQualValue(expression.getQualValue());
                }
                pe.setTissue(expression.getTissue());
                rtn.add(pe);
            }
        }
        if (gteExpressions != null)
            rtn.addAll(gteExpressions);
        if (targetDevList != null)
            rtn.addAll(targetDevList);
        return rtn;
    }
    
    /**
     * Return protein target levels as ProteinExpression objects for easy integration at the client side.
     * @param uniProts
     * @param session
     * @return
     */
    private List<ProteinExpression> queryProteinTargetLevels(Collection<String> uniProts,
                                                             Session session) {
        List<Protein> proteins = session.createQuery("SELECT a FROM " + Protein.class.getSimpleName() + " a WHERE a.uniprot in :uniprots", Protein.class)
                .setParameter("uniprots", uniProts)
                .getResultList();
        if (proteins == null || proteins.size() == 0)
            return new ArrayList<>();
        List<ProteinExpression> rtn = new ArrayList<>();
        for (Protein protein : proteins) {
            ProteinExpression expression = new ProteinExpression();
            // Just use the first protein
            expression.setUniprot(protein.getUniprot());
            expression.setSym(protein.getSym());
            expression.setQualValue(protein.getTarget().getTargetDevLevel());
            rtn.add(expression);
        }
        return rtn;
    }
    
    private List<ProteinExpression> queryGTExExpression(Collection<String> uniProts,
                                                        Collection<String> tissues,
                                                        Session session) {
        List<GTEx> expressions = session.createQuery("SELECT e FROM " + GTEx.class.getSimpleName() +
                                                     " e WHERE e.protein.uniprot in :uniprots " + 
                                                     " AND e.tissue in :tissues",
                                                     GTEx.class)
                                     .setParameter("uniprots", uniProts)
                                     .setParameter("tissues", tissues)
                                     .getResultList();
        List<ProteinExpression> rtn = new ArrayList<>();
        for (GTEx expression : expressions) {
            ProteinExpression pe = new ProteinExpression();
            pe.setUniprot(expression.getProtein().getUniprot());
            pe.setSym(expression.getProtein().getSym());
            pe.setEtype("GTEx");
            pe.setNumberValue(expression.getTpm());
            pe.setTissue(expression.getTissue());
            pe.setGender(expression.getGender());
            rtn.add(pe);
        }
        return rtn;
    }

}
