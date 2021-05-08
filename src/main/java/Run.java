import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class Run {
    private final static Logger logger = Logger.getLogger(Run.class.getName());
    public static void main(String[] args) throws Exception {
        // Set properties
        if(System.getProperty("mode") == null) {
            logger.warn("-Dmode is NULL, use default param 'dev', alternative 'prod'");
            System.setProperty("mode", "dev");
        }
        if(System.getProperty("isRemote") == null) {
            logger.warn("-DisRemote is NULL, use default param 'false', alternative 'true'");
            System.setProperty("isRemote", "false");
        }

        // local browser chrome driver v. 84.0.4147.30
        System.setProperty("webdriver.chrome.driver", ("./src/main/resources/driver/chromedriver"));

        // Read xml suites
        final Parser main = new Parser(("./src/main/resources/suites/main.xml"));
        final Parser local = new Parser(("./src/main/resources/suites/local.xml"));

        // Create list Xml suites
        final List<XmlSuite> suites = new ArrayList<>();
        switch (System.getProperty("isRemote")){
            case "true": suites.addAll(main.parseToList()); break;
            case "false": suites.addAll(local.parseToList()); break;
        }

//        suites.get(0).m_childSuites.get(0).m_tests.get(0).m_xmlPackages.remove(0);
//        suites.get(0).getChildSuites().get(0).getTests().get(0).getXmlPackages().remove(0);//works
        // Create testNG with default test adapter listeners
        final TestNG testNG = new TestNG(true);
        testNG.setXmlSuites(suites);
        testNG.run();
    }
}
