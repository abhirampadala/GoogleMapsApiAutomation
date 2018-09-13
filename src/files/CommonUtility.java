package files;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class CommonUtility {
	
	public JsonPath rawtoJson(Response r) {
		String res = r.asString();
		JsonPath js = new JsonPath(res);
		return js;
	}
	
	public XmlPath rawtoXML(Response r) {
		String res = r.asString();
		XmlPath xml = new XmlPath(res);
		return xml;
	}

}
