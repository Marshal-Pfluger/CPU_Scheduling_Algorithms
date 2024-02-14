//********************************************************************
//
//  Author:        Instructor
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
//  Description:   Demonstrates different CPU scheduling algorithms
//                 FCFS, SJF, PRI, RR, and PRI-RR
//
//********************************************************************
  
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program5
{
	private static BufferedReader inFile = null;
	private static Scanner input = null;
	
	//***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the program
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public static void main(String[] args)
	{		
	    Program5 obj = new Program5();
	    obj.developerInfo();
	    obj.runDemo();

	}
    
	//***************************************************************
    //
    //  Method:       runDemo()
    // 
    //  Description:  runs the program
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void runDemo() {
		input = new Scanner(System.in);
		String reRunState = "";
		do {
			String scheduleName = getUserInput("Enter the algorithm type: ");
			String algorithmFilename = getUserInput("Enter the schedule filename: ");
			
			openAlgorithmFile(algorithmFilename);
			List<Task> queue = createPopulateQueue();
			processAlgorithm(scheduleName, queue);
			reRunState = getUserInput("\n\nWould you like to run another algorithm?\n" + 
			                                     "type 'yes' or 'no'\n");
		}
		while (!reRunState.equalsIgnoreCase("no"));
		printOutput("Program Terminated");
		input.close();
		try {
			inFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//***************************************************************
    //
    //  Method:       getUserInput
    // 
    //  Description:  Gets the user input from terminal 
    //
    //  Parameters:   String prompt
    //
    //  Returns:      String inputData
    //
    //**************************************************************

	public String getUserInput(String prompt)
	{
		System.out.println(prompt);
		
		String inputData = input.nextLine();

		return inputData;
	}
	
	//***************************************************************
    //
    //  Method:       openAlgorithmFile
    // 
    //  Description:  opens the file that the user selected
    //
    //  Parameters:   String input
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void openAlgorithmFile(String input)
	{
		boolean fileFound = true;
		do {
	        try {
				inFile = new BufferedReader(new FileReader(input));
			} catch (FileNotFoundException e) {
				printOutput("The file you entered was not found.\n" + 
			                "Please try again");
				fileFound = false;
				e.printStackTrace();
			}	
		}
		while(!fileFound);

    }
	
	//***************************************************************
    //
    //  Method:       createPopulateQueue
    // 
    //  Description:  Splits the data from the user file and stores it in an arraylist
    //
    //  Parameters:   N/A
    //
    //  Returns:      List<Task> queue
    //
    //**************************************************************
	public List<Task> createPopulateQueue()
	{
        // create the queue of tasks
        List<Task> queue = new ArrayList<>();
		
        String scheduleRecord;
		
        // read in the tasks and populate the ready queue        
        try {
			while ((scheduleRecord = inFile.readLine()) != null)
			{
			    String[] tokens = scheduleRecord.split(",\\s*");
			    queue.add(new Task(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
			}
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			printOutput("The file was not formatted correctly.");
			System.exit(1);
		} catch (IOException i) {
			// TODO Auto-generated catch block
			i.printStackTrace();
		}
		return queue;
    }
    
	//***************************************************************
    //
    //  Method:       processAlgorithm
    // 
    //  Description:  Holds the switch statement to run different types of alg
    //
    //  Parameters:   String choice, List<Task> queue
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void processAlgorithm(String choice, List<Task> queue)
	{
        Algorithm scheduler = null;
        
        switch(choice)
		{
            case "FCFS":
                scheduler = new FirstComeFirstServed(queue);
                break;
            case "SJF":
                scheduler = new ShortestJobFirst(queue);
                break;
            case "PRI":
                scheduler = new Priority(queue);
                break;
            case "RR":
                scheduler = new RoundRobin(queue);
                break;
            case "PRI-RR":
                scheduler = new PriorityRoundRobin(queue);
                break;
            default:
                System.err.println("Invalid algorithm");
                System.exit(0);
        }
        
        // start the scheduler
        scheduler.schedule();
    }
	
	//***************************************************************
	//
	//  Method:       printOutput (Non Static)
	// 
	//  Description:  handles printing output for program2
	//
	//  Parameters:   String output
	//
	//  Returns:      N/A
	//
	//***************************************************************
	public void printOutput(String output) {
		//Print the output to the terminal
		System.out.println("\n" + output);
	}//End printOutput
	
	//***************************************************************
    //
    //  Method:       developerInfo
    // 
    //  Description:  The developer information method of the program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //*************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Marshal Pfluger");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Program: Five\n");
    } // End of the developerInfo method
}