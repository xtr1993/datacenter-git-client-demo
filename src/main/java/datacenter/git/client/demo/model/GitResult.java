package datacenter.git.client.demo.model;

public class GitResult<T> {

    private int code;

    private T message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"message\":")
                .append(message);
        sb.append('}');
        return sb.toString();
    }
}
