package org.reactome.tcrd.test;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import org.reactome.tcrd.model.Activity;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
import org.reactome.tcrd.model.Protein;
import org.reactome.tcrd.model.Target;

public class HibernateTests {
    
    public HibernateTests() {
    }
    
    /**
     * Just a bunch of tests to ensure the Hibernate annotation works as expected.
     * @throws Exception
     */
    @Test
    public void testLoad() throws Exception {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metaData.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        
        // Check proteins
        TypedQuery<Protein> query = session.createQuery("FROM " + Protein.class.getName(), Protein.class);
        List<Protein> proteins = query.getResultList();
        System.out.println("Total proteins: " + proteins.size());
        Protein protein = proteins.stream().findAny().get();
        System.out.println("Protein: " + protein.getUniprot() + ", " + protein.getSym());
        Target target = protein.getTarget();
        System.out.println("Protein's target: " + target.getName() + ", " + target.getTargetDevLevel());
        
        // Check targets
        TypedQuery<Target> targetQuery = session.createQuery("FROM " + Target.class.getName(), Target.class);
        List<Target> targets = targetQuery.getResultList();
        System.out.println("\nTotal targets: " + targets.size());
        target = targets.stream().filter(t -> t.getChemblActivities().size() > 0).findAny().get();
        System.out.println("Target: " + target.getName() + ", " + target.getTargetDevLevel());
        protein = target.getProtein();
        System.out.println("Target's protein: " + protein.getUniprot() + ", " + protein.getSym());
        Set<ChEMBLActivity> chemblActivities = target.getChemblActivities();
        System.out.println("Total ChEMBLActivities: " + chemblActivities.size());
        ChEMBLActivity chemblActivity = chemblActivities.stream().findAny().get();
        if (chemblActivity != null) {
            System.out.println("One of these activites: " + 
                    chemblActivity.getCompoundChEMBLId() + ", " + 
                    chemblActivity.getActivityType() + ", " + 
                    chemblActivity.getActivityValue());
        }
        target = targets.stream().filter(t -> t.getDrugActivities().size() > 0).findAny().get();
        System.out.println("Another target: " + target.getName() + ", " + target.getTargetDevLevel());
        Collection<DrugActivity> drugActivities = target.getDrugActivities();
        System.out.println("Total drug activities: " + drugActivities);
        DrugActivity drugActivity = drugActivities.stream().findAny().get();
        if (drugActivity != null) {
            System.out.println("One of drug activities: " + 
                    drugActivity.getDrug() + ", " + 
                    drugActivity.getActionType() + ", " + 
                    drugActivity.getActivityValue());
        }
        
        // Check ChEBMLActivity
        TypedQuery<ChEMBLActivity> chemblActivityQuery = session.createQuery("FROM " + ChEMBLActivity.class.getName(), ChEMBLActivity.class);
        List<ChEMBLActivity> activities = chemblActivityQuery.getResultList();
        System.out.println("\nTotal ChEMBLActivities: " + activities.size());
        Activity activity = activities.stream().findAny().get();
        System.out.println("ChEMBLActivity: " + activity.getActivityType() + ", " + activity.getActivityValue());
        target = activity.getTarget();
        System.out.println("Its target: " + target.getName() + ", " + target.getTargetDevLevel());
        
        // Check DrugActivity
        TypedQuery<DrugActivity> drugActivityQuery = session.createQuery("FROM " + DrugActivity.class.getName(), DrugActivity.class);
        drugActivities = drugActivityQuery.getResultList();
        System.out.println("\nTotal DrugActivities: " + drugActivities.size());
        drugActivity = drugActivities.stream().findAny().get();
        System.out.println("One of DrugActivity: " + drugActivity.getDrug() + ", " +
                drugActivity.getActionType() + ", " +
                drugActivity.getActivityValue() + ", " +
                drugActivity.getNlmDrugInfo());
        target = drugActivity.getTarget();
        System.out.println("Its target: " + target.getName() + ", " + target.getTargetDevLevel());
        
        session.close();
    }

}
