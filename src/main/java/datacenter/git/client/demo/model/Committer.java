package datacenter.git.client.demo.model;

import java.util.List;

public class Committer {

    private String branch = "master";

    private String commitMessage;

    private List<CommitActions> actions;


    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public List<CommitActions> getActions() {
        return actions;
    }

    public void setActions(List<CommitActions> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"branch\":\"")
                .append(branch).append('\"');
        sb.append(",\"commitMessage\":\"")
                .append(commitMessage).append('\"');
        sb.append(",\"actions\":")
                .append(actions);
        sb.append('}');
        return sb.toString();
    }
}
