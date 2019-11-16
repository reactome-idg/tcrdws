package org.reactome.tcrd.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.apache.commons.collections15.map.HashedMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.Test;
import org.reactome.tcrd.config.AppConfig;
import org.reactome.tcrd.model.ExpressionType;
import org.reactome.tcrd.model.GTEx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Use this class for generating the database.
 * @author wug
 *
 */
public class MainApp {
    //    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    /**
     * Run this method to test the application configuration when it is run in a standalone mode.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SessionFactory sf = context.getBean(SessionFactory.class);
        // Need to open a session without using a transaction
        Session session = sf.openSession();
//        generateExpressionTypeTissues(session);
        appendGTExTissues(session);
        session.close();
        context.close();
    }

    /**
     * Use this method to generate a file for expression type and tissues. This file
     * will be used for the web service.
     * @param session
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static void generateExpressionTypeTissues(Session session) throws Exception {
        // Fixed file names
        String fileName = "src/main/resources/expression_type_tisses.txt";
        PrintWriter writer = new PrintWriter(fileName);
        // Get all expression types
        TypedQuery<ExpressionType> query = session.createQuery("FROM " + ExpressionType.class.getName(),
                                                               ExpressionType.class);
        List<ExpressionType> types = query.getResultList();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT DISTINCT tissue FROM expression WHERE etype = ?");
        for (ExpressionType type : types) {
            nativeQuery.setParameter(1, type.getName());
            List<String> tissues = nativeQuery.list();
            Collections.sort(tissues);
            for (String tissue : tissues)
                writer.println(type.getName() + "\t" + tissue);
            //            break;
        }
        writer.close();
    }

    private static void appendGTExTissues(Session session) throws Exception {
        String fileName = "src/main/resources/expression_type_tisses.txt";
        FileWriter fileWriter = new FileWriter(fileName, true);
        PrintWriter writer = new PrintWriter(fileWriter);
        // Get all expression types
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT DISTINCT tissue FROM gtex");
        List<String> tissues = nativeQuery.list();
        Collections.sort(tissues);
        for (String tissue : tissues)
            writer.println("GTEx\t" + tissue);
        writer.close();
        fileWriter.close();
    }
    
    @Test
    public void checkExpressionTypeTissues() throws IOException {
        String fileName = "src/main/resources/expression_type_tisses.txt";
        Map<String, Set<String>> typeToTissues = new HashedMap<>();
        Files.lines(Paths.get(fileName))
             .map(line -> line.split("\t"))
             .forEach(tokens -> {
                 typeToTissues.compute(tokens[0], (key, set) -> {
                     if (set == null)
                         set = new HashSet<>();
                     set.add(tokens[1]);
                     return set;
                 });
             });
        typeToTissues.keySet().stream().sorted().forEach(type -> {
            System.out.println(type + ": " + typeToTissues.get(type).size());
        });
    }

}
