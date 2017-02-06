package RGSCommonUtils;

/**
 * Created by p.chavdarov on 20/01/2017.
 */
public class Entity {

    protected static final String url = "https://iitcloud-demo.iitrust.ru";

    protected static ConnectionInterface httpConn;
    protected String method;
    protected String uri;
    protected String url_str;
    protected static String SessionToken;

//    IitEntity() {
//        if (this.gson == null)
//            this.gson = new Gson();
//    }
//    IitEntity(String token) {
//        if (this.gson == null)
//            this.gson = new Gson();
//        this.setSessionToken(token);
//    }

    public static void Init(){
    }

}
