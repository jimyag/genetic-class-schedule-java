package cn.jimyag.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 文件加载器
 */
public class FileChooser extends JFrame implements ActionListener{

    public FileChooser(){
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        JFileChooser jfc=new JFileChooser();
//        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
//        jfc.showDialog(new JLabel(), "选择");
//        File file=jfc.getSelectedFile();
//        if(file.isDirectory()){
//            System.out.println("文件夹:"+file.getAbsolutePath());
//        }else if(file.isFile()){
//            System.out.println("文件:"+file.getAbsolutePath());
//        }
//        System.out.println(jfc.getSelectedFile().getName());
    }

    /**
     * 加载存放数据的文件
     * @return 文件的绝对路径
     */
    public String action(){
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
        }
//        System.out.println(jfc.getSelectedFile().getName());
        return file.getAbsolutePath();
    }

}