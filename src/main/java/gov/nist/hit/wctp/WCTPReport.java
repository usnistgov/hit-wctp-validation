package gov.nist.hit.wctp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nist.validation.report.Entry;
import gov.nist.validation.report.Report;

/**
 * Created by nc on 09/08/22.
 */
public class WCTPReport implements Report{

    List<Entry> schema;
    List<Entry> content;

    public WCTPReport() {
        this.schema = new ArrayList<>();
        this.content = new ArrayList<>();
    }

    @Override
    public Map<String, List<Entry>> getEntries() {
        HashMap<String,List<Entry>> map = new HashMap<>();
        map.put("structure", schema);
        map.put("content", content);
        return map;
    }

    @Override
    public String toJson() throws Exception {
        return gov.nist.validation.report.impl.JsonObjectMapper.mapper.writeValueAsString(this);
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder();
        if(schema.size()>0) {
            sb.append("\n########  schema check: ");
            sb.append(schema.size());
            sb.append("problems detected.");
            sb.append(printEntries(schema));
        }
        if(content.size()>0) {
            sb.append("\n########  content check: ");
            sb.append(content.size());
            sb.append(" problems detected.");
            sb.append(printEntries(content));
        }        
        return sb.toString();
    }

    private String printEntries(List<Entry> list){
        StringBuilder res = new StringBuilder();
        if(list.size()>0) {
            for (Entry e : list) {
                res.append("\n");
                res.append(e.toText());
            }
        }
        return res.toString();
    }

    public void addSchemaEntry(Entry e){
        this.schema.add(e);
    }
    public void addContentEntry(Entry e){
        this.content.add(e);
    }
    

}
