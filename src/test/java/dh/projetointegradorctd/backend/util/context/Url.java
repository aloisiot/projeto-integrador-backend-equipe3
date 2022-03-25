package dh.projetointegradorctd.backend.util.context;

public abstract class Url {
    public static String getLocalUrl(int port ,String relativePath) {
        return String.format("http://127.0.0.1:%s/%s", port, relativePath).toString();
    }
}
