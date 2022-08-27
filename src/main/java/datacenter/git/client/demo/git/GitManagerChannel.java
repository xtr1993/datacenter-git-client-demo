package datacenter.git.client.demo.git;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import datacenter.git.client.demo.IGit;
import datacenter.git.client.demo.model.FileTree;
import datacenter.git.client.demo.model.GitResult;
import datacenter.git.client.demo.model.Version;
import datacenter.git.client.demo.utils.Constant;
import datacenter.git.client.demo.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class GitManagerChannel implements IGit {

    private static final Logger logger = LoggerFactory.getLogger(GitManagerChannel.class);

    public  String gitHost;
    public  String gitToken;
    public  int gitProjectId;

    public GitManagerChannel(String gitHost, String gitToken, int gitProjectId) {
        this.gitHost = gitHost;
        this.gitToken = gitToken;
        this.gitProjectId = gitProjectId;
    }

    @Override
    public GitResult<JSONObject> createBranch(String branch, String ref) {
        String createBranchUrl = GitManagerUrl.buildCreateBranchUrl(gitHost, gitProjectId, branch, ref);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.BRANCH, branch);
        jsonObject.put(Constant.REF, ref);
        return GitManagerHttp.doPost(createBranchUrl, gitToken, jsonObject.toJSONString());
    }

    @Override
    public GitResult<JSONObject> getBranch(String branch) {
        String showBranchUrl = GitManagerUrl.buildShowBranchUrl(gitHost, gitProjectId, branch);
        return GitManagerHttp.doGet(showBranchUrl, gitToken);
    }

    @Override
    public GitResult<JSONObject> deleteBranch(String branch) {
        String deleteBranchUrl = GitManagerUrl.buildDeleteBranchUrl(gitHost, gitProjectId, branch);
        return GitManagerHttp.delete(deleteBranchUrl, gitToken);
    }

    @Override
    public GitResult<JSONObject> createMR(String sourceBranch, String targetBranch, String title, Boolean removeSource) {
        String createMrUrl = GitManagerUrl.buildCreateMrUrl(gitHost, gitProjectId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.TITLE, title);
        jsonObject.put(Constant.SOURCE_BRANCH, sourceBranch);
        jsonObject.put(Constant.TARGET_BRANCH, targetBranch);
        jsonObject.put(Constant.REMOVE_SOURCE_BRANCH, removeSource);
        return GitManagerHttp.doPost(createMrUrl, gitToken, jsonObject.toJSONString());
    }

    @Override
    public GitResult<JSONObject> mergeRequest(Integer iid, Boolean squash, Boolean removeSource) {
        String mergeRequestUrl = GitManagerUrl.buildMergeRequestUrl(gitHost, gitProjectId, iid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.SQUASH, squash);
        jsonObject.put(Constant.SHOULD_REMOVE_SOURCE_BRANCH, removeSource);
        return GitManagerHttp.put(mergeRequestUrl, gitToken, jsonObject.toJSONString());
    }

    @Override
    public GitResult<JSONObject> compareBranchDiff(String from, String to) {
        String compareDiff = GitManagerUrl.buildCompareDiff(gitHost, gitProjectId, from, to);
        return GitManagerHttp.doGet(compareDiff,gitToken);
    }


    @Override
    public int getProjectIdByName(String projectName) {
        int projectId = 0;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String projectIdURL = GitManagerUrl.buildProjectIdUrl(gitHost, projectName);
        try {
            HttpGet httpGet = new HttpGet(projectIdURL);
            httpGet.setHeader("PRIVATE-TOKEN", gitToken);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String object = EntityUtils.toString(entity);
                JSONArray jsonArray = JSON.parseArray(object);
                if (jsonArray.size() > 1) {
                    logger.error("Please use precise query, projectName must be unique.");
                } else {
                    List<Integer> ids = new ArrayList<>();
                    jsonArray.forEach(json -> {
                        JSONObject jsonObject = JSON.parseObject(json.toString());
                        ids.add(jsonObject.getInteger("id"));
                    });
                    projectId = ids.get(0);
                }
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return projectId;
    }

    @Override
    public String downloadFileContent(String branch, String fullPath) {
        String result = null;
        String fileContentUrl = GitManagerUrl.buildFileContentUrl(gitHost, branch, fullPath, gitProjectId);
        GitResult<JSONObject> entity = GitManagerHttp.doGet(fileContentUrl, gitToken);
        String content = entity.getMessage().getString("content");
        if (content != null) {
            result = new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8);
        }
        return result;
    }

    @Override
    public GitResult<JSONObject> commitVersionContent(String committer) {
        String commitUrl = GitManagerUrl.buildCommitUrl(gitHost, gitProjectId);
        return GitManagerHttp.doPost(commitUrl, gitToken, committer);
    }

    @Override
    public List<FileTree> getRepositoryTree(boolean root, String filePath) {
        List<FileTree> fileTrees = new ArrayList<>();
        String treeUrl = GitManagerUrl.buildRepositoryTree(gitHost, gitProjectId, root, filePath);
        GitResult<JSONObject> entity = GitManagerHttp.doGet(treeUrl, gitToken);
        String result = entity.toString();
        if (StringUtils.isNotEmpty(result)) {
            fileTrees = JSON.parseArray(result).toJavaList(FileTree.class);
        }
        return fileTrees;
    }

    @Override
    public List<Version> getFileVersionList(String filePath) {
        List<Version> versionInfos = new ArrayList<>();
        String versionUrl = GitManagerUrl.buildFileVersionUrl(gitHost, gitProjectId, filePath);
        GitResult<JSONObject> entity = GitManagerHttp.doGet(versionUrl, gitToken);
        String result = entity.toString();
        if (StringUtils.isNotEmpty(result)) {
            versionInfos = JSON.parseArray(result).toJavaList(Version.class);
        }
        //只获取最近但5个版本
        return versionInfos;
    }

}
