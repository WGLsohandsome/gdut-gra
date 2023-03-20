import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WGL
 * Date: 2022-10-17
 * Time: 15:41
 */
class Speciality_mark {
    Speciality tar = null;
    float mark = 0;
}


public class Student implements Comparable<Student> {
    int rank;
    int numOfFavouriteSpeciality = 0;
    int numOfHateSpeciality = 0;
    int getNumOfEnrollmentTeamNum;
    String personality;
    float[] expectationToSpeciality;
    Boolean[] tagToAdjustment;
    Boolean hasTheVery = false;
    Boolean isRecruitedGdut = false;
    Boolean isRecurited = false;
    Speciality theVery = null;
    Speciality recruitedOne = null;
    Speciality_mark[] favouriteSpeciality = new Speciality_mark[6];
    Speciality_mark[] hateSpeciality = new Speciality_mark[6];
    ArrayList<EnrollmentTeam> registeredTeam = new ArrayList<>();

    @Override
    public String toString() {
        return "Student{" +
                "rank=" + rank +
                ", personality='" + personality + '\'' +
                ", expectationToSpeciality=" + Arrays.toString(expectationToSpeciality) +
                '}';
    }

    private static int getNumArea1(Random random) {
        int tag = random.nextInt(80000);
        long tmp = tag * 4000000L;
        int t = random.nextInt(2100);
        int ret = (int) (((Math.log(tmp) - 1) * 2100 + t - 27000));

        return (int) ret;
    }

    private static int getNumArea2(Random random, Stack<Integer> stack) {
//        int tag = random.nextInt(43665);
//        while (tag<3000){
//            tag = random.nextInt(43665);
//        }
//        double dert = 1600*1600-4*15*(1000-tag);
//        double x1,x2;
//        x1 = (-1600+Math.sqrt(dert)/15*2);
//        x2 = (1600+Math.sqrt(dert)/15*2);
//        if(x1>0&&x2>0){
//            int tmp = random.nextInt(10);
//            if(tmp>=5){
//                return (int) x1;
//            }else {
//                return (int) x2;
//            }
//        }
//        return (int) (x1>0?x1*318-55000:x2*318-550000);
        int tag = 0, p = 0, pos = 0;
        while (!stack.isEmpty()) {
            tag = stack.pop();
            p = tag - 416;
            if (p < 0) continue;
            pos = p / 13;
            if (pos < 14) break;
        }
        int x = random.nextInt(2153);
        int ret = (pos - 1) * 2153 + 28000 + x;
        return ret;
    }

    public static int getNum(Random random, Stack<Integer> stack, int i) {
        int tag = random.nextInt(100);
        if (i <= 600) {
            return getNumArea1(random);
        }
        return getNumArea2(random, stack);

    }


    Student(Random random, Stack<Integer> stack, int num, Stack<Integer> stack1) {
        expectationToSpeciality = new float[4];
        Arrays.fill(this.expectationToSpeciality, 0);
//        this.rank = getNum(random,stack,num);
        this.rank = getRank(random, stack1);
        this.personality = createPersonality(random);
        getExpectationToSpeciality(random);
        for (int i = 0; i < 6; i++) {
            this.favouriteSpeciality[i] = new Speciality_mark();
            this.hateSpeciality[i] = new Speciality_mark();
        }
        this.numOfFavouriteSpeciality = this.numOfHateSpeciality = 0;

    }

    //
    private String createPersonality(Random random) {
        double tag = random.nextDouble();
        if (0 < tag && tag < 0.05) return "extremeCautious";
        if (tag > 0.05 && tag < 0.25) return "cautious";
        if (tag > 0.25 && tag < 0.75) return "common";
        if (tag > 0.75 && tag < 0.95) return "radical";
        if (tag > 0.95) return "extremeRadical";
        return null;
    }

    //    根据性格判断专业的排名是否符合学生的排名
    public Boolean isRankSuit(Speciality tar, Random random) {
//        int rank = tar.exactNum;
//        String Personality = this.personality;
//        switch (Personality) {
//            case "common":
//                return  this.rank <= rank;
//            case "cautious":
//                return this.rank + 1000 < rank;
//            case "extremeCautious":
//                return this.rank + 2000 < rank;
//            case "radical":
//                    if(this.rank<rank){
//                        return true;
//                    }else {
//                        return rank + 2000 > this.rank ;
//                    }
//            case "extremeRadical":
//                    if(this.rank<rank){
//                         return true;
//                    }else {
//                        return rank + 5000 > this.rank ;
//                    }
//            default:
//                return false;
//        }
        int tag = random.nextInt(5000);
        return Math.abs(tar.exactNum - this.rank) < tag;
    }

