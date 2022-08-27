package datacenter.git.client.demo;

import com.alibaba.fastjson.JSONObject;
import datacenter.git.client.demo.model.FileTree;
import datacenter.git.client.demo.model.GitResult;
import datacenter.git.client.demo.model.Version;

import java.util.List;

public interface IGit {

    GitResult<JSONObject> createBranch(String branch, String ref);

    GitResult<JSONObject> getBranch(String branch);

    GitResult<JSONObject> deleteBranch(String branch);

    GitResult<JSONObject> createMR(String sourceBranch, String targetBranch, String title, Boolean removeSource);

    GitResult<JSONObject> mergeRequest(Integer iid, Boolean squash, Boolean removeSource);

    GitResult<JSONObject> compareBranchDiff(String from, String to);

    /**
     * get projectId by projectName
     * @param projectName
     * @return
     */
    int getProjectIdByName(String projectName);

    /**
     * download file content
     * @param version
     * @param fullPath
     * @return
     */
    String downloadFileContent(String version, String fullPath);

    /**
     * commit sql files
     * the committer is the type of Committer object.
     * @param committer
     * @return
     */
    GitResult<JSONObject> commitVersionContent(String committer);

    /**
     * get dir tree
     * @param root
     * @param filePath
     * @return
     */
    List<FileTree> getRepositoryTree(boolean root, String filePath);

    /**
     * 指定版本回滚逻辑:
     * 提供文件的SHA
     * 可以根据SHA获取文件内容
     * 文件内容返回给前端窗口
     * 前端重新刷SQL开发界面
     * 如果用户点击保存后才会把回滚内容上传到Git仓库
     * @param filePath
     * @return
     */
    List<Version> getFileVersionList(String filePath);
}
