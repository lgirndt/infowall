package infowall.web.model;

/**
 *
 */
public class ReturnStatus {
    private String status;

    public String getStatus() {
        return status;
    }

    private ReturnStatus(String status) {
        this.status = status;
    }

    public static ReturnStatus success(){
        return new ReturnStatus("success");
    }

    public static ReturnStatus failed(){
        return new ReturnStatus("failed");
    }
}
