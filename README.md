# PreReqChecker
A Java project to create a directed graph of the undergraduate Rutgers CS department.

## Summary
PreReqChecker is comprised of five Java Classes each tasked with responding to specific problems related to navigating the CS course structure. 
- `AdjList.java`
- `ValidPrereq.java`
- `Eligible.java`
- `NeedToTake.java`
- `SchedulePlan.java`


## AdjList
Takes two arguments:
- An input file detailing the number of courses related to the department directory
- An output file

Implemented the graph using an arraylist of type Course.
<br> Course stores a string name, and an arraylist of immediate prerequisites. 
<br>
<p>
  <img src="docs/adjlist.jpg" width = "150" height = "800" >
  <img src="docs/adjlist2.jpg" width = "200" height = "500" >
</p>

## How to Run

All java files in `src` must be compiled.

