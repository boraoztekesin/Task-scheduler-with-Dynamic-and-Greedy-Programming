import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        // YOUR CODE HERE
        int hour=Integer.parseInt(start.substring(0,2))+duration;
        //System.out.println(hour);

       // LocalTime time=LocalTime.parse("10.00");
        int minutes=Integer.parseInt(start.substring(start.length()-2));
        //System.out.println(minutes);

        if(minutes==0){
          //  System.out.println(String.valueOf(hour)+":00");
            if (hour < 10) {
                return "0"+String.valueOf(hour)+":00";
            }else {
                return String.valueOf(hour)+":00";
            }

        }else {
            if (hour < 10) {
                return "0"+String.valueOf(hour)+":"+start.substring(start.length()-2);
            }else {
                return String.valueOf(hour)+":"+start.substring(start.length()-2);
            }

        }



    }


    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        // YOUR CODE HERE
        return (double) importance*(urgent? 2000 : 1)/duration;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        // YOUR CODE HERE
        if (o instanceof Task) {
            Task o1 = (Task) o;
            return this.getFinishTime().compareTo(o1.getFinishTime());
        }
            return -1;
    }
}
