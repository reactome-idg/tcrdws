package org.reactome.tcrd.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TargetCentralResourceDAOImp implements TargetCentralResourceDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public TargetCentralResourceDAOImp() {
    }

    @Override
    public List<ChEMBLActivity> queryChEBMLActitiesForId(String uniprot) {
        Session session = sessionFactory.getCurrentSession();
        List<ChEMBLActivity> activities = session.createQuery("SELECT a FROM ChEMBLActivity a WHERE a.target.protein.uniprot = :uniprot", ChEMBLActivity.class)
                                                 .setParameter("uniprot", uniprot)
                                                 .getResultList();
        return activities;
    }

    @Override
    public List<ChEMBLActivity> queryChEMBLActivitesForIds(Collection<String> uniprotIds) {
        Session session = sessionFactory.getCurrentSession();
        List<ChEMBLActivity> activities = session.createQuery("SELECT a FROM ChEMBLActivity a WHERE a.target.protein.uniprot in :uniprot", ChEMBLActivity.class)
                .setParameter("uniprot", uniprotIds)
                .getResultList();
        return activities;
    }

}
