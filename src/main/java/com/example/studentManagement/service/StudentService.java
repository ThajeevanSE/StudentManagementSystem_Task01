package com.example.studentManagement.service;

import com.example.studentManagement.model.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class StudentService {

    private final String COLLECTION_NAME = "students";

    public String addStudent(Student student) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document();
        student.setId(docRef.getId());
        ApiFuture<WriteResult> future = docRef.set(student);
        return "Created student with ID: " + student.getId() + " at " + future.get().getUpdateTime();
    }

    public Student getStudentById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();
        return snapshot.exists() ? snapshot.toObject(Student.class) : null;
    }

    public List<Student> getStudentsPaginated(int pageSize, String lastDocId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collection = db.collection(COLLECTION_NAME);
        Query query = collection.limit(pageSize);

        if (lastDocId != null && !lastDocId.isEmpty()) {
            DocumentSnapshot lastDoc = collection.document(lastDocId).get().get();
            if (lastDoc.exists()) {
                query = query.startAfter(lastDoc);
            }
        }

        ApiFuture<QuerySnapshot> future = query.get();
        List<Student> students = new ArrayList<>();
        for (DocumentSnapshot doc : future.get().getDocuments()) {
            students.add(doc.toObject(Student.class));
        }
        return students;
    }


    public String updateStudent(Student student) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(student.getId());
        ApiFuture<WriteResult> future = docRef.set(student);
        return future.get().getUpdateTime().toString();
    }

    public String deleteStudent(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document(id).delete();
        return "Deleted at: " + writeResult.get().getUpdateTime();
    }
}