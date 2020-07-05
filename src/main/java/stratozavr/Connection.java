package stratozavr;

import com.imperva.ddc.core.query.Endpoint;

public class Connection {
    public static String serverIP;
    public static String loginName;
    public static String loginPass;
    public static Endpoint createEndpoint() {
        return createEndpoint("<YOUR IP>",389,"<DOMAIN>\\<NAME>","<YOUR PASS>",false);
    }

    public static Endpoint createEndpoint(String host, int port, String userName, String pass, boolean isSecured) {
        Endpoint endpoint = new Endpoint();
        endpoint.setSecuredConnection(isSecured);
        endpoint.setPort(port);
        endpoint.setHost(host);
        endpoint.setPassword(pass);
        endpoint.setUserAccountName(userName); //* You can us the user's DistinguishedName as well
        //*endpoint.setSecondaryPort(389);
        //*endpoint.setSecondaryHost("10.100.10.100");
        //*endpoint.setSecuredConnectionSecondary(false);
        return endpoint;
    }


}
