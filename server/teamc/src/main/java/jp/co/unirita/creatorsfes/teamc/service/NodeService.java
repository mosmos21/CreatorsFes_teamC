package jp.co.unirita.creatorsfes.teamc.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jp.co.unirita.creatorsfes.teamc.cache.RecordCache;
import jp.co.unirita.creatorsfes.teamc.model.NodeData;

@Service
public class NodeService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public NodeData execute(Map<String, String> params, boolean isContainRecord) throws Exception {
        NodeData root = new NodeData("ROOT", null);
        root.setRecords(RecordCache.getRecordList());

        checkKeyRegex(params.keySet());
        int count = 0;
        while(params.size() > 0) {
            if(count > 9){
                throw new RuntimeException("NAZO");
            }
            root.nextAxis();
            List<String> list = new ArrayList<>();
            for(String key: params.keySet()){
                if(key.startsWith("axis" +  count)){
                    list.add(key);
                }
            }
            list.sort(Comparator.naturalOrder());
            logger.info("[execute] axis" + count + " =  " + String.join(". ", list));
            for(String key: list){
                root.addAxis(params.get(key));
            }
            list.forEach(params::remove);
            count++;
        }
        root.close(isContainRecord);
        return root;
    }

    private void checkKeyRegex(Set<String> keySet) {
        keySet.forEach(key -> {
            if(!key.matches("^axis[0-9]{2}$")){
                throw new RuntimeException("invalid key regex. key = " + key);
            }
        });
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("axis00");
        set.add("axis67");
        set.add("axis99");
        new NodeService().checkKeyRegex(set);
    }
}
