import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WGL
 * Date: 2022-10-17
 * Time: 15:40
 */
public class EnrollmentTeam {
    int toRecruitStudentNum = 0;
    int hasRecruitedStudentNum = 0;
    int specialtyNum = 0;
    float markVariance = 0;
    int  avgRank = 0;
    String name;
    float[] markAfterUniformization;
    ArrayList<Speciality> specialities = new ArrayList<>();
    Student[] studentToRecruit;
    Student[] officiallyRecruitedStudent;
    HashMap<Student,Boolean> map = new HashMap<>();
    PriorityQueue<Student> queue = new PriorityQueue<>();


    public boolean isContainSpeciality(Speciality speciality){
        for (int i = 0; i < this.specialities.size(); i++) {
            if(this.specialities.get(i).name.equals(speciality.name)){
                return true;
            }
        }
        return false;
    }

    public void getEnrollmentTeamNum(){
        for (int i = 0; i < specialities.size(); i++) {
//            this.toRecruitStudentNum += specialities[i].recruitedStudent.length;
            this.toRecruitStudentNum+=specialities.get(i).recruitedStudent.length;
        }
        this.officiallyRecruitedStudent = new Student[this.toRecruitStudentNum];
    }
//明天开始写这个东西
//    public void getSpecialityToTeamByAcademy(Speciality[] specialities){
//        Random random = new Random();
//        int n = 0;
//        if(this.specialities==null){
//            int pos = random.nextInt()% specialities.length;
//            while (specialities[pos].isAddedToTeam ){
//                if(pos < 0){
//                    pos = random.nextInt()%specialities.length;
//                }
//                pos = random.nextInt()%specialities.length;
//            }
//            this.specialities[0] = specialities[pos];
//            n++;
//        }
//        for (int i = 0;i< specialities.length;i++){
//            if(this.specialities[0].academy.equals(specialities[i].academy)){
//                this.specialities[n] = specialities[i];
//                n++;
//            }
//        }
//    }


    public void getSpecialityToTeam1(Speciality[] specialities) {
        for (int i = 0; i < specialities.length; i++) {
            if(!specialities[i].isAddedToTeam){
                this.specialities.add(specialities[i]);
                specialities[i].isAddedToTeam = true;
                this.specialtyNum++;
                if(this.specialtyNum>=38) {
                    return;
                }else {
                    this.specialities.add(specialities[specialities.length-i-1]) ;
                    specialities[specialities.length-i-1].isAddedToTeam = true;
                    this.specialtyNum++;
                }
                if(this.specialtyNum>=38) return;
            }
        }

    }

    public Boolean AddStudentToTeam(Student student){
        if(this.hasRecruitedStudentNum<this.toRecruitStudentNum){
            this.studentToRecruit[this.hasRecruitedStudentNum] = student;
            this.hasRecruitedStudentNum++;
            student.isRecurited = true;
            return true;
        }
        int pos = findLowestPos();
        if(student.rank<this.studentToRecruit[pos].rank){
            this.studentToRecruit[pos].isRecurited = false;
            this.studentToRecruit[pos] = student;
            student.isRecurited = true;
            return true;
        }
        return false;
    }

    public int findLowestPos(){
        int lowestRank = this.studentToRecruit[0].rank;
        int ret = 0;
        for (int i = 1; i < this.hasRecruitedStudentNum; i++) {
            if(lowestRank<this.studentToRecruit[i].rank){
                lowestRank=this.studentToRecruit[i].rank;
                ret = i;
            }
        }
        return ret;
    }

    public void showInf(){
        for (int i = 0; i < this.specialities.size(); i++) {
            String name = this.specialities.get(i).name;
            System.out.print(name+" ");
        }
        System.out.println(" ");
        int biggestRank = this.studentToRecruit[0].rank;
        int smallestRank = this.studentToRecruit[0].rank;
        int avgRank = this.studentToRecruit[0].rank;
        for (int i = 1; i < this.hasRecruitedStudentNum; i++) {
            if(biggestRank>this.studentToRecruit[i].rank){
                biggestRank = this.studentToRecruit[i].rank;
            }
            if(biggestRank>this.studentToRecruit[i].rank){
                biggestRank = this.studentToRecruit[i].rank;
            }
            if(smallestRank<this.studentToRecruit[i].rank){
                smallestRank = this.studentToRecruit[i].rank;
            }
            avgRank+=this.studentToRecruit[i].rank;

        }
        avgRank/=this.hasRecruitedStudentNum;
        this.avgRank = avgRank;
        System.out.print("该专业组最低排位为: "+smallestRank);
        System.out.print("该专业组最高排位为: "+biggestRank);
        System.out.println("该专业组平均排位为: "+avgRank);
        System.out.println("==========================================================================================");

    }

//    public void getSpecialityVariance(){
//        float tag = 0;
//        long sum = 0;
//        for (int i = 0; i < this.specialtyNum; i++) {
//            sum+=Math.pow(this.specialities.get(i).exactNum-this.avgRank,2);
//        }
//        this.markVariance = (float) Math.sqrt(sum/this.specialtyNum);
//        System.out.println(String.format("%.2f",this.markVariance));
//    }

    public void getMarkVariance(){
        this.markAfterUniformization = markUniformization();
        int avg = 0;
        int sum = 0;
        for (int i = 0; i < this.specialities.size(); i++) {
            sum+=this.specialities.get(i).exactNum;
        }
        avg = sum/this.specialities.size();
        float ret = 0;
        for (int i = 0; i < this.specialities.size(); i++) {
            ret+=Math.pow(this.markAfterUniformization[i]-(float) avg/sum,2);
        }
        this.markVariance = (float) Math.sqrt(ret/this.specialtyNum);
//        System.out.println(String.format("%f",this.markVariance));
    }

    private float[] markUniformization(){
        float[] ret = new float[this.specialities.size()];
        int sum = 0;
        for (int i = 0; i < this.specialities.size(); i++) {
            sum+=this.specialities.get(i).exactNum;
        }
        for (int i = 0; i < this.specialities.size(); i++) {
            ret[i] = (float) this.specialities.get(i).exactNum/sum;
        }
        return ret;
    }

    public void recruitStudent(Student student,Boolean isWillingToAdjust){
        boolean tag = student.isRecruitedGdut;
        Student abandonedOne = null;
        for (int i = 0; i < student.numOfFavouriteSpeciality; i++) {
            Speciality tar = student.favouriteSpeciality[i].tar;
            for (int j = 0; j < this.specialities.size(); j++) {
                if(tar.name.equals(this.specialities.get(j).name)){
                    this.specialities.get(j).addStudent(student);
                }
            }
        }
    }
}
