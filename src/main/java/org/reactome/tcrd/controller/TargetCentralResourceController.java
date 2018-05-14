package org.reactome.tcrd.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.reactome.tcrd.model.ChEMBLActivity;
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
    public List<ChEMBLActivity> queryChEBMLActitiesForId(@PathVariable("uniprotId") String uniprotId) {
        List<ChEMBLActivity> activities = tcrdService.queryChEBMLActitiesForId(uniprotId);
        return activities;
    }
    
    /**
     * Text should contain a set of UniProt ids delimited by ",".
     * @param text
     * @return
     */
    @Transactional(readOnly = true)
    @PostMapping("/chembl/uniprots")
    public List<ChEMBLActivity> queryChEBMLActitiesForIds(@RequestBody String text) {
        if (text == null || text.trim().length() == 0)
            return new ArrayList<>();
        List<String> ids = Arrays.asList(text.split(","));
        List<ChEMBLActivity> activities = tcrdService.queryChEMBLActivitesForIds(ids);
        return activities;
    }
    
    
}
