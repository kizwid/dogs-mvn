package sandkev.rest;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertTrue;
import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Created by kevsa on 07/06/2019.
 */
public class RestTest {

    @Test
    public void canGet200Response() throws InterruptedException {

//        Logger logger = LoggerFactory.getLogger(RestTest.class);
//        SparkUtils.createServerWithRequestLog(logger);
        port(1818);
        get("/hello", (request, response) -> "world");

        for(int n = 0; n < 100; n++){
            given()
                    .when()
                    .get("http://localhost:1818/hello")
                    .then()
                    .assertThat()
                    .body("world", contains("world"))
                    .and().statusCode(200);
        }

        //for /f "tokens=4" %i in ('netstat -an ^|findstr "1818"') DO (set /A cnt=cnt+1)
        //netstat -an|grep 1818|awk '{print $4}'|sort|uniq -c
        //netstat -an | awk '$1 ~/TCP/ && $3~/127/ {print $4}'|sort|uniq -c
        //D:\tools\cygwin\bin\bash.exe --login -i -c "netstat -an | awk '$1 ~/TCP/ && $3~/127/ {print $4}'|sort|uniq -c"
        //D:\tools\cygwin\bin\bash.exe --login -i -c "netstat -an|grep 1818|awk '{print $4}'|sort|uniq -c"
        //$a = netstat -an;$a[3..$a.count] | ConvertFrom-String | select p2,p3,p4,p5|where P4 -match '1818'|Select-Object P5|sort|uniq -c

        Map<String, Integer> netStats = collectNetStats();
        assertTrue(netStats.size() > 1);
        assertTrue(netStats.containsKey("TIME_WAIT"));
        assertTrue(netStats.get("TIME_WAIT") > 10);

        //wait a moment
//        Thread.sleep(2000);
//        Map<String, Integer> netStats2 = collectNetStats();
//        //assertTrue(netStats.size() > netStats.size());
//        assertTrue(netStats2.containsKey("TIME_WAIT"));
//        assertTrue(netStats2.get("TIME_WAIT") > 10);


    }

    private Map<String,Integer> collectNetStats() {
        Map<String,Integer> status = new HashMap<>();
        ProcessBuilder processBuilder = new ProcessBuilder();
        //processBuilder.command("cmd.exe", "/c", "ping -n 3 google.com");
        //processBuilder.command("cmd.exe", "/c", "D:\\tools\\cygwin\\bin\\bash.exe --login -i -c \"netstat -an|grep 1818|awk '{print $4}'|sort|uniq -c\"");
        processBuilder.command("C:\\Windows\\system32\\WindowsPowerShell\\v1.0\\powershell.exe", "-Command", "$a = netstat -an;$a[3..$a.count] | ConvertFrom-String | select p2,p3,p4,p5|where p4 -match '1818'|Select-Object p5|sort|uniq -c");
        try {

            Process process = processBuilder.start();

            // blocked :(
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] split = line.trim().split("\\s");
                if(split.length==2 && split[1].length()>2){
                    status.put(split[1], Integer.parseInt(split[0]));
                }
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("status: " + status);
        return status;
    }
}
