import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WGL
 * Date: 2023-01-18
 * Time: 0:20
 */
public class ShowInf {
    public void showPersonalityPer(Student[] students){
        int[] tag = new int[5];
        Arrays.fill(tag,0);
        for (Student student : students) {
            switch (student.personality) {
                case "common":
                    tag[0]++;
                    break;
                case "radical":
                    tag[1]++;
                    break;
                case "cautious":
                    tag[2]++;
                    break;
                case "extremeCautious":
                    tag[3]++;
                    break;
                case "extremeRadical":
                    tag[4]++;
                    break;
            }
        }
        System.out.println("common: "+tag[0]);
        System.out.println("radical: "+tag[1]);
        System.out.println("cautious: "+tag[2]);
        System.out.println("extremeCautious: "+tag[3]);
        System.out.println("extremeRadical: "+tag[4]);
    }

    public void showRank(Student[] students){
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].rank);
        }
    }

    public void showSpecialityStudentInf(Speciality[] specialities,InfCollector[] infCollectors) {
        try (Workbook workbook = new SXSSFWorkbook();
             FileOutputStream out = new FileOutputStream("E:\\RecruitStudent4.2\\result.xlsx")) {

            // 创建新的工作簿
            Sheet sheet = workbook.createSheet("1");
            Row row = sheet.createRow(0);
            Cell cell1 = row.createCell(1);
            cell1.setCellValue("最高排位");
            Cell cell2 = row.createCell(2);
            cell2.setCellValue("最低排位");
            Cell cell3 = row.createCell(3);
            cell3.setCellValue("平均排位");
            Cell cell4 = row.createCell(4);
            cell4.setCellValue("真实平均排位");


            for (int i = 0; i < specialities.length; i++) {
                Row row1 = sheet.createRow(i+1);
                int sum = 0;
                while (!specialities[i].queue.isEmpty()){
                    assert specialities[i].queue.peek() != null;
                    sum+=specialities[i].queue.peek().rank;
                    specialities[i].officiallyRecruitedStudent.add(specialities[i].queue.poll());

                }
                Cell cell = row1.createCell(0);
                cell.setCellValue(specialities[i].name);
                System.out.print(specialities[i].name);
                cell = row1.createCell(1);
                if((specialities[i].officiallyRecruitedStudent.size()>0)){
                    cell.setCellValue(specialities[i].officiallyRecruitedStudent.get(0).rank);
                    cell = row1.createCell(2);
                    cell.setCellValue(specialities[i].officiallyRecruitedStudent.get(specialities[i].officiallyRecruitedStudent.size()-1).rank);
                    cell = row1.createCell(3);
                    cell.setCellValue((float)sum/specialities[i].officiallyRecruitedStudent.size());
                    cell = row1.createCell(4);
                    cell.setCellValue(specialities[i].exactNum);
                    System.out.print(" "+specialities[i].officiallyRecruitedStudent.get(0).rank+" "+specialities[i].officiallyRecruitedStudent.get(specialities[i].officiallyRecruitedStudent.size()-1).rank);
                    System.out.print(" "+sum/specialities[i].officiallyRecruitedStudent.size());
                    cell = row1.createCell(5);
                    if(specialities[i].officiallyRecruitedStudent.size()==specialities[i].numToRecruit){
                        System.out.print(" 招满了" );
                        cell.setCellValue("招满");
                    }if(specialities[i].officiallyRecruitedStudent.size()>specialities[i].numToRecruit){
                        System.out.print(" 怎么他妈的招多了" );
                    }if(specialities[i].officiallyRecruitedStudent.size()<specialities[i].numToRecruit){
                        cell.setCellValue("少招了"+(specialities[i].numToRecruit-specialities[i].officiallyRecruitedStudent.size())+"人");
                        System.out.print(" 未招满,少招了" + (specialities[i].numToRecruit-specialities[i].officiallyRecruitedStudent.size())+"人 "+"招生目标: "+specialities[i].numToRecruit);
                    }
                    System.out.println(" ");

                    infCollectors[i].highestRank+=specialities[i].officiallyRecruitedStudent.get(0).rank;
                    infCollectors[i].lowestRank+=specialities[i].officiallyRecruitedStudent.get(specialities[i].officiallyRecruitedStudent.size()-1).rank;
                    infCollectors[i].avgRank+=sum/specialities[i].officiallyRecruitedStudent.size();
                }else {
                    cell.setCellValue("怎么没招到学生?");
                }

            }
            // 获取格式编码值

            workbook.write(out);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
