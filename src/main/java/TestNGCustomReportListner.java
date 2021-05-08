import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

/**
 * Created by VISISHTA.
 */
public class TestNGCustomReportListner implements IReporter{

    private Map<String, Map<String, Boolean>> failedTests = new HashMap<>();
    private PrintWriter writer;
    private int m_row;
    private Integer m_testIndex;
    private int m_methodIndex;
    private String reportTitle= "Отчет по тестированию " + System.getProperty("mode").toUpperCase();
    private String reportFileName = "emailable-report.html";
    private static Map<String, String> classDescriptor = new HashMap<String, String>() {
        {
            put("tests.portal.Buyer", "Покупка услуг");
            put("tests.portal.Patient", "Запись на онлайн прием");
            put("tests.portal.Visit", "Запись и отмена");
            put("tests.portal.Anonym", "Анонимная запись с СМС");
            put("tests.portal.ManySpecialists", "Запись к нескольким специалистам");
            put("tests.promo.Auth", "Запись на онлайн-прием через виджет");
            put("tests.mobile.Buyer", "Покупка услуг на телефоне");
            put("tests.mobile.Visit", "Запись и отмена на телефоне");
            put("tests.laboratory.Basic", "Базовый тест лаборатории");
            put("tests.admin.AccountantBilling", "Бухгалтерская админка");
            put("tests.admin.AddOutsideSystem", "Добавление внешней системы в админке");
            put("tests.admin.AddSite", "Добавление сайта в админке");
            put("tests.admin.ManagerBilling", "Менеджерская админка");
            put("tests.api.ApiTestCase", "API портала");
            put("tests.api.LabportalApi", "API лаборатории");
            put("tests.sdk.SdkTest", "SDK");
            put("tests.webinfoclinica.BasicTest", "Базовый тест веб-инфоклиники");
            put("tests.webinfoclinica.AdvancedTestCash", "Расширенный тест веб-нифоклиники (наличные)");
            put("tests.AppiumTest", "Appium");
            put("tests.laboratory.AliveTest", "AliveTEst");
            put("tests.vcdemo.CheckConferences", "VC-DEMO");
            put("tests.gmail.Portal", "Gmail test");
        }
        ;
    };


