package indi.tiandi;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author
 * @date 2022/3/3
 */
public class RepositorySettingsConfigurable implements Configurable {
    private RepositorySettingsPage page;


    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Multi Repo Config";
    }

    @Override
    public @Nullable JComponent createComponent() {
        if (page == null) {
            page = new RepositorySettingsPage();
        }
        return page.panel;
    }

    @Override
    public boolean isModified() {
        RepositorySettingsState settings = RepositorySettingsState.getInstance();
        int rowCount = page.table.getRowCount();
        if (settings.repoConfigs.size() != rowCount) {
            return true;
        }
        try {
            for (int i = 0; i < rowCount; i++) {
                RepositorySettingsState.RepositoryConfig repositoryConfig = settings.repoConfigs.get(i);
                if (!page.getValueAt(i, "email").equals(repositoryConfig.email)) {
                    return true;
                }
                if (!page.getValueAt(i, "url").equals(repositoryConfig.url)) {
                    return true;
                }
                if (!page.getValueAt(i, "name").equals(repositoryConfig.name)) {
                    return true;
                }
            }
        } catch (ColumnNameNotFoundException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    @Override
    public void apply() {
        RepositorySettingsState settings = RepositorySettingsState.getInstance();
        settings.repoConfigs.clear();
        for (int i = 0; i < page.table.getRowCount(); i++) {
            RepositorySettingsState.RepositoryConfig repositoryConfig = new RepositorySettingsState.RepositoryConfig();
            try {
                repositoryConfig.url = page.getValueAt(i, "url");
                repositoryConfig.name = page.getValueAt(i, "name");
                repositoryConfig.email = page.getValueAt(i, "email");
            } catch (ColumnNameNotFoundException e) {
                e.printStackTrace();
            }
            settings.repoConfigs.add(repositoryConfig);
        }
    }

    @Override
    public void reset() {
        RepositorySettingsState settings = RepositorySettingsState.getInstance();
        for (int i = 0; i < settings.repoConfigs.size(); i++) {
            RepositorySettingsState.RepositoryConfig repositoryConfig = settings.repoConfigs.get(i);
            try {
                page.setValueAt(repositoryConfig.url, i, "url");
                page.setValueAt(repositoryConfig.email, i, "email");
                page.setValueAt(repositoryConfig.name, i, "name");
            } catch (ColumnNameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void disposeUIResources() {
        page = null;
    }
}
