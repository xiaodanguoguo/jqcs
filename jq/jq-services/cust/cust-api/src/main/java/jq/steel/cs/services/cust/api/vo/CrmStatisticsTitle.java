package jq.steel.cs.services.cust.api.vo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: CrmStatisticsTitle
 * @Description: 统计
 * @Author: lirunze
 * @CreateDate: 2018/8/20 21:05
 */
public class CrmStatisticsTitle {
    // 答题人
    private String recordByNameStr;

    // 记录
    private List<Map<String, String>> recordStr;

    // 总分
    private String recordValueStr;

    // 平均分
    private String averageStr;

    // 满意度
    private String satisfactionStr;

    public String getRecordByNameStr() {
        return recordByNameStr;
    }

    public void setRecordByNameStr(String recordByNameStr) {
        this.recordByNameStr = recordByNameStr;
    }

    public List<Map<String, String>> getRecordStr() {
        return recordStr;
    }

    public void setRecordStr(List<Map<String, String>> recordStr) {
        this.recordStr = recordStr;
    }

    public String getRecordValueStr() {
        return recordValueStr;
    }

    public void setRecordValueStr(String recordValueStr) {
        this.recordValueStr = recordValueStr;
    }

    public String getAverageStr() {
        return averageStr;
    }

    public void setAverageStr(String averageStr) {
        this.averageStr = averageStr;
    }

    public String getSatisfactionStr() {
        return satisfactionStr;
    }

    public void setSatisfactionStr(String satisfactionStr) {
        this.satisfactionStr = satisfactionStr;
    }
}
