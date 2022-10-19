import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;


public class WebScraper {

    ConcurrentHashMap<String, Float> totoData = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, String> websiteData = new ConcurrentHashMap<>();
    AtomicInteger boardCounter = new AtomicInteger();
    AtomicInteger faultyLinks = new AtomicInteger();
    AtomicInteger testCounter = new AtomicInteger();
    Document doc;
    URL urlTemp;
    String url = "https://www.yelu.sg/lottery/results/history";
    Element data;
    String tempString = "";
    Float tempFloat = 0F;
    WebClient webClient = new WebClient(BrowserVersion.CHROME);

    public float snowballStatistics() {
        return totoData.get("avgTotoSnowball");
    }
    public float grp2Statistics() {
        return totoData.get("avgGrp2Winners");
    }
    public float grp3Statistics() {
        return totoData.get("avgGrp3Winners");
    }
    public float grp4Statistics() {
        return totoData.get("avgGrp4Winners");
    }
    public void webScraper() {

        //setting up the HashMap
        totoData.put("avgTotoSnowball", 0F);
        for (int x = 1; x < 8; x++) {
            totoData.put("avgGrp" + x + "Winners", 0F);
        }

        //option to use default values instead of scanning 500 pages
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to use default values for test? (note: This would skip webscraping)");
        System.out.println("Enter y to use default values or n to get fresh data from webscraping");
        String scanOption = scan.next();
        while (true) {
            if (scanOption.trim().equalsIgnoreCase("y") || scanOption.trim().equalsIgnoreCase("n")){
                break;
            } else {
                System.out.println("Please enter a valid option (y/n)");
                scanOption = scan.next();
            }
        }
        if (scanOption.equalsIgnoreCase("y")) {
            totoData.put("avgTotoSnowball", 43.487392F);
            totoData.put("avgGrp1Winners", 0.6407563F);
            totoData.put("avgGrp2Winners", 3.8886554F);
            totoData.put("avgGrp3Winners", 169.94537F);
            totoData.put("avgGrp4Winners", 422.15546F);
            totoData.put("avgGrp5Winners", 8727.748F);
            totoData.put("avgGrp6Winners", 11556.46F);
            totoData.put("avgGrp7Winners", 155589.39F);
        } else {

            //turning errors off otherwise it would not work
            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

            //setting multi thread cores
            int coreCount = Runtime.getRuntime().availableProcessors();
            System.out.println("Utilizing " + coreCount + " threads...");
            ExecutorService service = Executors.newFixedThreadPool(coreCount);

            try {
                webClient.getOptions().setThrowExceptionOnScriptError(false);

                HtmlPage page = webClient.getPage(url);

                //select form and input values to get to the history page
                HtmlForm form = page.getForms().get(0);
                HtmlSelect select = (HtmlSelect) page.getElementById("LotteryName");
                HtmlOption option = select.getOptionByValue("TOTO");
                select.setSelectedAttribute(option, true);

                //creating a button, tysm stackoverflow I love u
                HtmlElement button = (HtmlElement) page.createElement("button");
                button.setAttribute("type", "submit");
                form.appendChild(button);
                page = button.click();

                //saving the HtmlUnit data into a doc to read using Jsoup
                doc = Jsoup.parse(page.asXml());
                Elements content = doc.getElementsByTag("a");

                //to get base url after getting results from below
                URL baseUrl = new URL(url);

                //for loop to filter links to all result boards
                for (Element x : content) {
                    if (x.attr("href").contains("toto")) {
                        if (x.attr("href").contains("draw")) {
                            urlTemp = new URL(baseUrl, x.attr("href"));
                            websiteData.put(boardCounter.incrementAndGet(), urlTemp.toString());
                        }
                    }
                }

                System.out.println("Scanning " + boardCounter + " pages online...");

                //multithreaded website scraping
                for (int x = 1; x <= boardCounter.get(); x++) {
                    service.submit(new scrapperFunction());
                }

                //countdownlatch to prevent main thread from finishing calculation BEFORE data is tabulated
                CountDownLatch latch = new CountDownLatch(coreCount);

                class countDownLatch implements Runnable {

                    @Override
                    public void run() {
                        latch.countDown();
                    }
                }

                for (int x = 0; x < coreCount; x++) {
                    service.submit(new countDownLatch());
                }

                latch.await();
                service.shutdown();

                //to ensure threads have time to FINISH calculation before main thread continues
                while (!(service.isTerminated())) {
                }

                //doing the math
                totoData.put("avgTotoSnowball", (totoData.get("avgTotoSnowball") / boardCounter.get()) * 100);
                for (int x = 1; x < 8; x++) {
                    totoData.put("avgGrp" + x + "Winners", totoData.get("avgGrp" + x + "Winners") / boardCounter.get());
                }

                System.out.println("Sample size: " + boardCounter);
                System.out.println("There were " + faultyLinks + " faulty links");

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException npe) {
                boardCounter.decrementAndGet();
                faultyLinks.incrementAndGet();
            }
        }
                System.out.println("The average chance of Toto Prize Snowballing is " + totoData.get("avgTotoSnowball") + "%");
                for (int x = 1; x < 8; x++) {
                    System.out.println("The average number of Group " + x + " Winners is: " + totoData.get("avgGrp" + x + "Winners"));
                }
    }


    public class scrapperFunction implements Runnable {

        @Override
        public void run() {
            //adding the statistics
            try {
                doc = Jsoup.connect(websiteData.get(testCounter.incrementAndGet())).get();
                data = doc.selectFirst("div.desc");

                for (int xa = 2; xa <= 8; xa++) {
                    //int xa starts at 2 as that is the address of the data
                    tempString = data.select("table>tbody > tr:nth-child(" + xa + ") > td:nth-child(4)").text();
                    tempFloat = Float.parseFloat(tempString.replace(",", ""));

                    //data filter for each group
                    if (xa == 2) {
                        totoData.merge("avgGrp1Winners", tempFloat, Float::sum);
                        tempFloat = tempFloat > 0F ? 1F : 0F;
                        totoData.merge("avgTotoSnowball", tempFloat, Float::sum);
                    } else if (xa == 3) {
                        totoData.merge("avgGrp2Winners", tempFloat, Float::sum);
                    } else if (xa == 4) {
                        totoData.merge("avgGrp3Winners", tempFloat, Float::sum);
                    } else if (xa == 5) {
                        totoData.merge("avgGrp4Winners", tempFloat, Float::sum);
                    } else if (xa == 6) {
                        totoData.merge("avgGrp5Winners", tempFloat, Float::sum);
                    } else if (xa == 7) {
                        totoData.merge("avgGrp6Winners", tempFloat, Float::sum);
                    } else {
                        totoData.merge("avgGrp7Winners", tempFloat, Float::sum);
                    }
                }
            } catch (NullPointerException | IOException npe) {
                boardCounter.decrementAndGet();
                faultyLinks.incrementAndGet();
            }
        }
    }
}
