package Server.Request;

import Server.SessionToken;

import javax.swing.*;
import java.io.Serializable;

public class ScheduleBillboardRequest implements Serializable {
    private String BillboardName, ScheduledTime, UserName;
    private int Duration, ReoccurType, ReoccurAmount;
    private SessionToken sessionToken;

    public ScheduleBillboardRequest(String BillboardName, String ScheduledTime, String Duration,
                                    String ReoccurType, String ReoccurAmount, SessionToken sessionToken, String UserName) {
        this.BillboardName = BillboardName;
        this.ScheduledTime = ScheduledTime;
        this.Duration = Integer.parseInt(Duration);
        this.ReoccurType = Integer.parseInt(ReoccurType);
        this.ReoccurAmount = Integer.parseInt(ReoccurAmount);
        this.sessionToken = sessionToken;
        this.UserName = UserName;
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

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public String getUserName() {
        return UserName;
    }
}
