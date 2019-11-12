package org.reactome.tcrd.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
import org.reactome.tcrd.model.ExpressionType;
import org.reactome.tcrd.model.rest.ProteinExpression;
import org.reactome.tcrd.model.rest.ProteinProperty;
import org.reactome.tcrd.model.rest.ProteinTargetDevLevel;
import org.reactome.tcrd.service.TargetCentralResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is used to provide REST service related to interaction query.
 * Writing is not supported.
 * @author wug
 *
 */
@RestController
public class TargetCentralResourceController {
    private final static Logger logger = Logger.getLogger(TargetCentralResourceController.class);
    @Autowired
    private TargetCentralResourceService tcrdService;
    
    public TargetCentralResourceController() {
    }
    
    /**
     * Query protein expression or gene expression for a set of UniProt ids,
     * tissues, and expression data types.
     * Note: This is a post call. Three lines delimited by "\n" are expected in the 
     * post text in the following order:
     * 1). UniProt ids, delimited by ",".
     * 2). Tissues, delimited by ","
     * 3). Expression types, delimited by ",".
     */
    @Transactional(readOnly = true)
    @PostMapping("/expressions/uniprots")
    public List<ProteinExpression> queryProteinExpressions(@RequestBody String text) {
        if (text == null || text.trim().length() == 0)
            return new ArrayList<>();
        String[] lines = text.split("\n");
        if (lines.length < 3) {
            logger.info("Query post body text doesn't have enough lines: " + lines.length);
            return new ArrayList<>();
        }
        Collection<String> uniProts = Arrays.asList(lines[0].split(","));
        Collection<String> tissues = Arrays.asList(lines[1].split(","));
        Collection<String> etypes = Arrays.asList(lines[2].split(","));
        return tcrdService.queryProteinExpressions(uniProts, tissues, etypes);
    }
    
    /**
     * List expression types stored in the database.
     * @return
     */
    @Transactional(readOnly = true)
    @GetMapping("/expressionTypes")
    public List<ExpressionType> listExpressionTypes() {
        return tcrdService.listExpressionTypes();
    }
    
    /**
     * List tissues for an expression type.
     * @param etype
     * @return
     */
    @Transactional(readOnly = true)
    @GetMapping("/tissues/{etype}")
    public List<String> getTissues(@PathVariable("etype") String etype) {
        try {
            etype = URLDecoder.decode(etype, "utf-8");
            return tcrdService.getTissues(etype);
        }
        catch(UnsupportedEncodingException e) {
            logger.error("getTissues: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    /**
     * This Get call should be used for one single UniProt only.
     * @param uniProt
     * @return
     */
    @Transactional(readOnly = true)
    @GetMapping("/targetlevel/uniprot/{uniprotId}")
    public ProteinProperty queryProteinTargetLevel(@PathVariable("uniprotId") String uniProt) {
        return tcrdService.queryProteinTargetLevel(uniProt);
    }
    
    /**
     * Text should contain a set of UniProt ids delimited by ",".
     * @param text
     * @return
     */
    @Transactional(readOnly = true)
    @PostMapping("/targetlevel/uniprots")
    public List<ProteinTargetDevLevel> queryProteinTargetLevels(@RequestBody String text) {
        if (text == null || text.trim().length() == 0)
            return new ArrayList<>();
        List<String> ids = Arrays.asList(text.split(","));
        return tcrdService.queryProteinTargetLevels(ids);
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/chembl/uniprot/{uniprotId}")
    public List<ChEMBLActivity> queryChEMBLActivitiesForId(@PathVariable("uniprotId") String uniprotId) {
        List<ChEMBLActivity> activities = tcrdService.queryChEMBLActivitiesForId(uniprotId);
        return activities;
    }
    
    /**
     * Text should contain a set of UniProt ids delimited by ",".
     * @param text
     * @return
     */
    @Transactional(readOnly = true)
    @PostMapping("/chembl/uniprots")
    public List<ChEMBLActivity> queryChEMBLActivitiesForIds(@RequestBody String text) {
        if (text == null || text.trim().length() == 0)
            return new ArrayList<>();
        List<String> ids = Arrays.asList(text.split(","));
        List<ChEMBLActivity> activities = tcrdService.queryChEMBLActivitiesForIds(ids);
        return activities;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/chembl/gene/{gene}")
    public List<ChEMBLActivity> queryChEMBLActivitiesForGene(@PathVariable("gene") String gene) {
        List<ChEMBLActivity> activities = tcrdService.queryChEMBLActivitiesForGene(gene);
        return activities;
    }
    
    /**
     * Text should contain a set of UniProt ids delimited by ",".
     * @param text
     * @return
     */
    @Transactional(readOnly = true)
    @PostMapping("/chembl/genes")
    public List<ChEMBLActivity> queryChEMBLActivitiesForGenes(@RequestBody String text) {
        if (text == null || text.trim().length() == 0)
            return new ArrayList<>();
        List<String> genes = Arrays.asList(text.split(","));
        List<ChEMBLActivity> activities = tcrdService.queryChEMBLActivitiesForGenes(genes);
        return activities;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/drug/uniprot/{uniprotId}")
    public List<DrugActivity> queryDrugActivitiesForId(@PathVariable("uniprotId") String uniprotId) {
        List<DrugActivity> activities = tcrdService.queryDrugActivitiesForId(uniprotId);
        return activities;
    }
    
    /**
     * Text should contain a set of UniProt ids delimited by ",".
     * @param text
     * @return
     */
    @Transactional(readOnly = true)
    @PostMapping("/drug/uniprots")
    public List<DrugActivity> queryDrugActivitiesForIds(@RequestBody String text) {
        if (text == null || text.trim().length() == 0)
            return new ArrayList<>();
        List<String> ids = Arrays.asList(text.split(","));
        List<DrugActivity> activities = tcrdService.queryDrugActivitiesForIds(ids);
        return activities;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/drug/gene/{gene}")
    public List<DrugActivity> queryDrugActivitiesForGene(@PathVariable("gene") String gene) {
        List<DrugActivity> activities = tcrdService.queryDrugActivitiesForGene(gene);
        return activities;
    }
    
    /**
     * Text should contain a set of UniProt ids delimited by ",".
     * @param text
     * @return
     */
    @Transactional(readOnly = true)
    @PostMapping("/drug/genes")
    public List<DrugActivity> queryChEBMLActivitiesForGenes(@RequestBody String text) {
        if (text == null || text.trim().length() == 0)
            return new ArrayList<>();
        List<String> genes = Arrays.asList(text.split(","));
        List<DrugActivity> activities = tcrdService.queryDrugActivitiesForGenes(genes);
        return activities;
    }
    
    
}
