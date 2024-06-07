package github.Zcy19980412.core;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回类
 * @author calvin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {

    private boolean success;

    private T message;

    public static <R> BaseResponse<R> success(R message) {
        BaseResponse<R> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(true);
        baseResponse.setMessage(message);
        return baseResponse;
    }

    public static <R> BaseResponse<R> success() {
        BaseResponse<R> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(true);
        baseResponse.setMessage(null);
        return baseResponse;
    }

    public static <R> BaseResponse<R> fail(R message) {
        BaseResponse<R> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(false);
        baseResponse.setMessage(message);
        return baseResponse;
    }

}
