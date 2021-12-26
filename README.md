# PreReqChecker
A Java project to create a directed graph of the undergraduate Rutgers CS department course directory.

## Summary
PreReqChecker is comprised of five Java Classes each tasked with responding to specific problems related to navigating the CS course structure. 
- `AdjList.java`
- `ValidPrereq.java`
- `Eligible.java`
- `NeedToTake.java`
- `SchedulePlan.java`


## AdjList
The purpose of this class is to create an Adjacency List and return the dependencies of the course structure. 

Takes two arguments:
- An input file detailing the number of courses related to the department directory
- An output file

returns the full adjacency list structure. 

Implemented the graph using an arraylist of type Course.
<br> Course stores a string name, and an arraylist of immediate prerequisites. 
<br>
<p>
  <img src="docs/adjlist.jpg" width = "150" height = "800" >
  <img src="docs/adjlist2.jpg" width = "200" height = "500" >
</p>


## ValidPrereq
The purpose of this class is to use the adjacency list to determine whether a course would be possible to take given a hypothetical prerequisite. 

Takes three arguments:
- An adjlist input file
- A validprereq input file
- An output file

returning YES or NO. 

Implemented using a private class Paths and recusive depth for search method to determine connectivity within the adjacency list. 

<p>
  <img src="docs/val.jpg" width = "200" height = "100" >
  <img src="docs/val2.jpg" width = "200" height = "100" >
   <img src="docs/val3.jpg" width = "200" height = "100" >
  <img src="docs/val4.jpg" width = "200" height = "100" >
</p>


## Eligible
The purpose of this class is to use the adjacency list and an eligible input file to find what courses are eligible to take.
<br> The eligible input file is a list of completed courses, and it is assumed that they have been taken and all direct and indirect prerequisites have been taken.

Takes three arguments:
- An adjlist input file
- A eligible input file
- An output file


## How to Run

All java files in `src` must be compiled.

