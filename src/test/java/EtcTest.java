import com.spun.util.ObjectUtils;
import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class EtcTest {

    private static final  boolean USE_LOCAL = true;

    @Test
    public void test2016(){
        verify("2016");
    }

    @Test
    public void testSpeakers(){
    	verify("2016/speakers");
    }

    @Test
    public void test2016Schedule(){
        verify("2016/schedule");
    }

    @Test
    public void test2016Topics(){
        verify("2016/topics");
    }
    @Test
    public void test2017(){
    	verify("2017");
    }

    private void verify(String end) {
        String baseUrl = USE_LOCAL ? "http://localhost:4000/" : "http://europeantestingconference.eu/";
        String html = loadWebPage(baseUrl + end);
        html = normalizeHtml(html);
        verifyPage(html);
    }

    private String normalizeHtml(String html) {
        html = html.replaceAll(" +", " ");
        html = html.replaceAll("(?m)^\t+$", "\n");
        html = html.replaceAll("(?m)^ +$", "\n");
        html = html.replaceAll("\n+", "\n");
        html = customNormalizations(html);
        return html;
    }
	private String customNormalizations(String html) {
//		html = html.replaceAll("/snippets/2016/", "/");
//        html = html.replaceAll("fix-anchor\" id=\"", "fix-anchor\" id=\"/speakers/");
//        html = html.replaceAll("class=\"topic\" id=\"", "class=\"topic\" id=\"/topics/");
//        html = html.replaceAll("a href=\"/speakers#", "a href=\"/speakers#/topics/");
		return html;
	}

    private void verifyPage(String response) {
        Approvals.verifyHtml(response);
    }

    private String loadWebPage(String url)
    {
        try {
            return Request.Get(url).execute().returnContent().asString();
        } catch (IOException e) {
            throw ObjectUtils.throwAsError( e );
        }
    }
}
