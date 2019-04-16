package run_my_self;

public class Response<T> {
    private T Data;

    protected Response(T data) {
        Data = data;
    }

    public T getData() {
        return Data;
    }
}
