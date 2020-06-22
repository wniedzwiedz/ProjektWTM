create table application_entity(id int not null primary key, name varchar(50) not null);

create table package_entity(id int not null primary key, name varchar(50) not null, application_id int not null, constraint package_application foreign key (application_id) references application_entity);

create table user_entity(id int not null primary key, first_name varchar(50) not null, last_name varchar(50) not null, email varchar(50) unique not null, password_hash varchar(255) not null);

create table group_entity(id int not null primary key, name varchar(50) not null, owner_id int not null, package_id int, bank_account_number varchar(50), max_number_of_members int, information varchar(255), payment_info varchar(255), constraint group_owner foreign key (owner_id) references user_entity, constraint group_package foreign key (package_id) references package_entity);

create table user_in_group(user_id int not null, group_id int not null, is_confirmed boolean not null default false,  primary key (user_id, group_id));

create sequence user_seq increment 1 start 100;
create sequence app_seq increment 1 start 100;
create sequence pack_seq increment 1 start 100;
create sequence group_seq increment 1 start 100;

