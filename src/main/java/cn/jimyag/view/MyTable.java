package cn.jimyag.view;

import cn.jimyag.model.Student;
import cn.jimyag.model.WorkTime;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

class MyTable extends AbstractTableModel {//使用AbstractTableModel来创建表格模型


    JTable table;
    //表格数据
    Object[][] rowData =
            {
                    {"1-2", null, null, null, null, null},
                    {"3-4", null, null, null, null, null},
                    {"5-6", null, null, null, null, null},
                    {"7-8", null, null, null, null, null},
            };

    String[] columnNames = {"", "\u661F\u671F\u4E00", "\u661F\u671F\u4E8C", "\u661F\u671F\u4E09", "\u661F\u671F\u56DB", "\u661F\u671F\u4E94"};

    public JTable getTable() {
        return table;
    }

    public MyTable() {
        table = new JTable(rowData, columnNames);
        table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return rowData[row][col];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {    //判断单元格是否可以编辑
        return true;
    }

    public void setValueAt(int row, int col, Object value) {
        table.setValueAt(value, row, col);
    }


    /**
     * 初始化表格
     */
    public void clear() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 1; j <= getColumnCount() - 1; j++) {
                rowData[i][j] = "-";
            }
        }
    }

    /**
     * 处理单个人的信息 将单个人的值班信息更新
     * @param student  学生
     */
    public void fillingInformation(Student student) {
        String name = student.getName();
        List<WorkTime> workTimes = student.getWorkTimes();
        for (WorkTime workTime : workTimes) {
            rowData[workTime.getSerialNumber()-1][workTime.getDay()] = name;
        }
    }

    /**
     * 处理所有同学的信息
     * @param students 所有同学列表
     */
    public void fillingAllInfo(List<Student> students){
        for (Student student : students) {
            fillingInformation(student);
        }
        
    }

    static class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TableCellTextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            // 计算当下行的最佳高度
            int maxPreferredHeight = 0;
            for (int i = 0; i < table.getColumnCount(); i++) {
                setText("" + table.getValueAt(row, i));
                setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
                maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
            }

            if (table.getRowHeight(row) != maxPreferredHeight)  // 少了这行则处理器瞎忙
            {
                table.setRowHeight(row, maxPreferredHeight);
            }

            setText(value == null ? "" : value.toString());
            return this;
        }
    }
}
