package datacenter.git.client.demo;

import com.alibaba.fastjson.JSONObject;
import datacenter.git.client.demo.git.GitManagerChannel;
import datacenter.git.client.demo.model.GitResult;

public class Application {

    private static final String GIT_HOST = "";

    private static final String GIT_TOKEN = "";

    private static final int PROJECT_ID = 3239;

    public static void main(String[] args) {

        GitManagerChannel gitManagerChannel = new GitManagerChannel(GIT_HOST, GIT_TOKEN, PROJECT_ID);

        GitResult<JSONObject> channelMR = gitManagerChannel.createMR(
                "master",
                "dev",
                "create MR Process",
                false);

        GitResult<JSONObject> deleteBranch = gitManagerChannel.deleteBranch("dev");


        String fileContent = gitManagerChannel.downloadFileContent(
                "master",
                "/dolphinScheduler/code/job_1921.hql");
    }
}
