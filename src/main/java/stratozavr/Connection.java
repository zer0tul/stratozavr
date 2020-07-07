package stratozavr;

import com.imperva.ddc.core.query.Endpoint;

public class Connection {
    public static String serverIP;
    public static String loginName;
    public static String loginPass;
    private static Endpoint endpoint;


    public static Endpoint createEndpoint(String host, int port, String userName, String pass, boolean isSecured) {
        Endpoint endpoint = new Endpoint();
        endpoint.setSecuredConnection(isSecured);
        endpoint.setPort(port);
        endpoint.setHost(host);
        endpoint.setPassword(pass);
        endpoint.setUserAccountName(userName);
        Connection.endpoint = endpoint;
        return endpoint;
    }

    public static Endpoint getEndpoint() {
        return Connection.endpoint;
    }


}
