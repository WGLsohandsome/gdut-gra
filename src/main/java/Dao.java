import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WGL
 * Date: 2022-10-28
 * Time: 15:42
 */
public class Dao {

//    初始化专业数据
    public  void getSpeciallity(Speciality[] Tar) throws IOException {
        int n = 0;
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new FileReader("specialityInf2020.txt"));
        while (n<35){
            Tar[n] = new Speciality();
            String[] rec= reader.readLine().split(" ");
            Tar[n].academy = rec[0];
            Tar[n].name = rec[1];
            int recuritNum = Integer.parseInt(rec[2]);
            Tar[n].numToRecruit = recuritNum;
            Tar[n].recruitedStudent = new Student[recuritNum];
            Tar[n].exactNum = Integer.parseInt(rec[3]);
            Tar[n].lowestRank = Integer.parseInt(rec[4]);
            n++;
//            for (int i = 0; i < 4; i++) {
//                rec = scanner.next();
//                Tar[n].Features[i] = new Float((Float.parseFloat(rec)/100));
//                float tag = ;
//                Tar[n].Features[i] =  tag;
//            }
        }
//        Tar = ShellSort(Tar,n);
//        for (int i = 0; i < n; i++) {
//            System.out.println(Tar[i].exactNum+" "+Tar[i].name+" " + Tar[i].recruitedStudent.length+" "+Tar[i].academy);
//        }
        System.out.println("==========================================================================");
    }
