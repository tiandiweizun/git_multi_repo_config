package indi.tiandi;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vfs.VirtualFile;
import git4idea.config.GitConfigUtil;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author tiandi
 * @date 2022/3/2
 */
public class GitCheckinHandlerFactory extends CheckinHandlerFactory {
    private static final Logger log = Logger.getInstance(GitRepositoryManager.class);

    @Override
    public @NotNull CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        Project project = panel.getProject();
        return new CheckinHandler() {
            @Override
            public ReturnResult beforeCheckin() {
                for (GitRepository repository : GitRepositoryManager.getInstance(project).getRepositories()) {
                    VirtualFile root = repository.getRoot();
                    try {
                        for (RepositorySettingsState.RepositoryConfig repoConfig : RepositorySettingsState.getInstance().repoConfigs) {
                            String url = getUrl(repository);
                            if (url == null) {
                                continue;
                            }
                            if (url.contains(repoConfig.url)) {
                                GitConfigUtil.setValue(project, root, GitConfigUtil.USER_NAME, repoConfig.name);
                                GitConfigUtil.setValue(project, root, GitConfigUtil.USER_EMAIL, repoConfig.email);
                                break;
                            }
                        }
                    } catch (VcsException exception) {
                        exception.printStackTrace();
                    }
                }
                return super.beforeCheckin();
            }
        };
    }

    public static String getUrl(GitRepository repository) {
        String url = null;
        Optional<GitRemote> first = repository.getRemotes().stream().findFirst();
        if (first.isPresent()) {
            GitRemote gitRemote = first.get();
            url = gitRemote.getFirstUrl();
        }
        return url;
    }
}
