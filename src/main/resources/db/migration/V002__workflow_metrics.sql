create role read_only;
create role full_access;

create table BATCH (
    batch_id bigint AUTO_INCREMENT primary key
    ,name varchar(10) not null
    ,market_group varchar(50) not null
    ,market_region varchar(50) not null
    ,market_domain varchar(50) not null
    ,valuation_profile varchar(50) not null
    ,business varchar(50) not null
);

create table USER (
    user_id int AUTO_INCREMENT primary key
    ,name varchar(100) not null
);

create table WORKFLOW (
    workflow_id bigint AUTO_INCREMENT primary key
    ,workflow_guid VARBINARY(30) not null
    ,batch_id bigint not null
    ,start_time date
    ,end_time date
    ,user_id int
);
alter table WORKFLOW add constraint fk_event_id foreign key (batch_id) references BATCH(batch_id);

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
    ,start_time date
    ,end_time date
    ,parent int
    ,ordinal int
);
alter table WORKFLOW_TASK add constraint fk_wt_task_id foreign key (task_id) references TASK(task_id);
alter table WORKFLOW_TASK add constraint fk_wt_workflow_id foreign key (workflow_id) references WORKFLOW(workflow_id);