    private int getRank(Random random, Stack<Integer> stack1) {
        while (!stack1.isEmpty()) {
            int tag = stack1.pop();
            if (tag < 1500 || tag > 4500) continue;
            return tag * 9 + random.nextInt(3000) + 10000;
        }
        return -1;
    }

//百分之一的几率有个天命专业
//    找出喜欢的6个专业和不喜欢的六个专业.如果此学生没有一个喜欢的专业就认为不会报名


    public void judgeSpeciality(Random random, Speciality[] Tar) {
//        proportion = (float) (random.nextGaussian()*Math.sqrt(0.05)+proportion);
        Speciality_mark[] favouriteSpeciality = new Speciality_mark[6];
        float markToSpeciality = 0;
        if (!this.hasTheVery) {
            float tmp = random.nextFloat();
            if (tmp < 0.01) {
                int pos = Math.abs(random.nextInt() % 35);
                this.hasTheVery = true;
                this.theVery = Tar[pos];
                markToSpeciality = Float.MAX_VALUE;
            }
        }
        for (int i = 0; i < 35; i++) {
            if (!isRankSuit(Tar[i], random) && !this.hasTheVery) {
                continue;
            } else {
                markToSpeciality = getMarkToSpeciality(Tar[i]);
            }
            if (markToSpeciality > 0) {
                if (this.numOfFavouriteSpeciality < 6) {
                    this.favouriteSpeciality[this.numOfFavouriteSpeciality].tar = Tar[i];
                    this.favouriteSpeciality[this.numOfFavouriteSpeciality].mark = markToSpeciality;
                    this.numOfFavouriteSpeciality++;
                } else {
                    int pos = findLowestMarkPos();
                    if (markToSpeciality > this.favouriteSpeciality[pos].mark) {
                        this.favouriteSpeciality[pos].tar = Tar[i];
                        this.favouriteSpeciality[pos].mark = markToSpeciality;
                    }
                }
            } else {
                if (this.numOfHateSpeciality < 6) {
                    this.hateSpeciality[this.numOfHateSpeciality].tar = Tar[i];
                    this.hateSpeciality[this.numOfHateSpeciality].mark = markToSpeciality;
                    this.numOfHateSpeciality++;
                } else {
                    int pos = findLowestMarkPos();
                    if (markToSpeciality < this.favouriteSpeciality[pos].mark) {
                        this.favouriteSpeciality[pos].tar = Tar[i];
                        this.favouriteSpeciality[pos].mark = markToSpeciality;
                    }
                }
            }
        }
    }

    private int findLowestMarkPos() {
        float tmp = this.favouriteSpeciality[0].mark;
        int pos = 0;
        for (int i = 1; i < this.favouriteSpeciality.length; i++) {
            if (tmp < this.favouriteSpeciality[i].mark) {
                tmp = this.favouriteSpeciality[i].mark;
                pos = i;
            }
        }
        return pos;
    }

    private float getMarkToSpeciality(Speciality speciality) {
        float sum = 0;
        int[] tag = {10, 20, 30, 20, 20};
        for (int i = 0; i < this.expectationToSpeciality.length; i++) {
            sum += speciality.Features[i] - this.expectationToSpeciality[i];
        }
        return sum;
    }
    //    public int findPos(Speciality_mark[] Tar,float markToSpeciality){
//        if(markToSpeciality>0){
//            int pos = 0;
//            float tmp = Tar[0].mark;
//            for (int i = 1; i < Tar.length; i++) {
//                if(tmp< Tar[i].mark){
//                    pos = i;
//                    tmp = Tar[i].mark;
//                }
//            }
//        }else {
//            int pos = 0;
//            float tmp = Tar[0].mark;
//            for (int i = 1; i > Tar.length; i++) {
//                if (tmp < Tar[i].mark) {
//                    pos = i;
//                    tmp = Tar[i].mark;
//                }
//            }
//            return pos;
//        }

    //    如果对这个专业的评分大于0就认为可能是喜欢的专业,小于0就表示可能是不喜欢的专业
    private void insertSpecialty(Speciality_mark[] favouriteSpeciality, Speciality_mark[] hateSpeciality, float markToSpeciality, Speciality speciality) {
        if (markToSpeciality > 0) {
//            先往里面填六个,不然回有空指针访问的bug
            for (int i = 0; i < 6; i++) {
                if (markToSpeciality > favouriteSpeciality[i].mark) {
                    float tmp = favouriteSpeciality[i].mark;
                    Speciality tmpSpecialty = favouriteSpeciality[i].tar;
                    favouriteSpeciality[i].mark = markToSpeciality;
                    favouriteSpeciality[i].tar = speciality;
                    speciality = tmpSpecialty;
                    markToSpeciality = tmp;
                }

            }
        } else {
            for (int i = 0; i < 6; i++) {
                if (markToSpeciality < hateSpeciality[i].mark) {
                    float tmp = hateSpeciality[i].mark;
                    Speciality tmpSpecialty = hateSpeciality[i].tar;
                    hateSpeciality[i].mark = markToSpeciality;
                    hateSpeciality[i].tar = speciality;
                    speciality = tmpSpecialty;
                    markToSpeciality = tmp;
                }
            }
        }
    }

