package Server.Request;

import javax.swing.*;
import java.io.Serializable;

public class ScheduleBillboardRequest implements Serializable {
    private String BillboardName, ScheduledTime;
    private int Duration, ReoccurType, ReoccurAmount;

    public ScheduleBillboardRequest(String BillboardName, String ScheduledTime, String Duration,
                                    String ReoccurType, String ReoccurAmount) {
        this.BillboardName = BillboardName;
        this.ScheduledTime = ScheduledTime;
        this.Duration = Integer.parseInt(Duration);
        this.ReoccurType = Integer.parseInt(ReoccurType);
        this.ReoccurAmount = Integer.parseInt(ReoccurAmount);
    }

    public String getBillboardName() {
        return BillboardName;
    }

    public String getScheduledTime() {
        return ScheduledTime;
    }

    public int getDuration() {
        return Duration;
    }

    public int getReoccurType() {
        return ReoccurType;
    }

    public int getReoccurAmount() {
        return ReoccurAmount;
    }

}
