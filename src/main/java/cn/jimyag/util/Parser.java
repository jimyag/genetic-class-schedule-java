package cn.jimyag.util;

import cn.jimyag.model.Student;
import cn.jimyag.model.WorkTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    /**
     * 读文件，将文件内容转换为String
     *
     * @param fileName 文件的地址
     * @return 文件的内容
     */
    static public String fileToString(String fileName) {
        try {
//            System.out.println(Files.readString(Paths.get(fileName)));
            return new String(Files.readString(Paths.get(fileName)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 将String转换为Json
     *
     * @param str 被转换的字符串
     * @return 转换的结果
     */
    static public JSONObject strToJson(String str) {
        System.out.println(str);
        return new JSONObject(str);
    }


    /**
     * json解析到WorkTime
     *
     * @param jsonObject 待解析的json
     * @return WorkTime对象
     */
    private static WorkTime jsonToWObj(JSONObject jsonObject) {
        int weekDay = jsonObject.getInt("weekDay");
        int index = jsonObject.getInt("index");
        return new WorkTime(weekDay, index);
    }


    /**
     * json转换为Student对象
     *
     * @param jsonObject 代解析的json
     * @return 解析完成的Student对象
     */
    public static List<Student> jsonToStuObj(JSONObject jsonObject) {
        List<Student> students = new ArrayList<>();//存放学生

        //遍历每一个学生
        for(String key:jsonObject.keySet()){
            JSONObject keyJs = jsonObject.getJSONObject(key);
//            System.out.println(keyJs);
            int workCount = keyJs.getInt("workCount");
            JSONArray jsonFreeTimes = keyJs.getJSONArray("freeTimes");
            List<WorkTime> freeTimes = new ArrayList<>();
            for (Object jsonFreeTime : jsonFreeTimes) {
                freeTimes.add(jsonToWObj((JSONObject) jsonFreeTime));
            }
            students.add(new Student(key, workCount, freeTimes));
        }
        return students;
//        String name = jsonObject.getString("name");
//        int workCount = jsonObject.getInt("workCount");
//        JSONArray jsonFreeTimes = jsonObject.getJSONArray("freeTimes");
//        List<cn.jimyag.model.WorkTime> freeTimes = new ArrayList<>();
//        for (Object jsonFreeTime : jsonFreeTimes) {
//            freeTimes.add(jsonToWObj((JSONObject) jsonFreeTime));
//        }
//        return new cn.jimyag.model.Student(name, workCount, freeTimes);

    }


}

