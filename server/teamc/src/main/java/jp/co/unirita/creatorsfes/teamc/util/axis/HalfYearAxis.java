package jp.co.unirita.creatorsfes.teamc.util.axis;

import jp.co.unirita.creatorsfes.teamc.model.Record;

public class HalfYearAxis extends Axis {

    public HalfYearAxis() {
        super("halfYearId");
    }

    @Override
    public void classify(Record record) {
        int month = record.getParamAsInt("month");
        if(4 <= month && month <= 9) {
            record.setParam(getAxisName(), "1");
        } else {
            record.setParam(getAxisName(), "2");
        }
    }
}