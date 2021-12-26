
package prereqchecker;
import java.util.ArrayList;

/**
 * 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */

public class AdjList {
    public static void main(String[] args) {
        AdjList adjListClass = new AdjList();
        adjListClass.Run(args);
    }
    
    protected void Run(String[] args)
    {
        StdIn.setFile("adjlist.in");
        StdOut.setFile("out.txt");
        //StdIn.setFile(args[0]);
        //StdOut.setFile(args[1]);
    
        ArrayList<Course> adjList = new ArrayList<Course>(); 
        //try
        {
            CreateAdjList(adjList);
            Print(adjList);
        }
        //catch(Exception ex)
        //{
         //   StdOut.print("Exception " + ex.toString());            
        //}
    }

    protected static void CreateAdjList(ArrayList<Course> adjList){

    int numberOfCourses = -1; // number of courses
    int numberOfEdges = -1; // number of edges  

    boolean check = true;
    int compare = 0;
        while(StdIn.hasNextLine())
        {
            String[] word = StdIn.readLine().split(" ");
            try 
            {
                compare = Integer.parseInt(word[0]);
                check = true;
            } catch (NumberFormatException e)
            {
                check = false;
            } 
            if (check) { 
                if (compare >= 0 && numberOfCourses == -1){
                    numberOfCourses = compare;
                    check = false;
                } else {
                    numberOfEdges = compare;
                } 
                //all courses are loaded without prerequisits
            }
            else { //create course instnace 
                if(numberOfEdges > -1){
                    for(Course c : adjList){
                        String title0 = word[0].trim();
                        String title1 = word[1].trim();
                        if (c.title.equals(title0)){
                            for(int x=0;x<adjList.size();x++)
                            {
                                Course course1 = adjList.get(x);
                                if(course1.title.equals(title1))
                                {
                                    c.listCoursesPrerequisits.add(course1); 
                                    break;
                                }
                            }
                        }    
                    }
                }   
                if(adjList.size() < numberOfCourses){
                    Course course = new Course(word[0]); 
                    adjList.add(course);
                }
            } 
        }
    }

    protected static void Print (ArrayList<Course> adjList)
    {
        for(Course c : adjList)
        {
            StdOut.print(c.title + " ");
            for(Course p : c.listCoursesPrerequisits)
            {
                StdOut.print (p.title + " ");
            }
            StdOut.println();
        }   
    }
}


