package prereqchecker;

import java.util.ArrayList;

/*
* Class object to abstract CS courses
* Class stores a String course title
* Class stores an arraylist of immediate prerequisites for the course
*/

public class Course {
    String title; 
    int count;
    ArrayList<Course> listCoursesPrerequisits;
    Course() {
        title = null;
        listCoursesPrerequisits = new ArrayList<>(); 
    }
    Course(String word) {
        title = word.trim();
        listCoursesPrerequisits = new ArrayList<>(); 
    }
    @Override public int hashCode(){
        return title.hashCode();
    }
    @Override public boolean equals(Object obj){
        Course ofCourse = (Course)obj;
        return title.equals(ofCourse.title);
    }
}

