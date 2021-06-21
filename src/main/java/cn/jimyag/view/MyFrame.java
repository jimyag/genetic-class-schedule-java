package cn.jimyag.view;

import cn.jimyag.GeneticOptimize;
import cn.jimyag.model.Student;
import org.json.JSONObject;
import cn.jimyag.util.Parser;
import cn.jimyag.util.TwoTuple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MyFrame extends JFrame {



    GeneticOptimize ga = new GeneticOptimize();


    private JPanel panel;//控制面板
    private JButton startGAButton;//遗传算法
    MyTable table = null;
    MyTable myTable = new MyTable();


    public JTextField textField;
    private JLabel status;
    public String Dir;

    private java.util.List<Student> students;

    public MyFrame(String title) throws HeadlessException {
        super(title);
        init();
    }

    private void init() {
        setBounds(100, 100, 450, 300);//设置界面大小
        //用户单击窗口的关闭按钮时程序执行的操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createTable();

    }

    private void createTable() {
        myTable = new MyTable();
        table = myTable;
        // Data to be displayed in the JTable
        //滚动面板 cn.jimyag.view: 需要滚动显示的视图组件
        JScrollPane s = new JScrollPane(table.getTable());
        //获得面板 添加组件S 面板放在中心
        getContentPane().add(s, BorderLayout.CENTER);
        setTitle("JAVA Course Scheduling System Based on Genetic Algorithm");
        pack();//根据窗口里面的布局及组件的preferredSize来确定frame的最佳大小。
        setVisible(true);//根据提供的模型开始画图
        //在窗口添加一个Windows事件消息，目的是我们关闭窗口的时候可以正常的退出
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(1200, 700);
        //控件
        panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);//控件放在下面
        status = new JLabel("排课");
        panel.add(status);
        status.setPreferredSize(new Dimension(150, 15));//宽度随窗口变化。

        status.setText("排课");

        startGAButton = new JButton("开始排课(GA)");
        //排课按钮的监听器
        startGAButton.addActionListener(arg0 -> {
            if (Dir == null) {
                JOptionPane.showMessageDialog(null, "请先加载文件资源", "注意", JOptionPane.ERROR_MESSAGE);
            }
            myTable.clear();
            table.getTable().repaint();//系统重新绘制表格

            TwoTuple<List<Student>, Integer> res = ga.evolution(students);
            List<Student> resStu = res.first;
            myTable.fillingAllInfo(resStu);

//            System.out.println("1111");
        });
        panel.add(startGAButton);

        // 测试用的按钮
        JButton button = new JButton("计算器");
        button.addActionListener(arg0->{
            try {
                Runtime.getRuntime().exec("calc");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        panel.add(button);




        table.getTable().repaint();//系统重新绘制表格


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        JMenu fmenu = new JMenu("文件");
        menuBar.add(fmenu);
        JMenuItem fMenuLoad = new JMenuItem("txt或json导入");
        fMenuLoad.addActionListener(Event -> {
            //加载文件
            FileChooser fc = new FileChooser();
            //获得文件路径
            Dir = fc.action();
            //文件名不合法处理
            if (Dir.contains(".txt") || Dir.contains(".json")) {
                //加载文件
                String str = Objects.requireNonNull(Parser.fileToString(Dir));
                //读取文件内容
                JSONObject jsonObject = Parser.strToJson(str);
                //文件内容转对象
                this.students = Parser.jsonToStuObj(jsonObject);

            } else {
                JOptionPane.showMessageDialog(null, "文件格式不合法", "注意", JOptionPane.ERROR_MESSAGE);
                Dir = null;
            }
        });
        fmenu.add(fMenuLoad);


    }


}
