//********************************************************************
//
//  Author:        Marshal Pfluger
//
//  Program #:     Five
//
//  File Name:     Program5.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      10/15/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  17
//
//  Chapter:       5
//
//  Description:   Implementation of FCFS algorithm
//
//********************************************************************

import java.util.List;

public class MetricsCalc {
	
	//***************************************************************
    //
    //  Method:       empty Constructor
    // 
    //  Description:   
    //
    //  Parameters:  
    //
    //  Returns:      
    //
    //**************************************************************
	MetricsCalc(){
		
	}
	
	//***************************************************************
    //
    //  Method:       averageCalc
    // 
    //  Description:  Calculates the average wait time/ turn around of the given list 
    //
    //  Parameters:   List<Integer> processTimes
    //
    //  Returns:      double
    //
    //**************************************************************
	public void averageCalc(List<Integer> processTimes, int numProcesses, boolean typeSet) {
		String type = "";
		if (typeSet) {
			type = "Average wait time: ";
		}
		else {
			type = "Average turnaround time: ";
		}
		int totalTime = 0;
		for(Integer time : processTimes) {
			totalTime += time;
		}
		System.out.println(type + (double) totalTime / numProcesses);
	}
	
}
