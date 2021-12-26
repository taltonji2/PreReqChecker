package prereqchecker;

import java.util.ArrayList;
import java.util.List;

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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {
        ValidPrereq validPrereq = new ValidPrereq();
        validPrereq.Run(args);
    }
    protected void Run(String[] args)
    {
        ArrayList<Course> adjList = new ArrayList<Course>(); 
        String title;
        StdIn.setFile("adjlist.in");
        StdOut.setFile("out.txt");
        AdjList.CreateAdjList(adjList);
        StdIn.setFile("validprereq.in");
        
        title = StdIn.readLine();
        Course course1 = helpFindCourse(adjList, title);
        title = StdIn.readLine();
        Course course2 = helpFindCourse(adjList, title);

        addHypotheticalPrerequisit(adjList, course1, course2);
        Paths paths = new Paths(adjList, course1, course2);
        List<Course> path = paths.pathTo(adjList, course1);
    }
    
    //course 1 immediate prerequiit of course 0
    protected static void addHypotheticalPrerequisit(ArrayList<Course> adjList, Course course1, Course course2){
        for(Course c : adjList)
        {
            if(c.title.equals(course1.title))
            {
                if(course2 == null)
                {
                    //StdOut.println("course2 is null");
                }
                else
                c.listCoursesPrerequisits.add(course2); 
            }
        }
    }
    protected static Course helpFindCourse (ArrayList<Course> adjList, String courseTitle)
    {
        for(Course c : adjList)
        {
            if (c.title.equals(courseTitle))
            {
                return c;
            }
        }
        return null;
    }
    
class Paths {
    private Boolean marked[];
    private Course edgeTo[];
    private Course course1;
    private Course course2;
    private Course lowest;

        Paths(ArrayList<Course> adjList, Course cour1, Course cour2)
        {
            marked = new Boolean[adjList.size()];
            edgeTo = new Course[adjList.size()]; 
            course1 = cour1;
            course2 = cour2;
            for(int i = 0; i < marked.length; i++)
            {
                marked[i] = false;
            }

            dfs(adjList, marked, edgeTo, course1);
        }

        private void dfs(ArrayList<Course> adjList, Boolean marked[], Course edgeTo[], Course v) //recursive method: used to visit and record edges of courses in the adjacency list
        {

            marked[adjList.indexOf(v)] = true;   
            //for(Course p: adjList.get(adjList.indexOf(v)).listCoursesPrerequisits)
            for(Course p : v.listCoursesPrerequisits)
            {
                int index = adjList.indexOf(p);
                //StdOut.println("index: " + index);
                if(marked[index] == false)
                {
                    edgeTo[adjList.indexOf(p)] = v;
                    //StdOut.println("Course: " + p.title + " is edgeTo " + v.title);
                    dfs(adjList, marked, edgeTo, p);
                }
            }
        }

        private boolean hasPath(ArrayList<Course> adjList, Boolean marked[], Course v)  //returns if this index has been visited
        {
            return marked[adjList.indexOf(v)];
        }

        List<Course> pathTo(ArrayList<Course> adjList, Course lowest) 
        {
            if(hasPath(adjList, marked, lowest) == false)
                return null; 
            if (foundLowestPrereq(adjList, course2) == false) 
            {
                StdOut.println("NO");
                return null;
            } else {
                StdOut.println("YES");
            }
            List<Course> path = new ArrayList<Course>();
            // for(Course x = lowest; !x.equals(lowest); x = edgeTo[adjList.indexOf(x)])
            //     path.add(x);
            // path.add(lowest);
            return path;
        }

        private Boolean foundLowestPrereq(ArrayList<Course> adjList, Course course2)
        {
            ArrayList<Course> coursePrereqs = adjList.get(adjList.indexOf(course2)).listCoursesPrerequisits;  
            for(int i = 0; i < coursePrereqs.size(); i++)
            {
                Course c = coursePrereqs.get(i);
                if(c.count > 0)
                {
                    return false;
                }
                c.count += 1;
                if(c.listCoursesPrerequisits.size() == 0)
                {
                    lowest = c; 
                    helperDelperCourseSmelter(coursePrereqs, i);
                    printCount(coursePrereqs);
                    return true;
                }
                if( false == foundLowestPrereq(adjList, c))
                {
                    helperDelperCourseSmelter(coursePrereqs, i);
                    return false;
                }
                c.count -= 1;  
            }
            printCount(coursePrereqs);
            return true;
        }

    private void helperDelperCourseSmelter(ArrayList<Course> coursePrereqs, int j)
    {
            for(int i = 0; i <= j; i++)
            {
                Course c = coursePrereqs.get(i);
                c.count -= 1;
            }
    }
    private void printCount(ArrayList<Course> coursePrereqs)
    {
            for(int i = 0; i < coursePrereqs.size(); i++)
            {
                Course c = coursePrereqs.get(i);
                //StdOut.println("Course count: " + c.count);
            }
        }
    }
}
