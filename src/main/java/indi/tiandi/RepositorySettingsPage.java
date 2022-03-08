package indi.tiandi;

import com.intellij.openapi.util.Pair;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.*;


public class RepositorySettingsPage {
    JPanel panel;
    JTable table;
    Map<String, Integer> columnName2index = new HashMap<>();


    public RepositorySettingsPage() {
        Class configClass = RepositorySettingsState.RepositoryConfig.class;
        List<Pair<Integer, Pair<String, String>>> columnNames = new ArrayList<>();
        for (Field field : configClass.getFields()) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation == null) {
                continue;
            }
            String fieldName = field.getName();
            String tableColumnName = annotation.name().trim();
            if (tableColumnName.length() == 0) {
                tableColumnName = fieldName;
            }
            columnNames.add(new Pair<>(annotation.index(), new Pair<>(tableColumnName, fieldName)));
        }
        columnNames.sort(Comparator.comparing(o -> o.first));
        String[] tabColumnNames = new String[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            tabColumnNames[i] = columnNames.get(i).second.first;
            columnName2index.put(tabColumnNames[i], i);
            // fieldName
            columnName2index.put(columnNames.get(i).second.second, i);
        }
        table = new JBTable(new DefaultTableModel(tabColumnNames, 10));
        panel = FormBuilder.createFormBuilder()
                .addComponent(new JScrollPane(table))
                .getPanel();
    }

    String getValueAt(int row, int column) {
        Object value = table.getValueAt(row, column);
        return value == null ? "" : value.toString().trim();
    }

    String getValueAt(int row, String columnName) throws ColumnNameNotFoundException {
        Integer index = columnName2index.getOrDefault(columnName, -1);
        if (index < 0) {
            throw new ColumnNameNotFoundException(columnName + "does not found!!");
        }
        return getValueAt(row, index);
    }

    void setValueAt(Object value, int row, String columnName) throws ColumnNameNotFoundException {
        Integer index = columnName2index.getOrDefault(columnName, -1);
        if (index < 0) {
            throw new ColumnNameNotFoundException(columnName + "does not found!!");
        }
        table.setValueAt(value, row, index);
    }
}

