package vn.java20.studentcontroller.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.java20.studentcontroller.models.StudentModel;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController("/api")
public class StudentApi {
    private static List<StudentModel> STUDENT_LIST = new ArrayList<StudentModel>(){
        {
            add(new StudentModel("student 1", 1));
            add(new StudentModel("student 2", 2));
            add(new StudentModel("student 3", 3));
            add(new StudentModel("student 4", 4));
            add(new StudentModel("student 5", 5));
            add(new StudentModel("student 6", 6));
        }
    };

    @GetMapping("/students")
    public List<StudentModel> getAllStudent(@RequestParam(value = "limit", defaultValue = "10") int limit) {
        if (this.STUDENT_LIST.size() < limit) {
            return this.STUDENT_LIST;
        }
        List<StudentModel> studentListResult = new ArrayList<StudentModel>();
        for (int i = 0; i < limit; i++) {
            studentListResult.add(this.STUDENT_LIST.get(i));
        }
        return studentListResult;
    }

    @PostMapping("/students/params")
    public ResponseEntity<StudentModel> createStudentByReqParam(@RequestParam(value="name") String name, @RequestParam(value="age", defaultValue = "-1") int age) {
        if(name == null || age == -1){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StudentModel studentModel = new StudentModel(name, age);
        this.STUDENT_LIST.add(studentModel);
        return ResponseEntity.ok(studentModel);
    }

    @PostMapping("/students/path-vars/{name}/{age}")
    public ResponseEntity<StudentModel> createStudentByPathVar(@PathVariable("name") String name, @PathVariable("age") Integer age) {
        if(name == null || age == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StudentModel studentModel = new StudentModel(name, age);
        this.STUDENT_LIST.add(studentModel);

        return ResponseEntity.ok(studentModel);
    }

    @PostMapping("/students/body")
    public ResponseEntity<StudentModel> createStudentByReqBody(@RequestBody StudentModel student) {
        if(student == null || student.getName() == null || ((Integer) student.getAge()) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.STUDENT_LIST.add(student);

        return ResponseEntity.ok(student);
    }
}
