package prereqchecker;

import java.util.*;

/**
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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {
    
        NeedToTake needToTake = new NeedToTake();
        needToTake.Run(args);
    }

    protected void Run(String[] args)
    {

        ArrayList<Course> adjList = new ArrayList<Course>(); 
        StdIn.setFile("adjlist.in");
        AdjList.CreateAdjList(adjList);
        ArrayList<Course> coursesTook = new ArrayList<Course>();
        StdIn.setFile("needtotake.in");
        StdOut.setFile("out.txt");
        Process(adjList, coursesTook);
    }
    protected static void Process (ArrayList<Course> adjList, ArrayList<Course> cTook)
    {
        String targetTitle = StdIn.readLine();
        int numberCoursesTook = Integer.parseInt(StdIn.readLine());
        cTook = courseNamesTaken(adjList, numberCoursesTook); 
        addPrereqCoursesTaken(cTook);
        Course targetCourse = findTargetCourse(adjList, targetTitle); 
        ArrayList<Course> targetAndPrereqs = findTargetCoursePrereqs(adjList, targetCourse);
        coursesNeededForTarget(cTook, targetAndPrereqs,targetCourse);
    }

    protected static ArrayList<Course> courseNamesTaken(ArrayList<Course> adjList, int courseCount)
    {
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

    protected static ArrayList<Course> findTargetCoursePrereqs(ArrayList<Course> adjList, Course targetCourse)
    {
        ArrayList<Course> targetTaken = new ArrayList<Course>();
        targetTaken.add(targetCourse);
        addPrereqCoursesTaken(targetTaken);
        targetTaken.remove(targetCourse);
        return targetTaken; 
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

    protected static Course findTargetCourse (ArrayList<Course> adjList, String target)
    {
        Course targetCourse; 
        for(int i = 0; i < adjList.size(); i++)
        {
            if (adjList.get(i).title.equals(target))
            {
                targetCourse = adjList.get(i); 
                return targetCourse;
            }
        }
        return null; 
    }

    protected static void coursesNeededForTarget (ArrayList<Course> cTook, ArrayList<Course> targetAndCourses, Course target)
    {
        for(Course c : targetAndCourses)
        {
            if(cTook.contains(c) == false)
            {
                StdOut.println(c.title); 
            }
            
        }
    }
}
