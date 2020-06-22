insert into user_entity(id, first_name, last_name, email, password_hash) values (1, 'Sandra', 'Giermanska', 'sg@gmail.com', '1000:e30d5c33238b33a5065ac5ff13f1b548:24b854626aad97c937a6c6fbcaf753066c7d329d33b8e3d8312ab2d0d9ccdd65400070f7fda62067edbc05b79be6232e238b812631d4197f60d45337481548b4');
insert into user_entity(id, first_name, last_name, email, password_hash) values (2, 'Anna', 'Nowak', 'an@yahoo.com', 'avc,fxrhafhabshvtjhgtayxrgfy');
insert into user_entity(id, first_name, last_name, email, password_hash) values (3, 'Zbyszek', 'Nowak', 'zbych@yahoo.com', 'qqqfggthtnhkjshvtjhgtayxrgfy');
insert into user_entity(id, first_name, last_name, email, password_hash) values (4, 'Antek', 'Bomba', 'antekb@o2.pl', 'ahurhbbngzzdgthjbngdzgbn');

insert into application_entity(id, name) values (1, 'netflix');
insert into application_entity(id, name) values (2, 'spotify');

insert into package_entity(id, name, application_id) values (1, 'rodzinny', 1);
insert into package_entity(id, name, application_id) values (2, 'student', 2);

insert into group_entity(id, name, owner_id, package_id, bank_account_number, max_number_of_members, login_hash, password_hash, group_hash) values (1, 'moj netflix', 1, 1, '12345567890000234642', 6, 'zrjcvgmjytrznycrjn', 'yvsxngtkvs', 'sktcgsnyt');
insert into group_entity(id, name, owner_id, package_id, bank_account_number, max_number_of_members, login_hash, password_hash, group_hash) values (2, 'moje spotify', 1, 2, '12345567890000234642', 1, null, null, null);
insert into group_entity(id, name, owner_id, package_id, bank_account_number, max_number_of_members, login_hash, password_hash, group_hash) values (3, 'inny netflix', 2, 1, '19999999990000234642', 6, 'aystkgcny asycti', 'yvsililililtkvs', 'ryfrghfsgvfg');

insert into user_in_group(user_id, group_id, is_confirmed) values (1, 1, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (1, 2, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (2, 3, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (3, 3, false);
insert into user_in_group(user_id, group_id, is_confirmed) values (3, 1, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (4, 1, false);

insert into payment_entity (id, deadline, price, user_id, group_id, is_paid) values (1, null, 20.56, 1, 1, true);
insert into payment_entity (id, deadline, price, user_id, group_id, is_paid) values (2, null, 10.20, 1, 1, false);
insert into payment_entity (id, deadline, price, user_id, group_id, is_paid) values (3, null, 5.20, 3, 3, false);
insert into payment_entity (id, deadline, price, user_id, group_id, is_paid) values (4, null, 10.20, 2, 3, false);
insert into payment_entity (id, deadline, price, user_id, group_id, is_paid) values (5, null, 5.20, 2, 3, true);
insert into payment_entity (id, deadline, price, user_id, group_id, is_paid) values (6, null, 0.20, 1, 2, false);



