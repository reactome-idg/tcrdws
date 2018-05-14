package org.reactome.tcrd.main;

import java.util.logging.Logger;

import org.reactome.tcrd.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Use this class for generating the database.
 * @author wug
 *
 */
public class MainApp {
    private static final Logger logger = Logger.getLogger(MainApp.class.getName());
    
    /**
     * Run this method to load all interactions into the database.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        context.close();
     }

}
