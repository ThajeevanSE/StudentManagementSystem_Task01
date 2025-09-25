package com.example.studentManagement.service;

import com.example.studentManagement.model.Course;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CourseService {

    private static final String COLLECTION_NAME = "courses";

    public String addCourse(Course course) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef;

        if (course.getId() == null || course.getId().isEmpty()) {
            docRef = db.collection(COLLECTION_NAME).document(); // Auto ID
            course.setId(docRef.getId());
        } else {
            docRef = db.collection(COLLECTION_NAME).document(course.getId());
        }

        ApiFuture<WriteResult> future = docRef.set(course);
        return "Course added with ID: " + course.getId() + " at " + future.get().getUpdateTime();
    }

    public Course getCourseById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot doc = docRef.get().get();

        if (doc.exists()) {
            return doc.toObject(Course.class);
        } else {
            return null;
        }
    }

    public List<Course> getAllCourses() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<Course> courseList = new ArrayList<>();

        for (DocumentSnapshot doc : future.get().getDocuments()) {
            courseList.add(doc.toObject(Course.class));
        }
        return courseList;
    }

    public String updateCourse(Course course) throws ExecutionException, InterruptedException {
        if (course.getId() == null || course.getId().isEmpty()) {
            return "Course ID is required for update!";
        }

        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(course.getId());
        ApiFuture<WriteResult> future = docRef.set(course);

        return "Course updated at: " + future.get().getUpdateTime();
    }

    public String deleteCourse(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(id).delete();
        return "Course deleted at: " + future.get().getUpdateTime();
    }
}
