import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {


        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];
        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int low = 0;
        int high = index - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String a=taskArray[mid].getFinishTime();
            String b=taskArray[index].getStartTime();
            if (taskArray[mid].getFinishTime().compareTo(taskArray[index].getStartTime()) <= 0) {
                result = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        // YOUR CODE HERE
        for (int i = 0; i < taskArray.length; i++) {
            compatibility[i] = binarySearch(i);
        }
    }

    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        // YOUR CODE HERE

        calculateCompatibility();
        System.out.println("Calculating max array");
        System.out.println("---------------------");
        calculateMaxWeight(taskArray.length - 1);
        System.out.println();
        System.out.println("Calculating the dynamic solution");
        System.out.println("--------------------------------");
        solveDynamic(taskArray.length - 1);
        System.out.println();
        System.out.println("Dynamic Schedule");
        System.out.println("----------------");

        for (Task task : planDynamic) {
            System.out.println("At " + task.getStartTime() + ", " + task.getName() + ".");
        }

        return planDynamic;
    }
    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        if (i < 0) {
            return;
        }
        System.out.println("Called solveDynamic(" + i + ")");
        if (i == 0) {
            planDynamic.add(0, taskArray[0]);
            return;
        }
        double weight_with_task;
        if (compatibility[i] != -1) {
            weight_with_task =  maxWeight[compatibility[i]] + taskArray[i].getWeight() ;
        } else {
            weight_with_task = taskArray[i].getWeight();
        }

        if (weight_with_task >= maxWeight[i - 1]) {
            planDynamic.add(0, taskArray[i]);
            solveDynamic(compatibility[i]);
        } else {
            solveDynamic(i - 1);
        }
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    public Double calculateMaxWeight(int i) {
        // YOUR CODE HERE
        System.out.println("Called calculateMaxWeight(" + i + ")");
        if (i < 0) {
            return 0.0;
        }
        if (maxWeight[i] == null) {
            double weight_with_task = taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]);
            double weight_without_task = calculateMaxWeight(i - 1);
            if (weight_with_task >= weight_without_task) {
                maxWeight[i] = weight_with_task;
            } else {
                maxWeight[i] = weight_without_task;
            }
        }
        return maxWeight[i];
    }
    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Task> planGreedy() {
        // YOUR CODE HERE
        System.out.println("Greedy Schedule");
        System.out.println("---------------");
        int x = 0;
        planGreedy.add(taskArray[x]);

        for (int i = 1; i < taskArray.length; i++) {
            if (taskArray[x].getFinishTime().compareTo(taskArray[i].getStartTime()) <= 0) {
                planGreedy.add(taskArray[i]);
                x = i;
            }
        }
        for (Task task : planGreedy) {
            System.out.println("At " + task.getStartTime() + ", " + task.getName() + ".");
        }
        return planGreedy;
    }
}