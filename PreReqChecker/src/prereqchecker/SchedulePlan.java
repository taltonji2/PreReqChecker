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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        SchedulePlan schedulePlan = new SchedulePlan();
        schedulePlan.Run(args);
    }

    protected void Run(String[] args)
    {
        ArrayList<Course> adjList = new ArrayList<Course>(); 
        StdIn.setFile("adjlist.in");
        AdjList.CreateAdjList(adjList);
        StdIn.setFile("scheduleplan.in");
        StdOut.setFile("out.txt");
        Process(adjList);    
    }
    protected static void Process (ArrayList<Course> adjList)
    {
        String targetTitle = StdIn.readLine();
        Course target = findTargetCourse(adjList, targetTitle); 
        ArrayList<Course> targetPrereqs = new ArrayList<Course>();
        targetPrereqs.add(target);
        int numberCoursesTaken = Integer.parseInt(StdIn.readLine());
        ArrayList<Course> coursesTaken = courseNamesTaken(adjList, numberCoursesTaken); 
        addPrereqs(targetPrereqs); 
        removeTakenPrereqs(targetPrereqs, coursesTaken);
        
        ArrayList<ArrayList<Course>> shorty = shortestList(adjList, targetPrereqs, coursesTaken);
        
        StdOut.println(shorty.size());
        
        for(ArrayList<Course> C : shorty)
        {
            for(Course c : C)
            {
                StdOut.print(c.title + " ");
            }
            StdOut.println();
        }
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

    protected static void removeTakenPrereqs (ArrayList<Course> targetPrereqs, ArrayList<Course> coursesTaken)
    {
        ArrayList<Course> targetTemp = new ArrayList<Course>();
        deepCopy(targetPrereqs, targetTemp);
        for(Course c : targetTemp)
        {
            if (coursesTaken.contains(c))
            {
                targetPrereqs.remove(c); 
            }
        }
    }

    protected static void addPrereqs(ArrayList<Course> targetCourses)
    {
        ArrayList<Course> temp = new ArrayList<Course>();
        boolean isDouplicate = false;
        deepCopy(targetCourses, temp);
        for(int i = 0; i < targetCourses.size(); i++)
        {
            for(int j = 0; j < targetCourses.get(i).listCoursesPrerequisits.size(); j++)
            {
                Course p = targetCourses.get(i).listCoursesPrerequisits.get(j);
                for(Course k : temp)
                {
                    if(p.equals(k))
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
                    targetCourses.add(p);
                    temp.add(p);
                    //StdOut.println(p.title + " has been added");
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
    
    protected static ArrayList<ArrayList<Course>> shortestList (ArrayList<Course> adjList, ArrayList<Course> targetPrereqs, ArrayList<Course> takenPrereqs)
    {
        
        ArrayList<Course> tempTakenPrereqs = new ArrayList<>(); 
        ArrayList<Course> totalTakenPrereqs = new ArrayList<>(); 
        SchedulePlan.addPrereqs(takenPrereqs);
        for(Course c : takenPrereqs)
        { 
            tempTakenPrereqs.add(c);
            totalTakenPrereqs.add(c);
        }
        ArrayList<Course> semesterCourses = new ArrayList<>(); 
        ArrayList<ArrayList<Course>> shortestList = new ArrayList<ArrayList<Course>>(); 
        boolean able = true;
        int i = targetPrereqs.size()-1;
        
        while( i > -1)
        {
            for(int k = 0; k < targetPrereqs.get(i).listCoursesPrerequisits.size(); k++)
            {
                Course inQuestion = targetPrereqs.get(i).listCoursesPrerequisits.get(k);
                
                if(inQuestion.listCoursesPrerequisits.isEmpty())
                {
                    able = true; 
                } else 
                if(!totalTakenPrereqs.contains(inQuestion))
                {
                    able = false; 
                    break;
                }
            }
            if(able == true)
            {
                semesterCourses.add(targetPrereqs.get(i));
                tempTakenPrereqs.add(targetPrereqs.get(i)); 
                i--; 
            } 
            if(able == false) // push semesterCourses to shortestList
            {
                for(Course c : tempTakenPrereqs)
                {
                    if(!totalTakenPrereqs.contains(c))
                    {
                        totalTakenPrereqs.add(c);
                    }
                }
                for(Course c : takenPrereqs)
                { 
                    if (semesterCourses.contains(c)) 
                        semesterCourses.remove(c);
                }
                shortestList.add(semesterCourses); 
                semesterCourses = new ArrayList<>(); 
                able = true;
            }
        }
        return shortestList; 
    }
}