    /** Creates summary of the run */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outdir) {
        try {
            writer = createWriter(outdir);
        } catch (IOException e) {
            System.err.println("Unable to create output file");
            e.printStackTrace();
            return;
        }

        startHtml(writer);
        writeReportTitle(reportTitle);
        generateSuiteSummaryReport(suites);
        generateMethodSummaryReport(suites);
        generateBrokenMethodsReport();
        generateMethodDetailReport(suites);
        endHtml(writer);
        writer.flush();
        writer.close();
    }

    protected PrintWriter createWriter(String outdir) throws IOException {
        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, reportFileName))));
    }

    /**
     * Создает таблицу с методами, которые нигде не выполнились
     */
    protected void generateBrokenMethodsReport() {
        tableStart("methodOverview", "brokenMethods");

        writer.println("<tr><th>Class</th><th>Method</th></tr>");
        failedTests.forEach((k, v) -> {
            v.forEach((k2, v2) -> {
                if(!v2) writer.println("<tr><td>"+ k +"</td><td>" + k2 + "</td></tr>");
            });
        });
        writer.println("</table>");
    }

    /**
     * Creates a table showing the highlights of each test method with links to
     * the method details
     */
    protected void generateMethodSummaryReport(List<ISuite> suites) {
        m_methodIndex = 0;
        startResultSummaryTable("methodOverview");
        int testIndex = 1;
        for (ISuite suite : suites) {
            if (suites.size() >= 1) {
                titleRow(suite.getName(), 5);
            }

            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
                ITestContext testContext = r2.getTestContext();
                String testName = testContext.getName();
                m_testIndex = testIndex;
                writer.println("<tr><th>Class</th>"
                        + "<th>Method</th><th>Start Time </th><th>Execution Time<br/>(hh:mm:ss)</th></tr>");
                resultSummary(suite, testContext.getFailedConfigurations(), testName, "failed", " (configuration methods)");
                resultSummary(suite, testContext.getFailedTests(), testName, "failed", "");
                resultSummary(suite, testContext.getSkippedConfigurations(), testName, "skipped", " (configuration methods)");
                resultSummary(suite, testContext.getSkippedTests(), testName, "skipped", "");
                resultSummary(suite, testContext.getPassedTests(), testName, "passed", "");
                testIndex++;
            }
        }
        writer.println("</table>");
    }

    /** Creates a section showing known results for each method */
    protected void generateMethodDetailReport(List<ISuite> suites) {
        m_methodIndex = 0;
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
                ITestContext testContext = r2.getTestContext();
                if (r.values().size() > 0) {
                    writer.println("<h1>" + testContext.getName() + "</h1>");
                }
                resultDetail(testContext.getFailedConfigurations());
                resultDetail(testContext.getFailedTests());
                resultDetail(testContext.getSkippedConfigurations());
                resultDetail(testContext.getSkippedTests());
                resultDetail(testContext.getPassedTests());
            }
        }
    }

    /**
     * @param tests
     */
    private void resultSummary(ISuite suite, IResultMap tests, String testname,
                               String style, String details) {

        if (tests.getAllResults().size() > 0) {
            StringBuffer buff = new StringBuffer();
            String lastClassName = "";
            int mq = 0;
            int cq = 0;
            for (ITestNGMethod method : getMethodSet(tests, suite)) {
                m_row += 1;
                m_methodIndex += 1;
                ITestClass testClass = method.getTestClass();
                String className = classDescriptor.get(testClass.getName());
                if (mq == 0) {
                    String id = (m_testIndex == null ? null : "t"
                            + Integer.toString(m_testIndex));
                    titleRow(testname + " &#8212; " + style + details, 5, id);
                    m_testIndex = null;
                }
                if (!className.equalsIgnoreCase(lastClassName)) {
                    if (mq > 0) {
                        cq += 1;
                        writer.print("<tr class=\"" + style
                                + (cq % 2 == 0 ? "even" : "odd") + "\">"
                                + "<td");
                        if (mq > 1) {
                            writer.print(" rowspan=\"" + mq + "\"");
                        }
                        writer.println(">" + lastClassName + "</td>" + buff);
                    }
                    mq = 0;
                    buff.setLength(0);
                    lastClassName = className;
                }
                Set<ITestResult> resultSet = tests.getResults(method);
                long end = Long.MIN_VALUE;
                long start = Long.MAX_VALUE;
                long startMS=0;
                String firstLine="";

                for (ITestResult testResult : tests.getResults(method)) {
                    if (testResult.getEndMillis() > end) {
                        end = testResult.getEndMillis()/1000;
                    }
                    if (testResult.getStartMillis() < start) {
                        startMS = testResult.getStartMillis();
                        start =startMS/1000;
                    }

                    Throwable exception=testResult.getThrowable();
                    boolean hasThrowable = exception != null;
                    if(hasThrowable){
                        String str = Utils.shortStackTrace(exception, true);
                        Scanner scanner = new Scanner(str);
                        firstLine = scanner.nextLine();
                    }
                }
                DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(startMS);

                mq += 1;
                if (mq > 1) {
                    buff.append("<tr class=\"" + style
                            + (cq % 2 == 0 ? "odd" : "even") + "\">");
                }
                //String description = method.getDescription();
                String testInstanceName = resultSet
                        .toArray(new ITestResult[] {})[0].getTestName();
                buff.append("<td><a href=\"#m"
                        + m_methodIndex
                        + "\">"
                        + qualifiedName(method)
                        + " "
                        /*+ (description != null && description.length() > 0 ? "(\""
                        + description + "\")"
								: "")*/
                + "</a>"
                        + (null == testInstanceName ? "" : "<br/>("
                        + testInstanceName + ")") + "</td>"
                        //+ "<td class=\"numi\" style=\"text-align:left;padding-right:2em\">" + firstLine+"<br/></td>"
                        + "<td style=\"text-align:right\">" + formatter.format(calendar.getTime()) + "</td>" + "<td class=\"numi\">"
                        + timeConversion(end - start) + "</td>" + "</tr>");

                if(!failedTests.containsKey(lastClassName)){
                    failedTests.put(lastClassName, new HashMap<>());
                }
                if(!qualifiedName(method).contains("Инициализация") && !qualifiedName(method).contains("Завершение")) {
                    if (style.contains("failed") || style.contains("skipped")) {
                        if (!failedTests.get(lastClassName).containsKey(qualifiedName(method)) || !failedTests.get(lastClassName).get(qualifiedName(method))) {
                            failedTests.get(lastClassName).put(qualifiedName(method), false);
                        }
                    } else {
                        failedTests.get(lastClassName).put(qualifiedName(method), true);
                    }
                }
                System.out.println(suite.getName() + " " + lastClassName + " Method: " + qualifiedName(method) + " style: " + style);

            }
            if (mq > 0) {
                cq += 1;
                writer.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
                if (mq > 1) {
                    writer.print(" rowspan=\"" + mq + "\"");
                }
                writer.println(">" + lastClassName + "</td>" + buff);
            }
        }
    }


    private String timeConversion(long seconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
        seconds -= minutes * SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;

        return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int)seconds);
    }

    private String prefixZeroToDigit(int num){
        int number=num;
        if(number<=9){
            String sNumber="0"+number;
            return sNumber;
        }
        else
            return ""+number;

    }

    /** Starts and defines columns result summary table */
    private void startResultSummaryTable(String style) {
        tableStart(style, "summary");
//        writer.println("<tr><th>Class</th>"
//                + "<th>Method</th><th>Start Time </th><th>Execution Time<br/>(hh:mm:ss)</th></tr>");
        m_row = 0;
    }

    private String qualifiedName(ITestNGMethod method) {
        StringBuilder addon = new StringBuilder();
        String[] groups = method.getGroups();
        int length = groups.length;
        if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
            addon.append("(");
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    addon.append(", ");
                }
                addon.append(groups[i]);
            }
            addon.append(")");
        }

        return "<b>" + method.getDescription() + "</b> "/* + addon*/;
    }

    private void resultDetail(IResultMap tests) {
        Set<ITestResult> testResults=tests.getAllResults();
        List<ITestResult> testResultsList = new ArrayList<ITestResult>(testResults);
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
        Collections.sort(testResultsList, new TestResultsSorter());
        for (ITestResult result : testResultsList) {
            ITestNGMethod method = result.getMethod();
            m_methodIndex++;
            String cname = method.getTestClass().getName();
            writer.println("<div class=\"container border rounded\"><h2 id=\"m" + m_methodIndex + "\">" + cname + ":"
                    + method.getMethodName()/* + method.getDescription()*/ + "</h2>");
            Set<ITestResult> resultSet = tests.getResults(method);
            generateResult(result, method, resultSet.size());
            writer.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p></div><br/>");

        }
    }

    private void generateResult(ITestResult ans, ITestNGMethod method,
                                int resultSetSize) {
        Object[] parameters = ans.getParameters();
        boolean hasParameters = parameters != null && parameters.length > 0;
        if (hasParameters) {
            tableStart("result", null);
            writer.print("<tr class=\"param\">");
            for (int x = 1; x <= parameters.length; x++) {
                writer.print("<th>Param." + x + "</th>");
            }
            writer.println("</tr>");
            writer.print("<tr class=\"param stripe\">");
            for (Object p : parameters) {
                writer.println("<td>" + Utils.escapeHtml(Utils.toString(p))
                        + "</td>");
            }
            writer.println("</tr>");
        }
        List<String> msgs = Reporter.getOutput(ans);
        boolean hasReporterOutput = msgs.size() > 0;
        Throwable exception = ans.getThrowable();
        boolean hasThrowable = exception != null;
        if (hasReporterOutput || hasThrowable) {
            if (hasParameters) {
                writer.print("<tr><td");
                if (parameters.length > 1) {
                    writer.print(" colspan=\"" + parameters.length + "\"");
                }
                writer.println(">");
            } else {
                writer.println("<div>");
            }
            if (hasReporterOutput) {
                if (hasThrowable) {
                    writer.println("<h3>Test Messages</h3>");
                }
                for (String line : msgs) {
                    writer.println(line + "<br/>");
                }
            }
            if (hasThrowable) {
                boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
                if (hasReporterOutput) {
                    writer.println("<h3>"
                            + (wantsMinimalOutput ? "Expected Exception"
                            : "Failure") + "</h3>");
                }
                generateExceptionReport(exception, method);
            }
            if (hasParameters) {
                writer.println("</td></tr>");
            } else {
                writer.println("</div>");
            }
        }
        if (hasParameters) {
            writer.println("</table>");
        }
    }

    protected void generateExceptionReport(Throwable exception, ITestNGMethod method) {
        writer.print("<div class=\"stacktrace\">");
        writer.print(Utils.shortStackTrace(exception, true));
        writer.println("</div>");
    }

    /**
     * Since the methods will be sorted chronologically, we want to return the
     * ITestNGMethod from the invoked methods.
     */
    private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {

        List<IInvokedMethod> r = Lists.newArrayList();
        List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
        for (IInvokedMethod im : invokedMethods) {
            if (tests.getAllMethods().contains(im.getTestMethod())) {
                r.add(im);
            }
        }

        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
        Collections.sort(r,new TestSorter());
        List<ITestNGMethod> result = Lists.newArrayList();

        // Add all the invoked methods
        for (IInvokedMethod m : r) {
            for (ITestNGMethod temp : result) {
                if (!temp.equals(m.getTestMethod()))
                    result.add(m.getTestMethod());
            }
        }

        // Add all the methods that weren't invoked (e.g. skipped) that we
        // haven't added yet
        Collection<ITestNGMethod> allMethodsCollection=tests.getAllMethods();
        List<ITestNGMethod> allMethods=new ArrayList<ITestNGMethod>(allMethodsCollection);
        Collections.sort(allMethods, new TestMethodSorter());

        for (ITestNGMethod m : allMethods) {
            if (!result.contains(m)) {
                result.add(m);
            }
        }
        return result;
    }

    @SuppressWarnings("unused")
    public void generateSuiteSummaryReport(List<ISuite> suites) {
        tableStart("testOverview", null);
        writer.print("<tr>");
        tableColumnStart("Test");
        tableColumnStart("Methods<br/>Passed");
        tableColumnStart("# Skipped");
        tableColumnStart("# Failed");
//        tableColumnStart("Browser");
        tableColumnStart("Start<br/>Time");
        tableColumnStart("End<br/>Time");
        tableColumnStart("Total<br/>Time(hh:mm:ss)");
//        tableColumnStart("Included<br/>Groups");
//        tableColumnStart("Excluded<br/>Groups");

        writer.println("</tr>");
        NumberFormat formatter = new DecimalFormat("#,##0.0");
        int qty_tests = 0;
        int qty_pass_m = 0;
        int qty_pass_s = 0;
        int qty_skip = 0;
        long time_start = Long.MAX_VALUE;
        int qty_fail = 0;
        long time_end = Long.MIN_VALUE;
        m_testIndex = 1;
        for (ISuite suite : suites) {
            if (suites.size() >= 1) {
                titleRow(suite.getName(), 10);
            }
            Map<String, ISuiteResult> tests = suite.getResults();
            for (ISuiteResult r : tests.values()) {
                qty_tests += 1;
                ITestContext overview = r.getTestContext();

                startSummaryRow(overview.getName());
                int q = getMethodSet(overview.getPassedTests(), suite).size();
                qty_pass_m += q;
                summaryCell(q, Integer.MAX_VALUE);
                q = getMethodSet(overview.getSkippedTests(), suite).size();
                qty_skip += q;
                summaryCell(q, 0);
                q = getMethodSet(overview.getFailedTests(), suite).size();
                qty_fail += q;
                summaryCell(q, 0);

                // Write OS and Browser
//                summaryCell(suite.getParameter("browserType"), true);
//                writer.println("</td>");

                SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss");
                summaryCell(summaryFormat.format(overview.getStartDate()),true);
                writer.println("</td>");

                summaryCell(summaryFormat.format(overview.getEndDate()),true);
                writer.println("</td>");

                time_start = Math.min(overview.getStartDate().getTime(), time_start);
                time_end = Math.max(overview.getEndDate().getTime(), time_end);
                summaryCell(timeConversion((overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 1000), true);

//                summaryCell(overview.getIncludedGroups());
//                summaryCell(overview.getExcludedGroups());
                writer.println("</tr>");
                m_testIndex++;
            }
        }
        if (qty_tests > 1) {
            writer.println("<tr class=\"total\"><td>Total</td>");
            summaryCell(qty_pass_m, Integer.MAX_VALUE);
            summaryCell(qty_skip, 0);
            summaryCell(qty_fail, 0);
            summaryCell(" ", true);
            summaryCell(" ", true);
//            summaryCell(" ", true);
            summaryCell(timeConversion(((time_end - time_start) / 1000)), true);
//            writer.println("<td colspan=\"3\">&nbsp;</td></tr>");
        }
        writer.println("</table>");
    }


    private void summaryCell(String[] val) {
        StringBuffer b = new StringBuffer();
        for (String v : val) {
            b.append(v + " ");
        }
        summaryCell(b.toString(), true);
    }

    private void summaryCell(String v, boolean isgood) {
        writer.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
                + "</td>");
    }

    private void startSummaryRow(String label) {
        m_row += 1;
        writer.print("<tr"
                + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
        + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t"
                + m_testIndex + "\"><b>" + label + "</b></a>" + "</td>");

    }

    private void summaryCell(int v, int maxexpected) {
        summaryCell(String.valueOf(v), v <= maxexpected);
    }

    private void tableStart(String cssclass, String id) {
        writer.println("<table class=\"table table-striped table-sm\" cellspacing=\"0\" cellpadding=\"0\""
                + (cssclass != null ? " class=\"" + cssclass + "\""
						: " style=\"padding-bottom:2em\"")
        + (id != null ? " id=\"" + id + "\"" : "") + ">");
        m_row = 0;
    }

    private void tableColumnStart(String label) {
        writer.print("<th>" + label + "</th>");
    }

    private void titleRow(String label, int cq) {
        titleRow(label, cq, null);
    }

    private void titleRow(String label, int cq, String id) {
        writer.print("<tr");
        if (id != null) {
            writer.print(" id=\"" + id + "\"");
        }
        writer.println("><th colspan=\"" + cq + "\" class=\"text-center table-primary\">" + label + "</th></tr>");
        m_row = 0;
    }

    protected void writeReportTitle(String title) {
        writer.print("<br/><center><h1><a href=\"#brokenMethods\">" + title + " - " + getDateAsString() + "</a></h1></center>");
    }


    /*
     * Method to get Date as String
     */
    private String getDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /** Starts HTML stream */
    protected void startHtml(PrintWriter out) {
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
                out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println("<title>TestNG Report</title>");
        out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        out.println("<style type=\"text/css\">");
        out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
//        out.println("td,th {border:1px solid #009;padding:.25em .5em}");
        out.println(".result th {vertical-align:bottom}");
        out.println(".param th {padding-left:1em;padding-right:1em}");
        out.println(".param td {padding-left:.5em;padding-right:2em}");
        out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
        out.println(".numi,.numi_attn {text-align:right}");
        out.println(".total td {font-weight:bold}");
        out.println(".passedodd td {background-color: #c3e6cb}");
        out.println(".passedeven td {background-color: #56b86d}");
        out.println(".skippedodd td {background-color: #f2f2f2}");
        out.println(".skippedeven td {background-color: #fff}");
        out.println(".failedodd td,.numi_attn {background-color: #f5c6cb}");
        out.println(".failedeven td,.stripe .numi_attn {background-color: #ed9ba4}");
        out.println(".stacktrace {white-space:pre;font-family:monospace}");
        out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        out.println("/*! CSS Used from: https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css */\n" +
                ":root{--blue:#007bff;--indigo:#6610f2;--purple:#6f42c1;--pink:#e83e8c;--red:#dc3545;--orange:#fd7e14;--yellow:#ffc107;" +
                "--green:#28a745;--teal:#20c997;--cyan:#17a2b8;--white:#fff;--gray:#6c757d;--gray-dark:#343a40;--primary:#007bff;" +
                "--secondary:#6c757d;--success:#28a745;--info:#17a2b8;--warning:#ffc107;--danger:#dc3545;--light:#f8f9fa;--dark:#343a40;--breakpoint-xs:0;" +
                "--breakpoint-sm:576px;--breakpoint-md:768px;--breakpoint-lg:992px;--breakpoint-xl:1200px;--font-family-sans-serif:-apple-system,BlinkMacSystemFont," +
                "\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,\"Noto Sans\",sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\",\"Noto Color Emoji\";" +
                "--font-family-monospace:SFMono-Regular,Menlo,Monaco,Consolas,\"Liberation Mono\",\"Courier New\",monospace;}\n" +
                "*,::after,::before{box-sizing:border-box;}\n" +
                "html{font-family:sans-serif;line-height:1.15;-webkit-text-size-adjust:100%;-webkit-tap-highlight-color:transparent;}\n" +
                "body{margin:0;font-family:-apple-system,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,\"Noto Sans\",sans-serif,\"Apple Color Emoji\"," +
                "\"Segoe UI Emoji\",\"Segoe UI Symbol\",\"Noto Color Emoji\";font-size:1rem;font-weight:400;line-height:1.5;color:#212529;" +
                "text-align:left;background-color:#fff;}\n" +
                "h1,h2{margin-top:0;margin-bottom:.5rem;}\n" +
                "p{margin-top:0;margin-bottom:1rem;}\n" +
                "b{font-weight:bolder;}\n" +
                "a{color:#007bff;text-decoration:none;background-color:transparent;}\n" +
                "a:hover{color:#0056b3;text-decoration:underline;}\n" +
                "table{border-collapse:collapse;}\n" +
                "th{text-align:inherit;}\n" +
                "h1,h2{margin-bottom:.5rem;font-weight:500;line-height:1.2;}\n" +
                "h1{font-size:2.5rem;}\n" +
                "h2{font-size:2rem;}\n" +
                ".container{width:100%;padding-right:15px;padding-left:15px;margin-right:auto;margin-left:auto;}\n" +
                "@media (min-width:576px){\n" +
                ".container{max-width:540px;}\n" +
                "}\n" +
                "@media (min-width:768px){\n" +
                ".container{max-width:720px;}\n" +
                "}\n" +
                "@media (min-width:992px){\n" +
                ".container{max-width:960px;}\n" +
                "}\n" +
                "@media (min-width:1200px){\n" +
                ".container{max-width:1140px;}\n" +
                "}\n" +
                ".table{width:100%;margin-bottom:1rem;color:#212529;}\n" +
                ".table td,.table th{padding:.75rem;vertical-align:top;border-top:1px solid #dee2e6;}\n" +
                ".table-sm td,.table-sm th{padding:.3rem;}\n" +
                ".table-striped tbody tr:nth-of-type(odd){background-color:rgba(0,0,0,.05);}\n" +
                ".table-primary{background-color:#b8daff;}\n" +
                ".border{border:1px solid #dee2e6!important;}\n" +
                ".rounded{border-radius:.25rem!important;}\n" +
                ".text-center{text-align:center!important;}\n" +
                "@media print{\n" +
                "*,::after,::before{text-shadow:none!important;box-shadow:none!important;}\n" +
                "a:not(.btn){text-decoration:underline;}\n" +
                "tr{page-break-inside:avoid;}\n" +
                "h2,p{orphans:3;widows:3;}\n" +
                "h2{page-break-after:avoid;}\n" +
                "body{min-width:992px!important;}\n" +
                ".container{min-width:992px!important;}\n" +
                ".table{border-collapse:collapse!important;}\n" +
                ".table td,.table th{background-color:#fff!important;}\n" +
                "}");
        out.println("</style>");
//        out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\">\n");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");

    }

    /** Finishes HTML stream */
    protected void endHtml(PrintWriter out) {
        out.println("<center> TestNG Report </center>");
        out.println("</div></body></html>");
    }

    // ~ Inner Classes --------------------------------------------------------
    /** Arranges methods by classname and method name */
    private class TestSorter implements Comparator<IInvokedMethod> {
        // ~ Methods
        // -------------------------------------------------------------

        /** Arranges methods by classname and method name */
        @Override
        public int compare(IInvokedMethod obj1, IInvokedMethod obj2) {
            int r = obj1.getTestMethod().getTestClass().getName().compareTo(obj2.getTestMethod().getTestClass().getName());
            return r;
        }
    }

    private class TestMethodSorter implements Comparator<ITestNGMethod> {
        @Override
        public int compare(ITestNGMethod obj1, ITestNGMethod obj2) {
            int r = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
            if (r == 0) {
                r = obj1.getMethodName().compareTo(obj2.getMethodName());
            }
            return r;
        }
    }

    private class TestResultsSorter implements Comparator<ITestResult> {
        @Override
        public int compare(ITestResult obj1, ITestResult obj2) {
            int result = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
            if (result == 0) {
                result = obj1.getMethod().getMethodName().compareTo(obj2.getMethod().getMethodName());
            }
            return result;
        }
    }
}
