package hooks;

import framework.utilities.feedXMLUtil.xml.XMLUtil;
import io.cucumber.java.Before;

public class FeedXMLHooks {
    @Before(order = -1, value = "@xml")
    public void getBooks(){
        XMLUtil.instanceInitialization();
    }
}
