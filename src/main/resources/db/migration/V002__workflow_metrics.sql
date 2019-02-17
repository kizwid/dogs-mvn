create table EVENT (
    event_id bigint AUTO_INCREMENT primary key
    ,env varchar(10) not null
    ,region varchar(50) not null
    ,domain varchar(50) not null
    ,event_type varchar(50) not null
    ,key_type varchar(50) not null
    ,key_name varchar(50) not null
    ,timestamp timestamp not null
);

create table WORKFLOW (
    workflow_id bigint AUTO_INCREMENT primary key
    ,event_id bigint not null
    ,name varchar(255)
    ,start_time date
    ,creation_user bigint
);
alter table WORKFLOW add constraint fk_event_id foreign key (event_id) references EVENT(event_id);

create table TASK_TYPE (
    task_type_id int AUTO_INCREMENT primary key
    ,name varchar(255)
);

create table TASK (
    task_id int AUTO_INCREMENT primary key
    ,task_type_id int not null
    ,name varchar(255)
    ,parent int
    ,ordinal int
);
alter table TASK add constraint fk_task_id foreign key (task_type_id) references TASK_TYPE(task_type_id);

create table WORKFLOW_TASK (
    workflow_task_id int AUTO_INCREMENT primary key
    ,workflow_id bigint not null
    ,task_id int not null
    ,parent int
    ,ordinal int
);
alter table WORKFLOW_TASK add constraint fk_wt_task_id foreign key (task_id) references TASK(task_id);
alter table WORKFLOW_TASK add constraint fk_wt_workflow_id foreign key (workflow_id) references WORKFLOW(workflow_id);