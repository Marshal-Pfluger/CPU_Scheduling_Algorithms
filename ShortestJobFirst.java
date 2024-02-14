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
//  Due Date:      10/21/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  17
//
//  Chapter:       5
//
//  Description:   Implementation of SJF algorithm
//
//********************************************************************

import java.util.ArrayList;
import java.util.List;

public class ShortestJobFirst implements Algorithm {
	private List<Task> queue;
	private int numTasks = 0;
	private List<Integer> waitTime;
	private List<Integer> turnAround;
	
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
	ShortestJobFirst() {
		this.queue = new ArrayList<Task>();
	}
	
	//***************************************************************
    //
    //  Method:       parameterized constructor 
    // 
    //  Description:   
    //
    //  Parameters:  
    //
    //  Returns:      
    //
    //**************************************************************
	ShortestJobFirst(List<Task> queue){
		this.queue = queue;
		waitTime = new ArrayList<Integer>();
		turnAround = new ArrayList<Integer>();
	}
	
	//***************************************************************
    //
    //  Method:       schedule override 
    // 
    //  Description:  Handles the scheduling for the SJF algorithm
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
	@Override
	public void schedule() {
		// Declare variable to hold various function needs
		int waitHolder = 0;
		int turnHolder = 0;
		Task currentTask = null;
		// Loop through queue till it is empty
		while (!queue.isEmpty()) {
			// Get the next task from pickNextTask
			currentTask = pickNextTask();
			// If it is the first task set the wait time to 0 
			if (numTasks == 0) {
				waitTime.add(0);
				currentTask.setWait(0);
			}
			else {
				// Add the wait time to the holder variable
				waitTime.add(waitHolder);
				// Set the wait time of the task
				currentTask.setWait(waitHolder);
				// Increment the wait holder the length of current burst
				waitHolder += currentTask.getBurst();
			}
			// Increment the turn holder to current burst
			turnHolder += currentTask.getBurst();
			// Add the current turn around time to the turn around list
			turnAround.add(turnHolder);
			// Set the turn around for the task
			currentTask.setTurn(turnHolder);
			// Run the task through the CPU
			CPU.run(currentTask, currentTask.getBurst());
		}
		// instantiate an object to call the average calc method in metrics calc
		MetricsCalc obj = new MetricsCalc();
		obj.averageCalc(waitTime, numTasks, true);
		obj.averageCalc(turnAround, numTasks, false);
	}

	//***************************************************************
    //
    //  Method:       pickNextTask 
    // 
    //  Description:  Picks the next task for the schedule method
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
	@Override
	public Task pickNextTask() {
		// Get the first task in the list
		Task nextTask  = queue.get(0);
		// Loop through tasks to get the task with shortest burst
		for (Task tasks : queue) {
			if (tasks.getBurst() < nextTask.getBurst()) {
				nextTask = tasks;
			}
		}
		// Remove the task that has the shortest birst
		queue.remove(nextTask);
		// Increment numTasks 
		numTasks++;
		// Return the task with lowest burst. 
		return nextTask;
	}
}