package jq.steel.cs.services.cust.api.vo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: CrmStatisticsValue
 * @Description: 统计
 * @Author: lirunze
 * @CreateDate: 2018/8/20 21:05
 */
public class CrmStatisticsValue {
    // 答题人
    private String recordByName;

    // 记录
    private List<Map<String, Integer>> records;

    // 总分
    private Integer recordValue;

    // 平均分
    private String average;

    // 满意度 1-满意 2-基本满意 3-不满意
    private Integer satisfaction;

    public String getRecordByName() {
        return recordByName;
    }

    public void setRecordByName(String recordByName) {
        this.recordByName = recordByName;
    }

    public List<Map<String, Integer>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, Integer>> records) {
        this.records = records;
    }

    public Integer getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(Integer recordValue) {
        this.recordValue = recordValue;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }
}
