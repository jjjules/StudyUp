package ch.epfl.sweng.studyup.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studyup.firebase.Firestore;
import ch.epfl.sweng.studyup.player.UserData;
import ch.epfl.sweng.studyup.questions.Question;

import static ch.epfl.sweng.studyup.utils.Constants.FB_ANSWERED_QUESTIONS;
import static ch.epfl.sweng.studyup.utils.Constants.FB_COURSE;
import static ch.epfl.sweng.studyup.utils.Constants.FB_COURSES_ENROLLED;
import static ch.epfl.sweng.studyup.utils.Constants.FB_FIRSTNAME;
import static ch.epfl.sweng.studyup.utils.Constants.FB_LASTNAME;
import static ch.epfl.sweng.studyup.utils.Constants.FB_QUESTIONS;
import static ch.epfl.sweng.studyup.utils.Constants.FB_QUESTION_ANSWER;
import static ch.epfl.sweng.studyup.utils.Constants.FB_QUESTION_LANG;
import static ch.epfl.sweng.studyup.utils.Constants.FB_QUESTION_TITLE;
import static ch.epfl.sweng.studyup.utils.Constants.FB_QUESTION_TRUEFALSE;
import static ch.epfl.sweng.studyup.utils.Constants.FB_SCIPER;
import static ch.epfl.sweng.studyup.utils.Constants.FB_USERS;
import static ch.epfl.sweng.studyup.utils.Constants.INITIAL_FIRSTNAME;
import static ch.epfl.sweng.studyup.utils.Constants.INITIAL_LASTNAME;
import static ch.epfl.sweng.studyup.utils.Constants.INITIAL_SCIPER;
import static ch.epfl.sweng.studyup.utils.Utils.getCourseListFromStringList;
import static ch.epfl.sweng.studyup.utils.Utils.getOrDefault;

public class StatsUtils {

    public static void loadAllQuestions(final Callback callback) throws NullPointerException {

        final List<Question> questionList = new ArrayList<>();

        Firestore.get().getDb().collection(FB_QUESTIONS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> remoteQuestionData = document.getData();
                        String questionId = document.getId();
                        String questionTitle = (String) remoteQuestionData.get(FB_QUESTION_TITLE);
                        Boolean questionTrueFalse = (Boolean) remoteQuestionData.get(FB_QUESTION_TRUEFALSE);
                        int questionAnswer = Integer.parseInt((remoteQuestionData.get(FB_QUESTION_ANSWER)).toString());
                        String questionCourseName = remoteQuestionData.get(FB_COURSE).toString();
                        String langQuestion = remoteQuestionData.get(FB_QUESTION_LANG).toString();

                        Question question = new Question(questionId, questionTitle, questionTrueFalse, questionAnswer, questionCourseName, langQuestion);

                        questionList.add(question);
                    }

                    callback.call(questionList);

                } else Log.e(this.getClass().getSimpleName(), "Error getting documents for courses: ", task.getException());
            }
        });
    }

    public static void loadUsers(final Callback callback) {
        final List<UserData> userList = new ArrayList<>();

        Firestore.get().getDb().collection(FB_USERS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Map<String, Object> remotePlayerData = document.getData();

                        UserData user = new UserData(INITIAL_SCIPER,
                                INITIAL_FIRSTNAME,
                                INITIAL_LASTNAME,
                                new HashMap<String, List<String>>(),
                                new ArrayList<Constants.Course>());
                        user.setSciperNum(getOrDefault(remotePlayerData, FB_SCIPER, INITIAL_SCIPER).toString());
                        user.setFirstName(getOrDefault(remotePlayerData, FB_FIRSTNAME, INITIAL_FIRSTNAME).toString());
                        user.setLastName(getOrDefault(remotePlayerData, FB_LASTNAME, INITIAL_LASTNAME).toString());
                        user.setAnsweredQuestions((HashMap<String, List<String>>) getOrDefault(remotePlayerData, FB_ANSWERED_QUESTIONS, new HashMap<>()));
                        user.setCourses(getCourseListFromStringList((List<String>) getOrDefault(remotePlayerData, FB_COURSES_ENROLLED, new ArrayList<Constants.Course>())));

                        userList.add(user);
                    }

                    callback.call(userList);
                } else {
                    Log.e(this.getClass().getSimpleName(), "Error getting documents for courses: ", task.getException());
                }
            }
        });
    }

    public static List<UserData> getStudentsForCourse(List<UserData> userList, Constants.Course course){
        List<UserData> usersEnrolledInCourse = new ArrayList<>();
        for (UserData user: userList) {
            if(user.getCourses().contains(course)){
                usersEnrolledInCourse.add(user);
            }
        }
        return usersEnrolledInCourse;
    }

    public static List<String> getQuestionIdsForCourse(List<Question> questionList, Constants.Course course){
        List<String> questStrFromCourse = new ArrayList<>();
        for (Question q: questionList) {
            if(q.getCourseName().equals(course.name())){
                questStrFromCourse.add(q.getQuestionId());
            }
        }
        return questStrFromCourse;
    }
}
