package back.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;
    
    public ApiResponse(boolean success, String message, T data) {
       this.success = success;
       this.message = message;
       this.data = data;
    }
}



