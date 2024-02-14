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
//  Description:   Implementation of RR algorithm
//
//********************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin implements Algorithm {
	private Queue<Task> queue;
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
	RoundRobin(List<Task> queue){
		this.timeQuantum = 10;
		this.queue = new LinkedList<Task>(queue);
		waitTime = new ArrayList<Integer>();
		turnAround = new ArrayList<Integer>();
		referenceBursts  = new HashMap<>();
		// Making a reference list of the burst times 
		for (Task tasks : queue) {
			referenceBursts.put(tasks.getTid(), tasks.getBurst());	
		}
	}
	
	//***************************************************************
    //
    //  Method:       schedule override 
    // 
    //  Description:  Handles the scheduling for the RR algorithm
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
            	// set the wait time and turn around for the task
                currentTask.setWait(finalWait);
                currentTask.setTurn(currentTime);
                
                waitTime.add(finalWait);
                turnAround.add(currentTime);
                // Increment num task
                numTasks++;
                // Run task on CPU
                CPU.run(currentTask, currentTask.getBurst());
             // Task did not finish in TQ and needs more time
            } else {
                // Add the TQ to the current time
                currentTime += timeQuantum;
                // Run the task on CPU
                CPU.run(currentTask, currentTask.getBurst());
                // Decrease the burst time the TQ amount
                currentTask.setBurst(currentTask.getBurst() - timeQuantum);
                // Return the task to the queue
                queue.offer(currentTask); 
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
		// Return the next task
		return queue.poll();
	}

}
