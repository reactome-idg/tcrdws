package org.reactome.tcrd.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;
import org.reactome.tcrd.model.Activity;
import org.reactome.tcrd.model.ChEMBLActivity;
import org.reactome.tcrd.model.DrugActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WSTests {
    
    protected final String HOST_URL = "http://localhost:8060/tcrdws/";
    protected final String HTTP_POST = "Post";
    protected final String HTTP_GET = "Get";

    @Test
    public void testlistTDarkProteins() throws Exception {
        String url = HOST_URL + "tdark/uniprots";
        System.out.println(url);
        String rtn = callHttp(url, HTTP_GET, null);
        outputJSON(rtn);
    }
    
    @Test
    public void testListExpressionTypes() throws Exception {
        String url = HOST_URL + "expressionTypes";
        System.out.println(url);
        String rtn = callHttp(url, HTTP_GET, null);
        outputJSON(rtn);
    }
    
    @Test
    public void testListTissuesForExpressionType() throws Exception {
        String etype = "JensenLab Experiment HPA";
        etype = URLEncoder.encode(etype, "utf-8");
        String url = HOST_URL + "tissues/" + etype;
        System.out.println(url);
        String rtn = callHttp(url, HTTP_GET, null);
        outputJSON(rtn);
    }
    
    @Test
    public void testQueryChEMLActivities() throws Exception {
        testQueryActivities(ChEMBLActivity.class);
    }
    
    @Test
    public void testQueryDrugActivities() throws Exception {
        testQueryActivities(DrugActivity.class);
    }
    
    @Test
    public void testQueryProteinExpressions() throws Exception {
        String ids = "P00533,P10721"; // EGFR and KIT
        String tissues = "Bone,Bone marrow";
        String etypes = "JensenLab Experiment HPA,UniProt Tissue";
        String query = ids + "\n" + tissues + "\n" + etypes;
        String url = HOST_URL + "expressions/uniprots";
        String rtn = callHttp(url, HTTP_POST, query);
        System.out.println(url + "\n" + rtn);
        // Special check for gtex since it uses a dedicated table
        tissues = "Brain - Cortex,Brain - Hypothalamus";
        etypes = "GTEx";
        query = ids + "\n" + tissues + "\n" + etypes;
        rtn = callHttp(url, HTTP_POST, query);
        System.out.println("\nSpecial test for GTEx:\n" + rtn);
        ids = "Q6PGQ7"; // BORA in https://idg.reactome.org/PathwayBrowser/#/R-HSA-453274&SEL=R-HSA-3000330&PATH=R-HSA-1640170,R-HSA-69278
        etypes = "Consensus";
        tissues = "Blood and immune system,Digestive Tract";
        query = ids + "\n" + tissues + "\n" + etypes;
        rtn = callHttp(url, HTTP_POST, query);
        System.out.println("\nConsensus for BORA:\n" + rtn);
    }
    
    @Test
    public void testQueryProteinTargetLevel() throws Exception {
        // Test Get for one single UniProt id
        String uniProtId = "P00533";
        String url = HOST_URL + "expressions/uniprots";
        String etype = "Target Development Level";
        String query = uniProtId + "\nnull\n" + etype;
        String rtn = callHttp(url, HTTP_POST, query);
        System.out.println("\n" + url);
        outputJSON(rtn);
        // Test Post for two UniProt ids, delimited by ",".
        String ids = "P00533,P10721"; // EGFR and KIT
        query = ids + "\nnull\n" + etype;
        rtn = callHttp(url, HTTP_POST, query);
        System.out.println("\n" + url);
        outputJSON(rtn);
        ids = "Q09472,Q13951,Q13761,Q9Y6Y1,Q13148,Q9Y2G8,Q9P258,Q5TF58";
        String[] tokens = ids.split(",");
        System.out.println("Total ids: " + tokens.length);
        query = ids + "\nnull\n" + etype;
        rtn = callHttp(url, HTTP_POST, query);
        System.out.println("\n" + url);
        outputJSON(rtn);
    }
    
    @Test
	public void testQueryAllProteinTargetLevels() throws Exception {
		String url = HOST_URL +"targetLevel/all-uniprots";
		System.out.println(url);
		String rtn = callHttp(url, HTTP_GET, null);
		outputJSON(rtn);
	}
    
    private <T extends Activity> void testQueryActivities(Class<T> cls) throws Exception {
        String type = null;
        if (cls == ChEMBLActivity.class)
            type = "chembl";
        else if (cls == DrugActivity.class)
            type = "drug";
        if (type == null)
            throw new IllegalArgumentException(cls + " is not supported!");
        // Check GET for one UniProt id
        String url = HOST_URL + type + "/uniprot/";
        // EGFR
        String uniprotId = "P24385";
        System.out.println(url + uniprotId);
        String rtn = callHttp(url + uniprotId, HTTP_GET, "");
        outputJSON(rtn);
        
        // CHECK POST for more than one UniProt ids
        String ids = "P00533,P10721"; // EGFR and KIT
        url = HOST_URL + type + "/uniprots";
        rtn = callHttp(url, HTTP_POST, ids);
        System.out.println("\n" + url);
        outputJSON(rtn);
        
        // Check GET for one gene
        url = HOST_URL + type + "/gene/EGFR";
        System.out.println("\n" + url);
        rtn = callHttp(url, HTTP_GET, "");
        outputJSON(rtn);
        
        // Gene POST for more than one gene
        url = HOST_URL + type + "/genes";
        String genes = "EGFR,KIT,PPP2R2A";
        System.out.println(url);
        rtn = callHttp(url, HTTP_POST, genes);
        outputJSON(rtn);
    }
    
    private void outputJSON(String json) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    }
    
    protected String callHttp(String url,
            String type,
            String query) throws IOException {
        HttpMethod method = null;
        HttpClient client = null;
        if (type.equals(HTTP_POST)) {
            method = new PostMethod(url);
            client = initializeHTTPClient((PostMethod) method, query);
        } else {
            method = new GetMethod(url); // Default
            client = new HttpClient();
        }
        method.setRequestHeader("Accept", "application/json");
        int responseCode = client.executeMethod(method);
        if (responseCode == HttpStatus.SC_OK) {
            InputStream is = method.getResponseBodyAsStream();
            return readMethodReturn(is);
        } else {
            System.err.println("Error from server: " + method.getResponseBodyAsString());
            System.out.println("Response code: " + responseCode);
            throw new IllegalStateException(method.getResponseBodyAsString());
        }
    }
    
    protected String readMethodReturn(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
            builder.append(line).append("\n");
        reader.close();
        isr.close();
        is.close();
        // Remove the last new line
        String rtn = builder.toString();
        // Just in case an empty string is returned
        if (rtn.length() == 0)
            return rtn;
        return rtn.substring(0, rtn.length() - 1);
    }
    
    private HttpClient initializeHTTPClient(PostMethod post, String query) throws UnsupportedEncodingException {
        RequestEntity entity = new StringRequestEntity(query, "text/plain", "UTF-8");
//        RequestEntity entity = new StringRequestEntity(query, "application/XML", "UTF-8");
        post.setRequestEntity(entity);
//        post.setRequestHeader("Accept", "application/JSON, application/XML, text/plain");
              post.setRequestHeader("Accept", "application/json");
        HttpClient client = new HttpClient();
        return client;
    }

}
