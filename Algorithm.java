//********************************************************************
//
//  Author:        Textbook Authors
//
//  Program #:     Five
//
//  File Name:     Algorithm.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  17
//
//  Chapter:       5
//
//  Description:   Interface representing a generic scheduling algorithm.
//
//********************************************************************
public interface Algorithm
{
    /**
     * Invokes the scheduler
     */
    public abstract void schedule();

    /**
     * Selects the next task using the appropriate scheduling algorithm
     */
    public abstract Task pickNextTask();
}