package bjtu.pt.easycontracts.utils;

public class ReturnObject<T> {
    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;
    private T object;
    public ReturnObject(int code, T object)
    {
        this.object = object;
        this.code = code;
    }

}
