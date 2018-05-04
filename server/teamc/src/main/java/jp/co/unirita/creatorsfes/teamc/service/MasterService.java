package jp.co.unirita.creatorsfes.teamc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.co.unirita.creatorsfes.teamc.cache.MasterCache;
import jp.co.unirita.creatorsfes.teamc.model.Master;
import jp.co.unirita.creatorsfes.teamc.model.MasterData;

@Service
public class MasterService {

    public List<Master> execute(String masterName, Map<String, String> options) throws Exception {
		List<Master> list = new ArrayList<>();
		List<MasterData> srcList = MasterCache.getMasterList(masterName);
		String idKey = masterName + "Id";
		String nameKey = masterName + "Name";
		for (MasterData data : srcList) {
			boolean match = true;
			int cnt = options.keySet().size() / 2;
			for(int i = 1; i <= cnt; i++) {
				String key = options.get("opt" + i);
				String value = options.get("val" + i);
				String str = data.getParam(key);
				if(str == null || !str.equals(value)) {
					match = false;
					break;
				}
			}
			if(match) {
				list.add(new Master(data.getParam(idKey), data.getParam(nameKey)));
			}
		}
		return list;
	}
}
