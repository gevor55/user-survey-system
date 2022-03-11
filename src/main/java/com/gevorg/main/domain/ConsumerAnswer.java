package com.gevorg.main.domain;

import javax.persistence.*;

@Entity
@Table(name = "consumer_answers")
public class ConsumerAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "consumer_id")
    private long consumerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_option_id")
    private AnswerOption answerOption;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consumer_passed_survey_id")
    private ConsumerPassedSurvey consumerPassedSurvey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnswerOption getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(AnswerOption answerOption) {
        this.answerOption = answerOption;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public ConsumerPassedSurvey getConsumerPassedSurvey() {
        return consumerPassedSurvey;
    }

    public void setConsumerPassedSurvey(ConsumerPassedSurvey consumerPassedSurvey) {
        this.consumerPassedSurvey = consumerPassedSurvey;
    }
}
