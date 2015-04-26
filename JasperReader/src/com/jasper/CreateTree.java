package com.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class CreateTree {
	
	private HashMap<String, List<String>> links;
	private String regexCallSubReports = "\\{reportPath\\} *\\+ *\"\\/?(.+\\.jasper)";
	private String pathFolder = "C:\\Users\\Corentin\\JavaDevelopment\\TransUnionExhibitProposal";
	private String filesFilter = ".jrxml";
	private String mainReportName = "TransUnionMainReport.jrxml";
	private JTree tree;
	//private DefaultMutableTreeNode root = null;
	
	public CreateTree(){
		processFolder();
		tree = new JTree(iterateTree(mainReportName));
	}
	
	private DefaultMutableTreeNode iterateTree(String currentNodeName){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(currentNodeName);
		List<String> currentNodeChildren = links.get(currentNodeName);
		if (currentNodeChildren != null){
			for (String s:currentNodeChildren){
				DefaultMutableTreeNode child = iterateTree(s);
				node.add(child);
			}
		}
		return node;
	}
	
	public void displayLinks(){
		Iterator it = links.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pairs = (HashMap.Entry)it.next(); //map or hashmap?
	        System.out.println(pairs.getKey() + " is linked with " + pairs.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	public void processFolder(){
		final File folder = new File(pathFolder);
		links = new HashMap<String, List<String>>();
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	if (fileEntry.getName().contains(filesFilter)){
	        		processOneFile(fileEntry.getName());
	        	}
	        }
	    }
	    
	    System.out.println("Links is empty? = " + links.isEmpty());
	    
	    displayLinks();

/*		
		try {
			Files.walk(Paths.get(pathFolder)).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			        System.out.println(filePath);
			    }
			});
		} catch (IOException e) {
			System.out.println("Cannot find such folder");
			e.printStackTrace();
		}
*/
	}
	
	private void processOneFile(String filePath){
		try {
			if (filePath.contains("TransUnionPricingExhibit")){
				System.out.println("TEST");
			}
			List<String> connectionsFoundForThisFile = findconnections(pathFolder + "\\" + filePath, regexCallSubReports);
			if (!connectionsFoundForThisFile.isEmpty()){
				links.put(filePath, connectionsFoundForThisFile);
				//System.out.println("Added for : " + filePath);
			}
			else{
				System.out.println("Nothing found for : " + filePath);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private List<String> findconnections(String fullFilePath, String regex) throws FileNotFoundException {
    	//System.out.println("here1");
    	List<String> listOfconnectionsFound = new ArrayList<String>();
        Scanner s = new Scanner(new File(fullFilePath));
        //while (null != s.findWithinHorizon(".*Corentin\\.\"(.*)\"", 0)) {
        while (null != s.findWithinHorizon(regex, 0)) {
        	MatchResult mr = s.match();
            Pattern pattern = Pattern.compile(regex);
        	Matcher matcher = pattern.matcher(mr.group());
        	//System.out.println("Group = " + mr.group());
        	while (matcher.find()) {
        	      listOfconnectionsFound.add(matcher.group(1).replace("jasper", "jrxml"));
        	}
        	//System.out.println("here2");
        }
        s.close();
        
        return listOfconnectionsFound;
    }

	public JTree getTree() {
		return tree;
	}
}
