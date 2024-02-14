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
//  Description:   Implementation of PRI-RR algorithm
//
//********************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PriorityRoundRobin implements Algorithm {
	private List<Task> queue;
	private int numTasks = 0;
	private List<Integer> waitTime;
	private List<Integer> turnAround;
	private int timeQuantum;
	private HashMap<Integer, Integer> referenceBursts;
	
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
	PriorityRoundRobin(List<Task> queue){
		this.timeQuantum = 10;
		this.queue = queue;
		this.waitTime = new ArrayList<Integer>();
		this.turnAround = new ArrayList<Integer>();
		this.referenceBursts  = new HashMap<>();
		// Making a reference list of the burst times
		for (Task tasks : queue) {
			referenceBursts.put(tasks.getTid(), tasks.getBurst());	
		}
	}
	
	//***************************************************************
    //
    //  Method:       schedule override 
    // 
    //  Description:  Handles the scheduling for the PRI-RR algorithm
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************  
	@Override
	public void schedule() {
		// Declare variable to hold various function needs
        int currentTime = 0;
        int finalWait = 0;
        
        // Loop through queue till it is empty
        while (!queue.isEmpty()) {
        	// Get the next task from pickNextTask
            Task currentTask = pickNextTask();
            // Get burst time of current task
            int burstTime = currentTask.getBurst();
            // Task completes within the time quantum
            // If the task is equal to or under TQ enter block
            if (burstTime <= timeQuantum) {
            	// Add current task burst to currentTime
            	currentTime += burstTime;
            	// Get the wait time of the current task using the reference list
            	finalWait = (currentTime - referenceBursts.get(currentTask.getTid()));
            	// Add the turn around time and wait time to the list
                waitTime.add(finalWait);
                turnAround.add(currentTime);
                // set the wait time and turn around for the task
                currentTask.setWait(finalWait);
                currentTask.setTurn(currentTime);
                // Run task on CPU
                CPU.run(currentTask, currentTask.getBurst());
                // Increment num task
                numTasks++;
             // Task did not finish in TQ and needs more time
            } else {
            	// Add the TQ to the current time
                currentTime += timeQuantum;
                // Run the task on CPU
                CPU.run(currentTask, currentTask.getBurst());
                // Decrease the burst time the TQ amount
                currentTask.setBurst(currentTask.getBurst() - timeQuantum);
                // Return the task to the queue
                queue.add(currentTask); // Re-queue the task
            }
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
		// loop through list and find the task with highest priority
		for (Task tasks : queue) {
			if (tasks.getPriority() > nextTask.getPriority()) {
				nextTask = tasks;
			}
		}
		// Remove task
		queue.remove(nextTask);
		// Return the next task
		return nextTask;
	}
}