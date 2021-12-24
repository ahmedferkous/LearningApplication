package com.example.testapplication.Items;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

public class TestItem implements Parcelable {
    protected TestItem(Parcel in) {
        testName = in.readString();
        testDescription = in.readString();
    }

    public static final Creator<TestItem> CREATOR = new Creator<TestItem>() {
        @Override
        public TestItem createFromParcel(Parcel in) {
            return new TestItem(in);
        }

        @Override
        public TestItem[] newArray(int size) {
            return new TestItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(testName);
        dest.writeString(testDescription);
    }

    public static final String TESTS = "tests";
    public static final String TEST_NAME = "testName";
    public static final String TEST_DESCRIPTION = "testDescription";
    public static final String QUESTIONS = "questions";
    public static final String USERS_ASSIGNED = "usersAssigned";

    private String testName, testDescription;
    private ArrayList<Question> questions;
    private AssignedUser assignedUser;

    public TestItem(String testName, String testDescription, ArrayList<Question> questions, AssignedUser assignedUser) {
        this.testName = testName;
        this.testDescription = testDescription;
        this.questions = questions;
        this.assignedUser = assignedUser;
    }

    public TestItem() {
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public AssignedUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(AssignedUser assignedUser) {
        this.assignedUser = assignedUser;
    }

    public static class AssignedUser {
        public static final String ANSWERED_QUESTIONS = "answeredQuestions";
        public static final String PROGRESS = "progress";

        private Map<Integer, Object> answeredQuestions;
        private String progress;

        public AssignedUser(Map<Integer, Object> answeredQuestions, String progress) {
            this.answeredQuestions = answeredQuestions;
            this.progress = progress;
        }

        public Map<Integer, Object> getAnsweredQuestions() {
            return answeredQuestions;
        }

        public void setAnsweredQuestions(Map<Integer, Object> answeredQuestions) {
            this.answeredQuestions = answeredQuestions;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }
    }
}
