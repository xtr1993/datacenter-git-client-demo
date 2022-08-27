package datacenter.git.client.demo.git;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import datacenter.git.client.demo.model.GitResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GitManagerHttp {

    private static final Logger logger = LoggerFactory.getLogger(GitManagerHttp.class);

    public static GitResult<JSONObject> doPost(String url, String token, String content) {
        GitResult<JSONObject> gitResult = new GitResult<JSONObject>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("PRIVATE-TOKEN", token);
        httpPost.setEntity(new StringEntity(content, "UTF8"));
        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            gitResult.setCode(statusCode);
            if (null != entity) {
                JSONObject object = JSON.parseObject(EntityUtils.toString(entity));
                gitResult.setMessage(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return gitResult;
    }

    public static GitResult<JSONObject> doGet(String url, String token) {
        GitResult<JSONObject> gitResult = new GitResult<JSONObject>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("PRIVATE-TOKEN", token);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            gitResult.setCode(statusCode);
            if (entity != null) {
                JSONObject object = JSON.parseObject(EntityUtils.toString(entity));
                gitResult.setMessage(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return gitResult;
    }

    public static GitResult<JSONObject> put(String url, String token, String content) {
        GitResult<JSONObject> gitResult = new GitResult<JSONObject>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json; charset=utf-8");
        httpPut.setHeader("PRIVATE-TOKEN", token);
        httpPut.setEntity(new StringEntity(content, "UTF8"));
        try {
            CloseableHttpResponse response = httpclient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            gitResult.setCode(statusCode);
            if (null != entity) {
                JSONObject object = JSON.parseObject(EntityUtils.toString(entity));
                gitResult.setMessage(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return gitResult;
    }


    public static GitResult<JSONObject> delete(String url, String token) {
        GitResult<JSONObject> gitResult = new GitResult<JSONObject>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        try {
            httpDelete.setHeader("PRIVATE-TOKEN", token);
            CloseableHttpResponse response = httpclient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            gitResult.setCode(statusCode);
            if (null != entity) {
                JSONObject object = JSON.parseObject(EntityUtils.toString(entity));
                gitResult.setMessage(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return gitResult;
    }
}
