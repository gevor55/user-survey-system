package com.gevorg.main.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "consumer_passed_surveys")
public class ConsumerPassedSurvey {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "consumer_id")
    private long consumerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consumerPassedSurvey")
    private List<ConsumerAnswer> consumerAnswers;

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

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<ConsumerAnswer> getConsumerAnswers() {
        return consumerAnswers;
    }

    public void setConsumerAnswers(List<ConsumerAnswer> consumerAnswers) {
        this.consumerAnswers = consumerAnswers;
    }
}