    public void getEnrollmentTeam(ArrayList<EnrollmentTeam> enrollmentTeamToRegister) {
//        如果专业组里有学生认定的专业,不考虑其他因素.直接报名,所以先遍历专业组看有没有那个专业
        PriorityQueue<Float> queue = new PriorityQueue<>();
        HashMap<Float, Integer> map = new HashMap<>();
        int n = 0;
        if (this.hasTheVery) {
            for (EnrollmentTeam enrollmentTeam : enrollmentTeamToRegister) {
                if (enrollmentTeam.isContainSpeciality(this.theVery)) {
                    this.registeredTeam.add(0, enrollmentTeam);
//                    System.out.print("有唯一喜欢的专业");
                    n++;
                }
            }
        }
//       找完最喜欢的专业之后就开始对所有的专业组进行一个遍历,看下是否合适,取一个标准值,最多选出最牛逼的六个
        float[][] marks = new float[2][enrollmentTeamToRegister.size()];
//        找到学生可能会报的所有专业组,只要mark>0就认为有可能报名
        for (int i = 0; i < enrollmentTeamToRegister.size(); i++) {
            boolean isWillingToRegister = false;
            float markToEnrollmentTeam = 0;
            markToEnrollmentTeam = getMark(enrollmentTeamToRegister.get(i));
            if (markToEnrollmentTeam > 0) isWillingToRegister = true;
            if (isWillingToRegister) {
                queue.add(markToEnrollmentTeam);
                map.put(markToEnrollmentTeam, i);
            }
        }
//      找前六个专业组,可能找不到,找不到就认为这个学生和广工不得缘分
        while (!queue.isEmpty()) {
            float tmp = queue.poll();
            int pos = map.get(tmp);
            this.registeredTeam.add(enrollmentTeamToRegister.get(pos));
            n++;
//            System.out.print(tmp+" ");
        }
        this.getNumOfEnrollmentTeamNum = n;
//        System.out.println("填报的专业组数目:"+ this.getNumOfEnrollmentTeamNum);
//        System.out.println(" ");

    }


    public void getExpectationToSpeciality(Random random) {
        int num = this.rank / 10000;
//        while (num>0){
//            int pos = random.nextInt()%5;
//            while (pos<0) pos = random.nextInt()%5;
//            while (this.expectationToSpeciality[pos]!=0){
//                pos = random.nextInt()%5;
//                while (pos<0) pos = random.nextInt()%5;
//            }
//            float tmp = (float) (random.nextFloat()%0.5);
////            while (tmp < 0.75){
////                tmp = random.nextFloat();
////            }
//            this.expectationToSpeciality[pos] = tmp;
//            num--;
//        }
//        for (int i = 0; i < this.expectationToSpeciality.length; i++) {
//            if(this.expectationToSpeciality[i]==0){
//                this.expectationToSpeciality[i] = random.nextFloat();
//            }
//        }
        for (int i = 0; i < this.expectationToSpeciality.length; i++) {
            int tag = random.nextInt() % 6;
            if (tag < 0) {
                tag = Math.abs(tag);
            }
            if (tag <= num) {
                float t = (float) (random.nextFloat() % 0.7);
                while (t < 0.3) {
                    t = (float) (random.nextFloat() % 0.7);
                }
                this.expectationToSpeciality[i] = t;
            } else {
                float tmp = random.nextFloat();
                while (tmp < 0.7) {
                    tmp = random.nextFloat();
                }
                this.expectationToSpeciality[i] = tmp;
            }
        }
    }

    public void quickSortFavouriteSpeciality(Speciality_mark[] arr, int low, int high) {
        int i, j;
        Speciality_mark temp, t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];

