
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WGL
 * Date: 2022-10-17
 * Time: 16:27
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String[] names = {"环境科学与工程学院","物理与光电工程学院","政法学院","自动化学院","信息工程学院","艺术与设计学院","生物医药学院"
        ,"材料与能源学院","管理学院","建筑与城市规划学院","轻工化工学院","外国语学院","应用数学学院","土木与交通工程学院","机电工程学院"
        ,"经济与贸易学院","计算机学院"};

        Stack<Integer> stack = new Stack<>();
        Stack<Integer> stack1 = new Stack<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            stack1.push((int) (random.nextGaussian() * 1000) + 3000);
        }
        Speciality[] Tar = new Speciality[35];
        Dao dao =new Dao();
        dao.getSpeciallity(Tar);
        dao.getSpecialityFeatrues(Tar);
        System.out.println(" ");

        Student[] toTest = new Student[15000];

        dao.initStudent(toTest,stack,stack1);
        dao.adjustSpecialityFeatures(Tar,names);
        System.out.println(" ");
        dao.sortStudent(toTest);

        dao.getSpecialityToStudent(toTest,Tar);
        dao.sortStudentSpeciality(toTest);
        dao.exportDataToExcel(toTest,Tar);

        ArrayList<EnrollmentTeam> toRecruit = new ArrayList<>();
        dao.getEnrollmentTeam(toRecruit,Tar,names);
        dao.studentRegisterToTeam(toTest,toRecruit);
        dao.abortTeam(toTest);
        dao.getEnrollmentTeamsV(toRecruit);
        dao.judgeStudentAdjustment(toTest);
        dao.officiallyRecruitStudent(toTest);
//        dao.recruitSecondRound(toTest);

        ShowInf showInf = new ShowInf();
        InfCollector[] infCollectors = new InfCollector[35];
        for (int i = 0; i < 35; i++) {
            infCollectors[i] = new InfCollector();
            infCollectors[i].name = Tar[i].name;
        }
        showInf.showSpecialityStudentInf(Tar,infCollectors);
        System.out.println(" ");
        System.out.println("=======================================================================================================================");
    }
}
