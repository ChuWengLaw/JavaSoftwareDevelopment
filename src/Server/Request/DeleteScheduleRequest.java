package Server.Request;
import java.io.Serializable;

public class DeleteScheduleRequest implements Serializable {
    private String ScheduledName;
    private String ScheduledTime;

    public DeleteScheduleRequest(String ScheduledName, String ScheduledTime) {
        this.ScheduledName = ScheduledName;
        this.ScheduledTime = ScheduledTime;
    }
    public String getScheduledName() {
        return ScheduledName;
    }
    public String getScheduledTime() { return ScheduledTime; }
}