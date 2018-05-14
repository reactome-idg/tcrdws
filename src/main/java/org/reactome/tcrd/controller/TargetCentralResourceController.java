package org.reactome.tcrd.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;
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
    
    @Autowired
    private TargetCentralResourceService tcrdService;
    
    public TargetCentralResourceController() {
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
