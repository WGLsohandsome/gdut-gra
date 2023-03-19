import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WGL
 * Date: 2022-10-17
 * Time: 15:40
 */
public  class Speciality {
    String academy;
    String name;
    int exactNum;
    int lowestRank;
    int numToRecruit;
    int reputation;
    float[] Features = new float[4];

    public void setFeatures(float[] features) {
        Features = features;
    }

    Student[] recruitedStudent;
    ArrayList<Student> officiallyRecruitedStudent = new ArrayList<>();
    boolean isAddedToTeam = false;
    PriorityQueue<Student> queue = new PriorityQueue<>();
    public Boolean Isempty(){
        if(this.academy==null||this.name==null){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" +
                "academy='" + academy + '\'' +
                ", name='" + name + '\'' +
                ", Features=" + Arrays.toString(Features) +
                " " + Features +
                '}';
    }

    public void showLikeNumAndHateNum(Student[] students){
        float likeNum = 0;
        float hateNum = 0;
        float[] tagLike = new float[10];
        float[] tagHate = new float[10];

        for (int i = 0; i < students.length; i++) {
            for (int j = 0; j < students[i].numOfFavouriteSpeciality; j++) {
                if(students[i].favouriteSpeciality[j].tar.name.equals(this.name)){
                    likeNum++;
                }
            }
            for (int j = 0; j < students[i].numOfHateSpeciality; j++) {
                if(students[i].hateSpeciality[j].tar.name.equals(this.name)){
                    hateNum++;
                }
            }
        }
        float perLike = likeNum/ students.length;
        float perHate = hateNum/ students.length;
        System.out.print(String.format("%s %s %s ", this.name, likeNum, hateNum));
        System.out.print(String.format("%.2f",perLike*100));
        System.out.print("% ");
        System.out.print(String.format("%.2f",perHate*100));
        System.out.print("% ");
        System.out.println(" ");

    }

    public void showStudentDistribution(Student[] students){

    }

    public void addStudent(Student student){
        Student abandonedOne = null;
        if(queue.size()<this.recruitedStudent.length){
            queue.add(student);
            student.isRecruitedGdut = true;
            return ;
        }


    }

}
