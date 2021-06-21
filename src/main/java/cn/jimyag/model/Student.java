package cn.jimyag.model;

import cn.jimyag.util.TwoTuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Student {
    private final String name;//学生姓名
    private int workCount;//工作次数
    private List<WorkTime> workTimes;//工作时间
    private final List<WorkTime> freeTimes;//空课时间
    private final Random random = new Random();


    public Student(String name, int workCount, List<WorkTime> freeTimes) {
        this.name = name;
        this.workCount = workCount;
        this.freeTimes = freeTimes;
        this.workTimes = new ArrayList<>(workCount);
        randomInit();
    }



    /**
     * 随机初始胡学生的值班时间
     */
    private void randomInit() {
        for (int i = 0; i < this.workCount; i++) {
            int index = this.random.nextInt(this.freeTimes.size());
            while (this.workTimes.contains(this.freeTimes.get(index))) {
                index = this.random.nextInt(this.freeTimes.size());
            }
            this.workTimes.add(this.freeTimes.get(index));
        }
    }

    @Override
    protected Student clone() {
        return new Student(name, workCount, freeTimes);
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public List<WorkTime> getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(ArrayList<WorkTime> workTimes) {
        this.workTimes = workTimes;
    }
    public void setWorkTimes(int index,WorkTime workTime){
        workTimes.set(index,workTime);
    }

    public int getFreeTimeCount() {
        return freeTimes.size();
    }

    public List<WorkTime> getFreeTimes() {
        return freeTimes;
    }

    static public List<Student> copy (List<Student> students){
        List<Student> res = new ArrayList<>();
        for (Student student : students) {
            res.add(student.clone());
        }
        return res ;
    }

    public String getName() {
        return name;
    }

    /**
     * 找到种群中最好的群体
     *
     * @param population 种群
     * @param elite      规定的好种群的个数
     * @return 返回最佳结果指标 最低的冲突分数
     */
    static public TwoTuple<List<Integer>, Integer> studentScore(List<List<Student>> population, int elite) {
        int n = population.get(0).size();//总共有多少个学生
        ArrayList<Integer> conflicts = new ArrayList<>(n);
        for (List<Student> students : population) {
            int conflict = 0;
            //找到某个学生A
            for (int i = 0; i < (n - 1); i++) {
                //找到另一个学生B
                for (int j = i + 1; j < n; j++) {
                    //找到A学生的工作次数
                    for (int i_work = 0; i_work < students.get(i).workCount; i_work++) {
                        //找到B学生的工作次数
                        for (int j_work = 0; j_work < students.get(j).workCount; j_work++) {
                            if (students.get(i).workTimes.get(i_work).equals(students.get(j).workTimes.get(j_work)) ) {
                                conflict++;
                            }
                        }
                    }
                }
            }
            //冲突的次数
            conflicts.add(conflict);
        }
        //冲突次数按照从小到大排序
        List<Integer> index = sort(conflicts);
        //返回最佳结果指标 最低的冲突分数
        return new TwoTuple<>(index.subList(0, elite), conflicts.get(index.get(0)));


    }


    /**
     * 对一个List进行排序，返回排序后结果对应的索引
     *
     * @param conflicts 待排序的List
     * @return 排序后的索引
     */
    static public ArrayList<Integer> sort(List<Integer> conflicts) {
        int n = conflicts.size();
//        System.out.println(n);
        ArrayList<Integer> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            result.add(i);
        }
        result.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (conflicts.get(o1) - conflicts.get(o2));
            }
        });
        return result;
    }

    @Override
    public String toString() {
        return "\ncn.jimyag.model.Student{" +
                "name='" + name + '\'' +
                ", workCount=" + workCount +
                ", workTimes=" + workTimes +
                '}';
    }
}
