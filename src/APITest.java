import RGS_FRISBEE.FrisbeeAPI;

/**
 * Created by Павел on 26.01.2017.
 */
public class APITest {

    public static void main(String[] args){
        String xml =    "<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
                        "<Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" " +
                            "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                            "xsi:type=\"GetDirectoryRequest\" " +
                            "ServicesDetalization=\"4\" " +
                            "IncludeRegions=\"true\" " +
                            "IncludeServiceTypes=\"true\" " +
                            "IncludeContrac ts=\"true\" " +
                            "IncludeOperators=\"true\" " +
                            "IncludeCurrencies=\"true\" " +
                            "IncludeNominals=\"true\" " +
                            "xmlns =\"http://ekassir.com/eKassir/PaySystem/Server/eKassirV3Protocol\" />";

        FrisbeeAPI.SendRequest(xml);
    }
}
