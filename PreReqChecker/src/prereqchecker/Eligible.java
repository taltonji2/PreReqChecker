package prereqchecker;

import java.util.*;

/**
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */

public class Eligible {
    public static void main(String[] args) {
        Eligible eligible = new Eligible();
        eligible.Run(args);
    }

    protected void Run(String[] args)
    {
        ArrayList<Course> adjList = new ArrayList<Course>();
        StdIn.setFile("adjlist.in");
        AdjList.CreateAdjList(adjList);
        StdIn.setFile("eligible.in");
        StdOut.setFile("out.txt");
        ArrayList<Course> coursesTaken = courseNamesTaken(adjList); 
        addPrereqCoursesTaken(coursesTaken);
        //traverse adjList and compare to coursesTaken to find courses available
        ArrayList<Course> availableList = availableCourses(adjList, coursesTaken);
        for(Course c : availableList)
        {
            StdOut.println(c.title);
        }
    }

    //Returns arraylist of availableCourses as a consiquence of coursesTaken.
    protected static ArrayList<Course> availableCourses(ArrayList<Course> adjList, ArrayList<Course> coursesTaken)
    {
        ArrayList<Course> availableList = new ArrayList<Course>(); 
        Boolean isPossible = true; 
        for (int i = 0; i < adjList.size(); i++)
        {
            for(int j = 0; j < adjList.get(i).listCoursesPrerequisits.size(); j++)
            {
                if(isTaken(coursesTaken, adjList.get(i).listCoursesPrerequisits.get(j)) == false)
                {
                    isPossible = false; 
                }
            } 
            if(isPossible == true)
            {
                availableList.add(adjList.get(i));
                //StdOut.println(adjList.get(i).title + " :(");
            } else
            {
                isPossible = true; 
            }
        }
        removeDouplicates(availableList, coursesTaken);
        return availableList; 
    }

    protected static void removeDouplicates (ArrayList<Course> availableList, ArrayList<Course> coursesTaken)
    {
        ArrayList<Course> temp = new ArrayList<Course>();
        deepCopy(coursesTaken, temp);
        for (Course c : temp)
        {
            if(isTaken(coursesTaken, c) == true)
            {
                availableList.remove(c); 
            }
        }
    }

    protected static boolean isTaken (ArrayList<Course> coursesTaken, Course courseInQuestion)
    {
        for(Course c : coursesTaken)
        {
            if (c.title.equals(courseInQuestion.title))
            {
                return true;
            }
        }
        return false; 
    }

    protected static ArrayList<Course> courseNamesTaken(ArrayList<Course> adjList)
    {
        int courseCount = Integer.parseInt(StdIn.readLine()); 
        ArrayList<Course> courses = new ArrayList<Course>(courseCount); 
        while(StdIn.hasNextLine())
        {
            String title = StdIn.readLine(); 
            for(int i = 0; i < adjList.size(); i++)
            {
                if (adjList.get(i).title.equals(title))
                {
                    courses.add(adjList.get(i));
                    //StdOut.println(adjList.get(i).title + " has been taken");
                    break; 
                }
            }
        }
        return courses; 
    }

    protected static void addPrereqCoursesTaken(ArrayList<Course> coursesTaken)
    {
        ArrayList<Course> temp = new ArrayList<Course>();
        boolean isDouplicate = false;
        deepCopy(coursesTaken, temp);
        for(int i = 0; i < coursesTaken.size(); i++)
        {
            if(!coursesTaken.get(i).listCoursesPrerequisits.isEmpty())
            {
                for(int j = 0; j < coursesTaken.get(i).listCoursesPrerequisits.size(); j++)
                {
                    Course p = coursesTaken.get(i).listCoursesPrerequisits.get(j);
                    for(int k = 0; k < temp.size(); k++)
                    {
                       if(p.equals(temp.get(k)))
                       {
                           isDouplicate = true; 
                           break;
                       } else
                       {
                           isDouplicate = false;
                       }
                    }
                    if(isDouplicate == false)
                    {
                        coursesTaken.add(p);
                        temp.add(p);
                        //StdOut.println(p.title + " has been taken");
                    }
                    
                }
            }
        }
    }

    protected static void deepCopy(ArrayList<Course> coursesTaken, ArrayList<Course> copy)
    {
        for(Course c : coursesTaken)
        {
            copy.add(c);
        }
    }
}