//    初始化学生数据
    public  void initStudent(Student[] toTest, Stack<Integer> stack, Stack<Integer> stack1){
        Random random = new Random();
        for (int i = 0; i < toTest.length; i++) {
            toTest[i] = new Student(random,stack,i,stack1);
        }
    }

    public void collectRank(Student[] students){
        try (Workbook workbook = new SXSSFWorkbook();
             FileOutputStream out = new FileOutputStream("E:\\RecruitStudentWithExcel\\rank.xlsx")) {

            // 创建新的工作簿
            Sheet sheet = workbook.createSheet("num");
            for (int i = 0; i < students.length; i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(students[i].rank);
            }
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//     得到专业的各指标
    public void getSpecialityFeatrues(Speciality[] Tar) throws IOException {
        Academy[] academies = new Academy[17];
        BufferedReader reader = new BufferedReader(new FileReader("academyInf2.txt"));
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 17; ++i) {
            academies[i] = new Academy();
            String[] name = reader.readLine().split(" ");
            academies[i].name = name[0];
            for (int j = 0; j < 4; j++) {
                String feature = name[j+1];
                academies[i].featrues[j] = (Float.parseFloat(feature)/100);
            }
        }
        System.out.println("=========================================================================");
//        for (int i = 0; i < 17; i++) {
//            System.out.print(academies[i].name+" ");
//            for (int j = 0; j < 5; j++) {
//                System.out.print(academies[i].featrues[j]+" ");
//            }
//            System.out.println("");
//        }
        for (int i = 0; i < 35; i++) {
            for (Academy academy : academies) {
                if (Tar[i].academy.equals(academy.name)) {
                   Tar[i].setFeatures(academy.featrues.clone());
                }
            }
        }
//        for (int i = 0; i < 46; i++) {
//            System.out.println(Tar[i].toString());
//        }


    }

    public void sortStudent(Student[] students){
//        分而治之，循环为每次总数除二
        for (int i = students.length / 2; i > 0; i /= 2) {
//            循环分治的每一个分组
            for (int j = i; j < students.length; j++) {
                int index = j;
                Student temp = students[index];
//                比较每一组的值
                if (students[index].rank < students[index - i].rank) {
//                    如果比前面小就把前面的数值往后移，将合适的数值插入
                    while (index - i > 0 && temp.rank < students[index - i].rank) {
                        students[index] = students[index - i];
                        index -= i;
                    }
                    students[index] = temp;
                }
            }
        }
    }

    public void sortSpeciality(Speciality_mark[] arr,int low,int high){
        int i,j;
        Speciality_mark temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];

        while (i<j) {
            //先看右边，依次往左递减
            while (temp.mark>=arr[j].mark&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.mark<=arr[i].mark&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        sortSpeciality(arr, low, j-1);
        //递归调用右半数组
        sortSpeciality(arr, j+1, high);
    }


//    public void matchStudentToTeam(EnrollmentTeam[] enrollmentTeams,Student[] students){
//// 每个学生六个专业组，招生招六轮
//        for (int round = 0; round < 6; round++) {
//            for (int teamNum = 0; teamNum < enrollmentTeams.length; teamNum++) {
//                for (int studentNum = 1; studentNum < students.length; studentNum++) {
//                    if(students[studentNum].registeredTeam[round]==enrollmentTeams[teamNum]&&!students[studentNum].isRecurited){
//                        if(enrollmentTeams[teamNum].hasRecruitedStudentNum<enrollmentTeams[teamNum].toRecruitStudentNum){
//                            enrollmentTeams[teamNum].studentToRecruit[enrollmentTeams[teamNum].hasRecruitedStudentNum] = students[studentNum];
//                            enrollmentTeams[teamNum].hasRecruitedStudentNum++;
//                            students[studentNum].isRecurited = true;
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void getSpecialityToStudent(Student[] Tar,Speciality[] Source){
        Random random = new Random();
        for (int i = 0; i < Tar.length; i++) {
            Tar[i].judgeSpeciality(random,Source);
        }
    }

    public void showFavouriteSpecialityAndHateSpeciality(Student[] students){

        for (int i = 0; i < students.length; i++) {
            System.out.print("学生排名:"+ students[i].rank+ "学生性格: " + students[i].personality +" 喜欢的专业:");
            for (int j = 0; j < students[i].numOfFavouriteSpeciality; j++) {
                System.out.print(students[i].favouriteSpeciality[j].tar.name + " ");
            }

            System.out.print("不喜欢的专业:");
            for (int j = 0; j < students[i].numOfHateSpeciality; j++) {
                System.out.print(students[i].hateSpeciality[j].tar.name+" ");
            }
            System.out.println(" ");

        }
    }

    public void getEnrollmentTeamNum(EnrollmentTeam[] enrollmentTeams){
        for (int i = 0; i < enrollmentTeams.length; i++) {
            enrollmentTeams[i].getEnrollmentTeamNum();
        }
    }

    public void getEnrollmentTeam(ArrayList<EnrollmentTeam> enrollmentTeams, Speciality[] specialities, String[] names){
        Scanner scanner = new Scanner(System.in);
////        学院
//        for (int i = 0; i < 17; i++) {
//            EnrollmentTeam enrollmentTeam = new EnrollmentTeam();
//            enrollmentTeam.name = names[i];
//            enrollmentTeams.add(enrollmentTeam);
//        }
//        for (int i = 0; i < specialities.length; i++) {
//            for (int j = 0; j < enrollmentTeams.size(); j++) {
//                if(specialities[i].academy.equals(enrollmentTeams.get(j).name)){
//                    enrollmentTeams.get(j).specialities.add(specialities[i]);
//                }
//            }
//        }
//        小组
//        for (int i = 0; i < 9; i++) {
//            EnrollmentTeam team = new EnrollmentTeam();
//            String[] rec = scanner.nextLine().split(" ");
//            team.name = rec[0];
//            for (int k = 1; k < rec.length; k++) {
//                for (int j = 0; j < specialities.length; j++) {
//                    if(rec[k].equals(specialities[j].name)){
//                        team.specialities.add(specialities[j]);
//                    }
//                }
//            }
//            team.specialtyNum = rec.length-1;
//            enrollmentTeams.add(team);
//        }
//        大组
        EnrollmentTeam enrollmentTeam = new EnrollmentTeam();
        enrollmentTeam.specialities.addAll(Arrays.asList(specialities));
        enrollmentTeams.add(enrollmentTeam);
        enrollmentTeam.specialtyNum = specialities.length;




    }

    public boolean isAllAddedTOTeam(Speciality[] specialities){
        for (int i = 0; i < specialities.length; i++) {
            if(specialities[i].isAddedToTeam==false) return false;
        }
        return true;
    }

    public void studentRegisterToTeam(Student[] students,ArrayList<EnrollmentTeam> teams){
        for (int i = 0; i < students.length; i++) {
//            System.out.print("序号: "+i+" ");
            students[i].getEnrollmentTeam(teams);

        }
    }

    public void studentsAddToTeam(Student[] students, ArrayList<EnrollmentTeam> enrollmentTeams){
        for (int i = 0; i < students.length; i++) {
            if(!students[i].isRecurited){
                if(students[i].hasTheVery){
                    EnrollmentTeam theVery = students[i].registeredTeam.get(0);
                    for (int j = 0; j < enrollmentTeams.size(); j++) {
                        if(enrollmentTeams.get(j)==theVery){
                            enrollmentTeams.get(j).AddStudentToTeam(students[i]);
                        }
                    }
                    if(!students[i].isRecurited){
                        for (int j = students[i].getNumOfEnrollmentTeamNum-1; j >=1 && !students[i].isRecurited; j--) {
                            EnrollmentTeam tar = students[i].registeredTeam.get(j);
                            for (int k = 0; k < enrollmentTeams.size(); k++) {
                                if(tar== enrollmentTeams.get(j)){
                                    enrollmentTeams.get(j).AddStudentToTeam(students[i]);
                                }
                            }
                        }
                    }
                }else {
                    for (int j = students[i].getNumOfEnrollmentTeamNum-1; j >=0 && !students[i].isRecurited; j--) {
                        EnrollmentTeam tar = students[i].registeredTeam.get(j);
                        for (int k = 0; k < enrollmentTeams.size(); k++) {
                            if(tar== enrollmentTeams.get(k)){
                                enrollmentTeams.get(k).AddStudentToTeam(students[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    public void showEnrollmentTeamInf(EnrollmentTeam[] enrollmentTeams){
        for (int i = 0; i < enrollmentTeams.length; i++) {
            enrollmentTeams[i].showInf();
        }
    }

    public void getEnrollmentTeamsV(ArrayList<EnrollmentTeam> enrollmentTeams){
        for (int i = 0; i < enrollmentTeams.size(); i++) {
            enrollmentTeams.get(i).getMarkVariance();

        }
    }

    public void sortStudentSpeciality(Student[] students){
        for (int i = 0; i < students.length; i++) {
            students[i].quickSortFavouriteSpeciality(students[i].favouriteSpeciality,0,students[i].numOfFavouriteSpeciality-1);
            students[i].quickSortHateSpeciality(students[i].hateSpeciality,0,students[i].numOfHateSpeciality-1);
        }
    }

    public void SpecialityNum(Speciality[] specialities,Student[] students){
        for (int i = 0; i < specialities.length; i++) {
            specialities[i].showLikeNumAndHateNum(students);
        }
    }

    public void showStudentDistribution(Speciality[] specialities,Student[] students){

        try (Workbook workbook = new SXSSFWorkbook();
             FileOutputStream out = new FileOutputStream("E:\\RecruitStudent4.0\\Distribution.xlsx")) {
            Sheet sheet = workbook.createSheet("1");
            Row row = sheet.createRow(0);

            for (int i = 0; i < specialities.length; i++) {
                int[][] tag = new int[2][13];
                for (int k = 0; k < students.length; k++) {
                    for (int j = 0; j < students[i].numOfFavouriteSpeciality; j++) {
                        if(students[i].favouriteSpeciality[j].tar.name.equals(specialities[i].name)){
                            int pos = students[i].rank/4000-3;
                            if(pos<0) continue;
                            tag[0][pos]++;
                        }
                    }
                    for (int j = 0; j < students[i].numOfHateSpeciality; j++) {
                        if(students[i].hateSpeciality[j].tar.name.equals(specialities[i].name)){
                            int pos = students[i].rank/4000-3;
                            if(pos<0) continue;
                            tag[1][pos]++;
                        }
                    }
                }
                row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(specialities[i].name);
                for (int j = 0; j < 13; j++) {
                    cell = row.createCell(j+1);
                    cell.setCellValue(tag[0][j]);
                }
            }


            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void teamsUniformization(EnrollmentTeam[] enrollmentTeams){
        for (int i = 0; i < enrollmentTeams.length; i++) {

        }
    }

    public void judgeStudentAdjustment(Student[] students){
        for (int i = 0; i < students.length; i++) {
            students[i].judgeIsWillingToAdjust();
//
        }
    }

    public void officiallyRecruitStudent(Student[] students){
        for (int i = 1; i < students.length; i++) {
            Student tmp = students[i];
            if(tmp.isRecruitedGdut) continue;
            for (int j = 0; j < tmp.getNumOfEnrollmentTeamNum; j++) {
                tmp.registeredTeam.get(j).recruitStudent(tmp,tmp.tagToAdjustment[j]);
                if(tmp.isRecruitedGdut) break;
            }
        }
    }

    public void adjustInf(InfCollector[] infCollectors,int num){
        for (int i = 0; i < infCollectors.length; i++) {
            infCollectors[i].avgRank/=num;
            infCollectors[i].highestRank/=num;
            infCollectors[i].lowestRank/=num;
        }
    }
    public void freeStudent(Student[] students){
        for (int i = 0; i < students.length; i++) {
            students[i] = null;
        }
    }

    public void exportDataToExcel(Student[] students, Speciality[] tar){
        long begin = System.currentTimeMillis(); //获取当前时间(毫秒)

        //超大excel文件写入使用SXSSFWorkbook对象
        try (Workbook workbook = new SXSSFWorkbook();
             FileOutputStream out = new FileOutputStream("E:\\RecruitStudent5.0\\Data.xlsx")) {

            // 创建新的工作簿
            Sheet sheet = workbook.createSheet("1");

            // 获取格式编码值
            DataFormat format = workbook.createDataFormat();
//            short dateFormat = format.getFormat("");
//            short monyFormat = format.getFormat("#,###");

            // 创建日期格式对象
//            CellStyle dateCellType = workbook.createCellStyle();
//            dateCellType.setDataFormat(dateFormat);

            // 创建红包格式对象
            CellStyle moneyCellType = workbook.createCellStyle();
//            moneyCellType.setDataFormat(monyFormat);

            for (int i = 0; i < students.length; i++) {

                String name = students[i].personality;
                Row row = sheet.createRow(i + 1);

                Cell cell0 = row.createCell(0); // 性格
                cell0.setCellValue(name);

                Cell cell1 = row.createCell(1); // 排名
                cell1.setCellValue(students[i].rank);

                Cell cell2 = row.createCell(2); // 第一偏好专业
//                cell2.setCellStyle(dateCellType);
                if(students[i].favouriteSpeciality[0].tar==null){
                    cell2.setCellValue(" ");
                }else {
                    cell2.setCellValue(students[i].favouriteSpeciality[0].tar.name);
                }
                Cell cell3 = row.createCell(3); // 第一负偏好专业
                cell3.setCellStyle(moneyCellType);
                if(students[i].hateSpeciality[0].tar==null){
                    cell3.setCellValue(" ");
                }else {
                    cell3.setCellValue(students[i].hateSpeciality[0].tar.name);
                }
                for (int j = 0; j < students[i].expectationToSpeciality.length; j++) {
                    Cell cell = row.createCell(4+j);
                    cell.setCellValue(students[i].expectationToSpeciality[j]);
                }

            }
            sheet = workbook.createSheet("2");
            for (int i = 0; i < tar.length; i++) {
                int numF = 0;
                int numH = 0;
                for (int j = 0; j < students.length; j++) {
                    if(students[j].favouriteSpeciality[0].tar==null) continue;
                    if(students[j].favouriteSpeciality[0].tar.name.equals(tar[i].name)){
                        numF++;
                    }

                }
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(tar[i].name);
                 cell = row.createCell(1);
                cell.setCellValue(numF);
                cell = row.createCell(2);
                cell.setCellValue(numH);
            }
            workbook.write(out);

            long end = System.currentTimeMillis();
            System.out.println("耗时" + (end-begin) + "毫秒"); //计算时间差

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Workbook workbook = new SXSSFWorkbook();
             FileOutputStream out = new FileOutputStream("E:\\RecruitStudent5.0\\SpecialityFeatures.xlsx")) {

            Sheet sheet = workbook.createSheet("1");

            Row row1 = sheet.createRow(0);
            Cell cell = row1.createCell(1);
            cell.setCellValue("工作满意度");
             cell = row1.createCell(2);
            cell.setCellValue("专业相关度");
             cell = row1.createCell(3);
            cell.setCellValue("职业期待吻合度");
             cell = row1.createCell(4);
            cell.setCellValue("工作稳定率");
            cell = row1.createCell(5);
            cell.setCellValue("真实平均排名");


            for (int i = 0; i < tar.length; i++) {

                String name = tar[i].name;
                Row row = sheet.createRow(i + 1);

                Cell cell0 = row.createCell(0); // 性格
                cell0.setCellValue(name);

                for (int j = 0; j < tar[i].Features.length; j++) {
                    cell0 = row.createCell(j+1);
                    cell0.setCellValue(tar[i].Features[j]);
                }
                cell0 = row.createCell(5);
                cell0.setCellValue(tar[i].exactNum);

            }
            workbook.write(out);

            long end = System.currentTimeMillis();
            System.out.println("耗时" + (end-begin) + "毫秒"); //计算时间差

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adjustSpecialityFeatures(Speciality[] specialities, String[] name) {
        adjustFeaturesInAcademy(specialities,name);
        featuresNormalization(specialities);
//        for (int i = 0; i < specialities.length; i++) {
//            System.out.println(specialities[i].toString());
//        }
    }

    private void featuresNormalization(Speciality[] specialities){
        double yMin,yMax,xMax,xMin;
        yMin = 0.3;
        yMax = 1;
        xMax = xMin = specialities[0].Features[0];
        for (int i = 0; i < specialities.length; i++) {
            for (int j = 0; j < specialities[i].Features.length; j++) {
                if (xMax<specialities[i].Features[j]){
                    xMax = specialities[i].Features[j];
                }
                if (xMin>specialities[i].Features[j]){
                    xMin = specialities[i].Features[j];
                }
            }
        }
        for (int i = 0; i < specialities.length; i++) {
            for (int j = 0; j < specialities[i].Features.length; j++) {
                double x = specialities[i].Features[j];
                specialities[i].Features[j] = (float) (yMin+((yMax-yMin)/(xMax-xMin))*(x-xMin));
            }
        }
    }

    private void adjustFeaturesInAcademy(Speciality[] specialities, String[] name){
        HashMap<String, ArrayList<Speciality>> map = new HashMap<>();
        for (Speciality speciality : specialities) {
            if (!map.containsKey(speciality.academy)) {
                ArrayList<Speciality> arrayList = new ArrayList<>();
                arrayList.add(speciality);
                map.put(speciality.academy, arrayList);
            } else {
                map.get(speciality.academy).add(speciality);
            }
        }
        System.out.println(" ");
        for (String s :
                name) {
            ArrayList<Speciality> list = map.get(s);
            int sum = 0;
            if(list==null) continue;
            for (Speciality speciality : list) {
                sum += speciality.exactNum;
            }
            sum /= list.size();
            adjustF(list, sum);
        }
    }

    private void adjustF(ArrayList<Speciality> specialities, int sum) {
        for (Speciality s :
                specialities) {
            float tag = (float) sum / s.exactNum;
            if (tag > 1) continue;
            for (int i = 0; i < s.Features.length; i++) {
                s.Features[i] *= tag;
            }
        }
    }
    public void isRecruited(Student[] students){
        for (int i = 0; i < students.length; i++) {
            if(students[i].isRecruitedGdut){
                System.out.println("1");
            }else {
                System.out.println("0");
            }
        }
    }

    public void recruitSecondRound(Student[] students){
        for (int i = 0; i < students.length; i++) {
            if (!students[i].isRecruitedGdut) {
                for (int j = 0; j < students[i].getNumOfEnrollmentTeamNum; j++) {
                    if(students[i].tagToAdjustment[j]){
                        for (int k = 0; k < students[i].registeredTeam.get(j).specialities.size(); k++) {
                            if(students[i].registeredTeam.get(j).specialities.get(k).numToRecruit>
                                    students[i].registeredTeam.get(j).specialities.get(k).officiallyRecruitedStudent.size()){
                                students[i].registeredTeam.get(j).specialities.get(k).addStudent(students[i]);
                            }
                            if(students[i].isRecruitedGdut) continue;
                        }
                        if(students[i].isRecruitedGdut) continue;
                    }
                    if(students[i].isRecruitedGdut) continue;
                }
                if(students[i].isRecruitedGdut) continue;
            }

        }
    }

    public void abortTeam(Student[] students){
        for (int i = 0; i < students.length; i++) {
            students[i].abortTeam();
        }
    }



}




