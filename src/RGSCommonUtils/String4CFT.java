package RGSCommonUtils;

import java.util.HashMap;

/**
 * Created by p.chavdarov on 13/01/2017.
 */
public class String4CFT {

    static public String setPar(String target, String key, String value){
        if (target.isEmpty())
            target = "";
        return target + "^~" + key + "~" + value + "~^";
    }

    static public String getPar(String source, String key){
        String[] parts = source.split("\\^~");

        for (Integer i = 0; i < parts.length; i++) {
            if (parts [i].isEmpty()) continue;
            String[] keyVal = parts [i].split ("~");
            if (keyVal.length == 0) continue;
            if(keyVal[0].equals(key))
                return keyVal[1];
        }
        return null;
    }

    static public HashMap<String, String> getMap(String source){
        HashMap<String, String> result = new HashMap<String, String>();
        result.clear();

        if (null == source) return result;

        String[] parts = source.split("\\^~");

        for (Integer i = 0; i < parts.length; i++) {
            if (parts [i].isEmpty()) continue;

            String[] keyVal = parts [i].split ("~");

            if (keyVal.length == 0) continue;

            result.put(keyVal[0], keyVal[1]);
        }
        return result;
    }

}