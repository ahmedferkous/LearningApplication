package com.example.testapplication.Items;

import java.util.ArrayList;

public abstract class Question {
    public static final String TYPE = "type";
    public static final String QUESTION = "question";
    public static final String POSITION = "position";

    private String type, question;
    private long position;

    public Question(String type, String question, long position) {
        this.type = type;
        this.question = question;
        this.position = position;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static class ShortAnswer extends Question {
        public static final String CORRECT_ANSWER = "correctAnswer";
        public static final String SHORT_ANSWER = "shortAnswer";

        private String correctAnswer;

        public ShortAnswer(String type, String question, long position, String correctAnswer) {
            super(type, question, position);
            this.correctAnswer = correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }

    public static class CheckList extends Question {
        public static final String CHOICES = "choices";
        public static final String CORRECT_ANSWERS = "correctAnswers";
        public static final String CHECKLIST = "checklist";

        private ArrayList<String> choices, correctAnswer;

        public CheckList(String type, String question, long position, ArrayList<String> choices, ArrayList<String> correctAnswer) {
            super(type, question, position);
            this.choices = choices;
            this.correctAnswer = correctAnswer;
        }

        public void setChoices(ArrayList<String> choices) {
            this.choices = choices;
        }

        public void setCorrectAnswer(ArrayList<String> correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public ArrayList<String> getChoices() {
            return choices;
        }

        public ArrayList<String> getCorrectAnswer() {
            return correctAnswer;
        }
    }

    public static class MultipleChoice extends Question {
        public static final String CHOICES = "choices";
        public static final String CORRECT_ANSWER = "correctAnswer";
        public static final String MULTIPLE_CHOICE = "multipleChoice";

        private ArrayList<String> choices;
        private String correctAnswer;

        public MultipleChoice(String type, String question, long position, ArrayList<String> choices, String correctAnswer) {
            super(type, question, position);
            this.choices = choices;
            this.correctAnswer = correctAnswer;
        }

        public void setChoices(ArrayList<String> choices) {
            this.choices = choices;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public ArrayList<String> getChoices() {
            return choices;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
