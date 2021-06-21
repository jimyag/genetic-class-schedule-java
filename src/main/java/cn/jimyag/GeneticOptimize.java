package cn.jimyag;

import cn.jimyag.model.Student;
import cn.jimyag.util.TwoTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 遗传算法安排课程
 */
public class GeneticOptimize {
    private List<List<Student>> population;//种群
    private final int populationSize;//种群规模
    private final int elite;//精英个位
    private final int maxIter;//最大迭代次数
    private final Random random = new Random();

    public GeneticOptimize(int populationSize, int elite, int maxIter) {
        this.populationSize = populationSize;
        this.elite = elite;
        this.maxIter = maxIter;
        population = new ArrayList<>(populationSize);
    }

    public GeneticOptimize() {
        this(100,10,50);
    }

    public void initPopulation(List<Student> students) {
        for (int i = 0; i < this.populationSize; i++) {
            List<Student> studentsCopy = Student.copy(students);
            population.add(studentsCopy);
        }
    }

    /**
     * 精英种群中变异
     *
     * @param elitePopulation 精英种群
     * @return 遍历后的群体
     */
    public List<Student> mutate(List<List<Student>> elitePopulation) {
        int mutateIndex = random.nextInt(this.elite);
        List<Student> partElitePopulation = Student.copy(population.get(mutateIndex));
        for (Student student : partElitePopulation) {
            //如果这位同学的值班次数大于1 就让他的一次值班时间变异
            if (student.getWorkCount() > 1 && (student.getWorkCount() != student.getFreeTimeCount())) {
                //随机更换值班的时间
                int ranWokTimIndex = random.nextInt(student.getWorkCount());
                //从空闲的时间中抽一个没有值班的时间出来
                int ranFreeTimIndex = random.nextInt(student.getFreeTimeCount());
                //如果抽出来的空闲的时间在值班的时间，就继续抽值班时间
                while (student.getWorkTimes().contains(student.getFreeTimes().get(ranFreeTimIndex))) {
                    ranWokTimIndex = random.nextInt(student.getWorkCount());
                    ranFreeTimIndex = random.nextInt(student.getFreeTimeCount());
                }
                //设置新的值班时间
                student.setWorkTimes(ranWokTimIndex, student.getFreeTimes().get(ranFreeTimIndex));
            }
        }
        return partElitePopulation;
    }

    /**
     * 算法
     * @param students 待排课的学生
     * @return 排完课的学生信息
     */
    public TwoTuple<List<Student>, Integer> evolution(List<Student> students) {

        int bestScore = 0;
        initPopulation(students);

        List<Integer> eliteIndex = null;//精英种群的下标
        for (int i = 0; i < this.maxIter; i++) {
            //给种群进行打分 返回精英种群的下标和最好的分数
            TwoTuple<List<Integer>, Integer> cost = Student.studentScore(population, this.elite);
            bestScore = cost.second;

            System.out.printf("Iter : %d | conflict : %d", i + 1, bestScore);
            System.out.println();

            eliteIndex = cost.first;
            //如果没有冲突则就是最优的
            if (cost.second.equals(0)) {
                System.out.printf("Iter : %d", i + 1);
                System.out.println();
                return new TwoTuple<>(this.population.get(eliteIndex.get(0)), cost.second);
            }

            //如果有冲突则对种群变异
            List<List<Student>> newPopulation = new ArrayList<>();

            // 由精英种群产生新的种群
            for (Integer index : eliteIndex) {
                newPopulation.add(Student.copy(this.population.get(index)));
            }
            while (newPopulation.size() < this.populationSize) {
                List<Student> newStudents = this.mutate(newPopulation);
                newPopulation.add(newStudents);
            }
            this.population = newPopulation;
        }
        if (eliteIndex != null) {
            return new TwoTuple<>(this.population.get(eliteIndex.get(0)), bestScore);
        }
        return null;

    }


}
