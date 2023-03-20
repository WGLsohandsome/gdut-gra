
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WGL
 * Date: 2022-10-17
 * Time: 16:27
 */
public class Test {
    public static void main(String[] args) {
        String[] names = {"环境科学与工程学院","物理与光电工程学院","政法学院","自动化学院","信息工程学院","艺术与设计学院","生物医药学院"
        ,"材料与能源学院","管理学院","建筑与城市规划学院","轻工化工学院","应用数学学院","土木与交通工程学院","机电工程学院"
        ,"计算机学院"};
        Random random = new Random();
        Student[] toTest = new Student[7000];
        Speciality[] Tar = new Speciality[35];
        ArrayList<EnrollmentTeam> toRecruit = new ArrayList<>();
        InfCollector[] infCollectors = new InfCollector[35];
        ShowInf showInf = new ShowInf();
        Dao dao =new Dao();
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> stack1 = new Stack<>();
        for (int i = 0; i < 100000; i++) {
            double tag = random.nextGaussian();
            int tmp = (int) (tag*100+500);
            while (tmp<426||tmp>585){
                tag = random.nextGaussian();
                tmp = (int) (tag*100+500);
            }
            stack.add(tmp);
        }
        for (int i = 0; i < 100000; i++) {
            double tag = random.nextGaussian();
            int tmp = (int) (random.nextGaussian()*1000)+3000;
            stack1.add(tmp);
        }
        Scanner scanner = new Scanner(System.in);

        dao.getSpeciallity(Tar);
        dao.getSpecialityFeatrues(Tar);
        System.out.println(" ");
        for (int i = 0; i < 35; i++) {
            infCollectors[i] = new InfCollector();
            infCollectors[i].name = Tar[i].name;
        }
        int operateNum = 10;
//        for (int i = 0; i < operateNum; i++) {
            dao.initStudent(toTest,stack,stack1);
            dao.adjustSpecialityFeatures(Tar,names);
            System.out.println(" ");
            dao.sortStudent(toTest);
            dao.getSpecialityToStudent(toTest,Tar);
            dao.sortStudentSpeciality(toTest);
            dao.exportDataToExcel(toTest,Tar);
//        dao.showStudentDistribution(Tar,toTest);
            dao.getEnrollmentTeam(toRecruit,Tar);
            dao.studentRegisterToTeam(toTest,toRecruit);
            dao.abortTeam(toTest);
//            dao.studentsAddToTeam(toTest,toRecruit);
//        dao.showEnrollmentTeamInf(toRecurit);
            dao.getEnrollmentTeamsV(toRecruit);
            dao.judgeStudentAdjustment(toTest);
//            dao.showStudentDistribution(Tar,toTest);
            dao.officiallyRecruitStudent(toTest);
            dao.recruitSecondRound(toTest);
            showInf.showSpecialityStudentInf(Tar,infCollectors);
//            dao.isRecruited(toTest);
//            dao.freeStudent(toTest);
            System.out.println(" ");
//        }

        System.out.println("=======================================================================================================================");
    }
}
