package jp.co.unirita.creatorsfes.teamc.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.unirita.creatorsfes.teamc.service.AxisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.creatorsfes.teamc.model.Master;
import jp.co.unirita.creatorsfes.teamc.model.NodeData;
import jp.co.unirita.creatorsfes.teamc.service.MasterService;
import jp.co.unirita.creatorsfes.teamc.service.NodeService;

@RestController
@RequestMapping(value = "/api/v1")
public class OverTimesController {

    @Autowired
    NodeService nodeService;

    @Autowired
    MasterService masterService;

    @Autowired
    AxisService axisService;
    
    @GetMapping(value = "overtimes")
    @ResponseStatus(HttpStatus.OK)
    public NodeData getOvertimes(@RequestParam Map<String, String> params) throws Exception{
        return nodeService.execute(params, false);
    }

    @GetMapping(value = "overtimes/detail")
    @ResponseStatus(HttpStatus.OK)
    public NodeData getDetail(@RequestParam Map<String, String> params) throws Exception {
        return nodeService.execute(params, true);
    }

    @GetMapping(value = "/masters/{masterName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Master> getMasterData(
            @PathVariable(value = "masterName")String masterName,
            @RequestParam Map<String, String> options
    ) throws Exception {
        return masterService.execute(masterName, options);
    }

    @GetMapping(value = "axes")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getKeySet() throws Exception {
        return axisService.getAxes();
    }

    @GetMapping(value = "axes/{axisName}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAxisValues(@PathVariable(value = "axisName")String axisName) throws Exception {
        return axisService.getValues(axisName);
    }
}
