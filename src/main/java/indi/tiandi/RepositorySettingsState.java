package indi.tiandi;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 储兵兵
 * @date 2022/3/3
 */
@State(
        name = "indi.tiandi.git_multi_repo_config",
        storages = @Storage("other.xml")
)
public class RepositorySettingsState implements PersistentStateComponent<RepositorySettingsState> {
    public List<RepositoryConfig> repoConfigs = new ArrayList<>();


    public static RepositorySettingsState getInstance() {
        return ApplicationManager.getApplication().getService(RepositorySettingsState.class);
    }

    @Nullable
    @Override
    public RepositorySettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull RepositorySettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    /**
     * @author 储兵兵
     * @date 2022/3/4
     */
    public static final class RepositoryConfig {
        @Column(name = "repository")
        public String url;
        @Column
        public String name;
        @Column
        public String email;

        @Override
        public String toString() {
            return "url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}