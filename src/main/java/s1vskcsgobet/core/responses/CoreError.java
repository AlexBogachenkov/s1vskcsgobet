package s1vskcsgobet.core.responses;

import lombok.Data;

@Data
public class CoreError {

    private String field;
    private String message;

    public CoreError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
