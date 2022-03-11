create table surveys
(
    id              int auto_increment primary key,
    title           varchar(250) not null,
    start_date      timestamp    not null,
    expiration_date timestamp    not null,
    description     varchar(250) not null
);

create table questions
(
    id            int auto_increment primary key,
    question_text varchar(250) not null,
    question_type varchar(250) not null,
    survey_id     int          not null,
    foreign key (survey_id) references surveys (id)
);

create table answer_options
(
    id          int auto_increment,
    question_id int not null,
    answer_text varchar(250),
    primary key answer_options (id),
    foreign key answer_options (question_id) references questions (id)
);

create table consumer_passed_surveys
(
    id          int auto_increment,
    consumer_id int not null,
    survey_id   int not null,
    primary key consumer_passed_surveys (id),
    foreign key consumer_passed_surveys (survey_id) references surveys (id),
    constraint consumer_passed_survey_unq
        unique (consumer_id, survey_id)
);

create table consumer_answers
(
    id                        int auto_increment not null,
    consumer_id               int                not null,
    consumer_passed_survey_id int                not null,
    question_id               int                not null,
    answer_option_id          int,
    answer_text               varchar(250),
    primary key consumer_question_answers (id),
    foreign key consumer_question_fk (question_id) references questions (id),
    foreign key consumer_question_answer_option_fk (answer_option_id) references answer_options (id),
    foreign key consumer_passed_surveys_fk (consumer_passed_survey_id) references consumer_passed_surveys (id)
);
