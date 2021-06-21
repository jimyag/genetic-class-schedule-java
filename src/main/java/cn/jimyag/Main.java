package cn.jimyag;

import cn.jimyag.view.MyFrame;

public class Main {

    public static void main(String[] args) {

//
//        String str =  cn.jimyag.util.Parser.fileToString("data.txt");
//        JSONObject jsonObject =cn.jimyag.util.Parser.strToJson(str);
//        List<cn.jimyag.model.Student> students = cn.jimyag.util.Parser.jsonToStuObj(jsonObject);
//        cn.jimyag.GeneticOptimize geneticOptimize = new cn.jimyag.GeneticOptimize(100,10,100);
//        cn.jimyag.util.TwoTuple<List<cn.jimyag.model.Student>,Integer> res =  geneticOptimize.evolution(students);
//        System.out.println(res);

        new MyFrame("JAVA Course Scheduling System Based on Genetic Algorithm").setVisible(true);
    }

}
