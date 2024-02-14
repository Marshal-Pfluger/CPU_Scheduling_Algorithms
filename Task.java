//*********************************************************************
//
//  Author:        Textbook Authors
//
//  Program #:     Five
//
//  File Name:     Task.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Instructor:    Prof. Fred Kumi
//
//  Chapter:       5
//
//  Description:   Task to be scheduled by the scheduling algorithm.
// 
//                 Each task is represented by
//
//                 String name - a task name, not necessarily unique
// 
//                 int tid - unique task identifier
// 
//                 int priority - the relative priority of a task where
//                                a higher number indicates higher relative
//                                priority.
// 
//                 int burst - the CPU burst of this this task
// 
//*********************************************************************
import java.util.concurrent.atomic.AtomicInteger;

public class Task
{
    // the representation of each task
    private String name;
    private int tid;
    private int priority;
    private int burst;
    private Integer waitTime;
    private Integer turnAround;

    /**
     * We use an atomic integer to assign each task a unique task id.
     */
    private static AtomicInteger tidAllocator = new AtomicInteger();

    public Task(String name, int priority, int burst)
    {
        this.name = name;
        this.priority = priority;
        this.burst = burst;
        this.waitTime = null;
        this.turnAround = null;
        this.tid = tidAllocator.getAndIncrement();
    }

    /**
     * Appropriate getters
     */
    public String getName()
    {
        return name;
    }

    public int getTid()
    {
        return tid;
    }

    public int getPriority()
    {
        return priority;
    }

    public int getBurst()
    {
        return burst;
    }

    /**
     * Appropriate setters
     */
    public int setPriority(int priority)
    {
        this.priority = priority;

        return priority;
    }
    
    public int setBurst(int burst)
    {
        this.burst = burst;

        return burst;
    }
    
    public void setWait(int waitTime) {
    	this.waitTime = waitTime;
    }
    
    public void setTurn(int turnAround) {
    	this.turnAround = turnAround;
    }

    /**
     * We override equals() so we can use a
     * Task object in Java collection classes.
     */
    @Override
    public boolean equals(Object object)
    {
        if (object == this)
            return true;

        if (!(object instanceof Task))
            return false;

        /**
         * Otherwise we are dealing with another Task.
         * two tasks are equal if they have the same tid.
         */
        Task rightHandSide = (Task)object;
        return (this.tid == rightHandSide.tid) ? true : false;
    }

    @Override
    public String toString()
    {
        return
            "Name: " + name + "\n" + 
            "Tid: " + tid + "\n" + 
            "Priority: " + priority + "\n" + 
            "Burst: " + burst + "\n\n" +
            "**Run Info**" + "\n" +
            "(Null if not complete)" + "\n" +
            "Wait Time: " + waitTime + "\n" +
            "Turn around time: " + turnAround + "\n";
    }
}
