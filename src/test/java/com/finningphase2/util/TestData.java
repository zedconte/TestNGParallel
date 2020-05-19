package com.finningphase2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class TestData {

    private static List<IBodyElement> bodyElemnts;
    private static XWPFTable tableInfo;
    private static XWPFTable tableStep;
    private static XWPFDocument doc;
    private static int caseNum = 1;
    private static int bodyIdx = 1;

    static {
        try {
            doc = new XWPFDocument(new FileInputStream(new File("CaseSource").listFiles()[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bodyElemnts = doc.getBodyElements();
    }

    public static void getTable() {
        Iterator<XWPFParagraph> pIter = doc.getParagraphsIterator();
        int count = 0;
        while (pIter.hasNext() && !pIter.next().getText().contains(caseNum + ""))
            count++;
        bodyIdx = doc.getPosOfParagraph(doc.getParagraphs().get(count));
    }

    public static String getDesc(int caseNumber, int stepNo){
        caseNum = caseNumber;
        List<XWPFTableRow> rs = tableStep.getRows();
        String desc = rs.get(stepNo).getCell(1).getText().trim();
        return desc;
    }

    public static String getExp(int caseNumber, int stepNo){
        caseNum = caseNumber;
        List<XWPFTableRow> rs = tableStep.getRows();
        String expResult = rs.get(stepNo).getCell(2).getText().trim();
        return expResult;
    }


    public static String getTitle(int caseNumber) throws Exception{
        caseNum = caseNumber;
        getTable();

        tableInfo = (XWPFTable)bodyElemnts.get(bodyIdx + 1);
        tableStep = (XWPFTable)bodyElemnts.get(bodyIdx + 6);

        String title = tableInfo.getRow(1).getCell(1).getText().replaceAll("[-\\. ]", "").trim();
        return title;
    }

    private static Properties resource = null;
    // it is used to load the test data file
    public static void load(String filename)
    {
        resource = new Properties();
        try{
            File file=new File("./testdata/"+filename);
            InputStream data_input = new FileInputStream(file);
            //InputStream data_input = SystemUtil.class.getResourceAsStream("/"+filename);
            resource.load(data_input);
            System.out.println("Read test data file "+ filename+"...");
        }catch (Exception e){
            System.out.println("Warning: Not found test data file "+ filename);
        }

    }
    /**
     * Objective: Get the test data, simple use TestData.get("args name");
     *
     * @param argName
     *            : the test data args name you defined in test data file
     */
    public static String get(String argName){
        return resource.getProperty(argName).trim();
    }

}