        while (i < j) {
            //先看右边，依次往左递减
            while (temp.mark >= arr[j].mark && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.mark <= arr[i].mark && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSortFavouriteSpeciality(arr, low, j - 1);
        //递归调用右半数组
        quickSortFavouriteSpeciality(arr, j + 1, high);
    }

    public void quickSortHateSpeciality(Speciality_mark[] arr, int low, int high) {
        int i, j;
        Speciality_mark temp, t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];

        while (i < j) {
            //先看右边，依次往左递减
            while (temp.mark <= arr[j].mark && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.mark >= arr[i].mark && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSortFavouriteSpeciality(arr, low, j - 1);
        //递归调用右半数组
        quickSortFavouriteSpeciality(arr, j + 1, high);
    }


    public void judgeIsWillingToAdjust() {
        this.tagToAdjustment = new Boolean[this.getNumOfEnrollmentTeamNum];
        for (int i = 0; i < this.getNumOfEnrollmentTeamNum; i++) {
            tagToAdjustment[i] = judgeIsWillingAdjustToTeam(this.registeredTeam.get(i));

        }
    }

    private Boolean judgeIsWillingAdjustToTeam(EnrollmentTeam team) {
        return isWillingToAdjustByFeatures(team) && isWillingToAdjustByVariance(team);


    }

    private Boolean isWillingToAdjustByFeatures(EnrollmentTeam team) {
        int numOfFavouriteSpecialityInTeam = 0;
        float[] featuresOfLikeSpeciality = new float[4];
        float[] featuresOfElseSpeciality = new float[4];
        float[] tag = new float[4];
        for (int i = 0; i < team.specialities.size(); i++) {
            for (int j = 0; j < this.numOfFavouriteSpeciality; j++) {
                if (team.specialities.get(i).name.equals(this.favouriteSpeciality[j].tar.name)) {
                    for (int k = 0; k < featuresOfLikeSpeciality.length; k++) {
                        featuresOfLikeSpeciality[k] += this.favouriteSpeciality[j].tar.Features[k];
                    }
                    numOfFavouriteSpecialityInTeam++;
                } else {
                    for (int k = 0; k < featuresOfLikeSpeciality.length; k++) {
                        featuresOfElseSpeciality[k] += team.specialities.get(i).Features[k];
                    }
                }
            }
        }

        int numOfElseSpecialityInTeam = team.specialities.size() - numOfFavouriteSpecialityInTeam;
        float likePer = (float) numOfFavouriteSpecialityInTeam / team.specialities.size();
        float ElsePer = (float) numOfElseSpecialityInTeam / team.specialities.size();
        float sum = 0;
        for (int i = 0; i < tag.length; i++) {
            tag[i] = featuresOfElseSpeciality[i] * likePer + featuresOfElseSpeciality[i] * likePer;
            sum += tag[i] - this.expectationToSpeciality[i];
        }
        if (sum < 0) return false;
        return true;
    }

    private Boolean isWillingToAdjustByVariance(EnrollmentTeam team) {
        Random random = new Random();
        int tag = random.nextInt() % 100;
        if (team.markVariance * 1000 > tag) return false;
        return true;
    }


    @Override
    public int compareTo(Student o) {
        return o.rank - this.rank;
    }

    private float getMark(EnrollmentTeam team) {
        float ret = 0;
        for (int j = 0; j < this.numOfFavouriteSpeciality; j++) {
            if (team.isContainSpeciality(this.favouriteSpeciality[j].tar)) {
                ret += (float) 1.0 / (Math.pow(2, j));
            }
        }
        for (int j = 0; j < this.numOfHateSpeciality; j++) {
            if (team.isContainSpeciality(this.hateSpeciality[j].tar)) {
                ret += (float) (-1.0 / (Math.pow(2, (5 - j))));
            }
        }
        return ret;
    }

    private boolean judgeTeam(EnrollmentTeam team) {
        Random random = new Random();
        int unacceptableNum = 0;
        for (int i = 0; i < this.numOfFavouriteSpeciality; i++) {
            if (!team.isContainSpeciality(this.favouriteSpeciality[i].tar)) {
                int lowerNum = 0;
                for (int j = 0; j < this.favouriteSpeciality[i].tar.Features.length; j++) {
                    if (this.favouriteSpeciality[i].tar.Features[j] - this.expectationToSpeciality[j] > 0) {
                        lowerNum++;
                    }
                }
                int tag = random.nextInt(5);
                if (tag <= lowerNum) unacceptableNum++;
            }
        }
        double tag =  Math.sin(unacceptableNum*Math.PI/10);
        double tmp = random.nextDouble();
        return tmp < tag;
    }

    public void abortTeam() {
        for (int i = 0; i < this.registeredTeam.size(); i++) {
            if (judgeTeam(this.registeredTeam.get(i))) {
                System.out.println(this.registeredTeam.get(i).name + "删了");
                this.registeredTeam.remove(i);
                this.getNumOfEnrollmentTeamNum--;
            }
            System.out.println("");
        }
    }
}
