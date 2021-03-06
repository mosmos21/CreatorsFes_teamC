package jp.co.unirita.creatorsfes.teamc.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.co.unirita.creatorsfes.teamc.cache.RecordCache;

@Service
public class AxisService {

    public Set<String> getAxes() throws Exception {
        return RecordCache.getRecordList().get(0).keySet();
    }

    public List<String> getValues(String axisName) throws Exception {
        Set<String> axes = getAxes();
        if(!axes.contains(axisName)) {
            throw new RuntimeException("axis " + axisName + " is not found.");
        }
        return RecordCache.getRecordList().stream()
                .map(r -> r.getParam(axisName))
                .distinct().sorted().collect(Collectors.toList());
    }
}
