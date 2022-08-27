package datacenter.git.client.demo.git;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GitManagerUrl {

    /**
     * 获取项目ID
     * @param gitHost
     * @param projectName
     * @return
     */
    public static String buildProjectIdUrl(String gitHost,String projectName) {
        return String.format("https://%s/api/v4/projects?search=%s",gitHost,projectName);
    }

    /**
     * 获取文件内容
     * @param gitHost
     * @param version
     * @param fullPath
     * @param projectId
     * @return
     */
    public static String buildFileContentUrl(String gitHost,String version, String fullPath, int projectId) {
        try {
            fullPath = URLEncoder.encode(fullPath,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format("https://%s//api/v4/projects/%d/repository/files/%s/?ref=%s",gitHost,projectId,fullPath,version);
    }

    /**
     * 单个请求提交多个文件
     * @param gitHost
     * @param projectId
     * @return
     */
    public static String buildCommitUrl(String gitHost,int projectId) {
        return String.format("https://%s//api/v4/projects/%d/repository/commits", gitHost, projectId);
    }

    /**
     * 创建文件路径
     * @param gitHost
     * @param projectId
     * @param filePath
     * @return
     */
    public static String buildCreateFileUrl(String gitHost,int projectId,String filePath) {
        try {
            filePath = URLEncoder.encode(filePath,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format("https://%s//api/v4/projects/%d/repository/files/%s",gitHost, projectId, filePath);
    }

    /**
     * 项目文件分布树
     * @param gitHost
     * @param projectId
     * @param root
     * @param filePath
     * @return
     */
    public static String buildRepositoryTree(String gitHost,int projectId,boolean root,String filePath) {
        if (root) {
            return String.format("https://%s//api/v4/projects/%d/repository/tree", gitHost, projectId);
        } else {
           return String.format("https://%s//api/v4/projects/%d/repository/tree?path=%s", gitHost, projectId, filePath);
        }
    }

    /**
     * 文件版本管理
     * @param gitHost
     * @param projectId
     * @param filePath
     * @return
     */
    public static String buildFileVersionUrl(String  gitHost,int projectId, String filePath) {
        return String.format("https://%s//api/v4/projects/%d/repository/commits?path=%s", gitHost, projectId, filePath);
    }

    /**
     * 创建分支接口
     * @param gitHost
     * @param projectId
     * @param branch
     * @param ref
     * @return
     */
    public static String buildCreateBranchUrl(String  gitHost,int projectId, String branch, String ref) {
        return String.format("https://%s/api/v4/projects/%d/repository/branches?branch=%s&ref=%s", gitHost, projectId, branch, ref);
    }

    /**
     * 查询特定分支
     * @param gitHost
     * @param projectId
     * @param branch
     * @return
     */
    public static String buildShowBranchUrl(String  gitHost,int projectId, String branch) {
        return String.format("https://%s/api/v4/projects/%d/repository/branches/%s", gitHost, projectId, branch);
    }

    /**
     * 删除特定分支
     * @param gitHost
     * @param projectId
     * @param branch
     * @return
     */
    public static String buildDeleteBranchUrl(String  gitHost,int projectId, String branch) {
        return String.format("https://%s/api/v4/projects/%d/repository/branches/%s", gitHost, projectId, branch);
    }

    /**
     * 创建MR接口
     * @param gitHost
     * @param projectId
     * @return
     */
    public static String buildCreateMrUrl(String gitHost, int projectId) {
        return String.format("https://%s/api/v4/projects/%d/merge_requests", gitHost, projectId);
    }

    /**
     * 提交Merge请求
     * @param gitHost
     * @param projectId
     * @param iid
     * @return
     */
    public static String buildMergeRequestUrl(String gitHost, int projectId, int iid) {
        return String.format("https://%s/api/v4/projects/%d/merge_requests/%d/merge", gitHost, projectId, iid);
    }

    /**
     * 文件对比
     * @param gitHost
     * @param projectId
     * @param from
     * @param to
     * @return
     */
    public static String buildCompareDiff(String gitHost, int projectId, String from, String to) {
        return String.format("https://%s/api/v4/projects/%d/repository/compare?from=%s&to=%s&straight=true", gitHost, projectId, from, to);
    }
}
