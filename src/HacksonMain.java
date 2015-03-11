import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.lazy.LWL;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import Utils.Company;
import Utils.Price;

/**
 * Created by kanghuang on 2/7/15.
 */
public class HacksonMain {
 
	static ArrayList<Company> cpList;

    public HacksonMain() {
	cpList = new ArrayList<Company>();
    }

    public void readFile(final String s) throws IOException {
	File directory = new File(s);
//	FilenameFilter fileNameFilter = new FilenameFilter() {
//	    @Override
//	    public boolean accept(File dir, String name) {
//
//		return name.matches(s);
//		
//	    }
//	};
//	File files = directory.listFiles(fileNameFilter);
//	System.out.println(files.length);
	//for (File f : files) {
	File f = new File (s);
	 BufferedReader br = new BufferedReader(new FileReader(s));
	    Company cp = new Company();
	    cp.setName(f.getName());
	    br.readLine();
	    while (br.ready()) {
		Price p = new Price();
		String line = br.readLine();
		String[] tokens = line.split(",");
		 p.setStart_price(Float.parseFloat(tokens[1]));
	                p.setEnd_price(Float.parseFloat(tokens[2]));
		cp.add(p);
	    }
	    cpList.add(cp);
	    br.close();
	//}

    }

    public void print() {
	// System.out.println(cpList.size());
	for (Company cp : this.cpList) {
	    System.out.println(cp.getName() + " ");
	    ArrayList<Price> pl = (ArrayList<Price>) cp.getStock_price();
	    for (Price p : pl) {
		System.out.println(p.getStart_price() + " " + p.getEnd_price());
	    }
	    System.out.println("********");
	}
    }

    public static void main(String[] args) throws IOException {
	HacksonMain hm = new HacksonMain();
	hm.readFile(args[0]+" US Equity.dat");
	//hm.print();
	PeriodTimeSeriesForecasting pf = new PeriodTimeSeriesForecasting();
	List<Float> a3_list = new ArrayList<Float>();
	List<Float> a5_list = new ArrayList<Float>();
	List<Float> a10_list = new ArrayList<Float>();
	List<Float> orignial_list = new ArrayList<Float>();
	
	for (Company p : cpList) {
	    a3_list = pf.threePeriodTimesSeriesForecasting(p.getStock_price(),p);
	    a5_list = pf.fivePeriodTimesSeriesForecasting(p.getStock_price(),p);
	    orignial_list = pf.originalData(p.getStock_price(),p);
	    a10_list = pf.TenPeriodTimesSeriesForecasting(p.getStock_price(),p);
	}
	for (Float f : orignial_list){
		System.out.println(f);
	}
	System.out.println("*************************");
	System.out.println();
	System.out.println();
	System.out.println();
	for (Float f : a3_list){
		System.out.println(f);
	}
	System.out.println("*************************");
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println();
	for (Float f : a5_list){
		System.out.println(f);
	}
	/*
	       // hm.readFile();
	        //hm.print();
	        LWL localWeightAlgorithm = new LWL();
	        LinearRegression linearRegression = new LinearRegression();
	        CSVLoader csvLoader= new CSVLoader();
	        csvLoader.setSource(new File ("IBM US Equity.dat"));
	        Instances instances = csvLoader.getDataSet();
	        instances.setClassIndex(0);//instances.numAttributes()-1);
	        String optionCommand = "-K 10 -U 4 -W weka.classifiers.functions.LinearRegression";
	       // localWeightAlgorithm.setKNN(10);
	       // localWeightAlgorithm.setClassifier(linearRegression);
	        String[] options = optionCommand.split(" ");
	        for (String str: options){
	            System.out.println("str=" + str);
	        }
	        try {
		    localWeightAlgorithm.setOptions(options);
		    localWeightAlgorithm.buildClassifier(instances);
		    System.out.println(localWeightAlgorithm);
		       // localWeightAlgorithm.classifyInstance();
		        csvLoader.setSource(new File("test.train"));
		        instances = csvLoader.getDataSet();
		        instances.setClassIndex(0);
		        for (Instance instance : instances){
		            System.out.println(localWeightAlgorithm.classifyInstance(instance));
		        }
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	        
	    */
	
	Modeling model = new Modeling();
	    
	 for(int i =0 ; i< cpList.size();i++)
	    {
	    	model.calculateHistory(i, cpList);
	    	model.calculateMA(i, cpList);
	    	model.RiskControl(i, cpList);
	    	model.Grading(i, cpList);
	    	System.out.println(cpList.get(i).getGrade());
	    }
	    

    }
}