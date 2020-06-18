create table application_entity(id int not null primary key, name varchar(50) not null);

create table package_entity(id int not null primary key, name varchar(50) not null, application_id int not null, constraint package_application foreign key (application_id) references application_entity);

create table user_entity(id int not null primary key, first_name varchar(50) not null, last_name varchar(50) not null, email varchar(50) unique not null, password_hash varchar (255) not null);

create table group_entity(id int not null primary key, name varchar(50) not null, owner_id int not null, package_id int, bank_account_number varchar(50), max_number_of_members int, login_hash varchar(255), password_hash varchar(255), group_hash varchar(255), constraint group_owner foreign key (owner_id) references user_entity, constraint group_package foreign key (package_id) references package_entity);

create table user_in_group(user_id int not null, group_id int not null, is_confirmed boolean not null default false,  primary key (user_id, group_id));

create table payment_entity (id int not null primary key, deadline timestamp, price double not null, user_id int not null, group_id int not null, is_paid boolean not null default false, constraint payment_user foreign key (user_id) references user_entity, constraint payment_group foreign key (group_id) references group_entity);

create sequence user_seq increment 1 start 100;
create sequence app_seq increment 1 start 100;
create sequence pack_seq increment 1 start 100;
create sequence group_seq increment 1 start 100;
create sequence pay_seq increment 1 start 100;

