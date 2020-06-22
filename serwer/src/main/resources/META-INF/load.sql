insert into user_entity(id, first_name, last_name, email, password_hash) values (1, 'Sandra', 'Giermanska', 'sg@gmail.com', '1000:e30d5c33238b33a5065ac5ff13f1b548:24b854626aad97c937a6c6fbcaf753066c7d329d33b8e3d8312ab2d0d9ccdd65400070f7fda62067edbc05b79be6232e238b812631d4197f60d45337481548b4');
insert into user_entity(id, first_name, last_name, email, password_hash) values (2, 'Anna', 'Nowak', 'an@yahoo.com', '1000:577d6d724679b7178de6fdc6527b1079:76c77d18873fab4e42e5a1ef3b8319de0e23c638f81a2ef55e269ee1c14b851481fc43c0c46f888715b84de4284cd6284124258c2abe4a85ad4acaad140f6920');
insert into user_entity(id, first_name, last_name, email, password_hash) values (3, 'Zbyszek', 'Nowak', 'zbych@yahoo.com', '1000:7d462e23683d2df41cf9c8ba8b4aa3ce:68f5987f54312889c77bfe41e2c87b5c5a84a52a59fd77bea529b179bbc3dc0e51e0c67a0ad37c3c615a00f73506822c63bf73ac2068548bbd5a6edb44b87f04');
insert into user_entity(id, first_name, last_name, email, password_hash) values (4, 'Antek', 'Bomba', 'antekb@o2.pl', '1000:00de0b2419aa55001515491b9c2c1b41:1571029640894d78dee6baa0008c7045fc1e425f85d3a5af6489018ddff5d9489d431feb5da68435d620a5b3d8c01f8e80d5266715961e1ed24c3ca3c1430594');

insert into application_entity(id, name) values (1, 'netflix');
insert into application_entity(id, name) values (2, 'spotify');

insert into package_entity(id, name, application_id) values (1, 'rodzinny', 1);
insert into package_entity(id, name, application_id) values (2, 'student', 2);

insert into group_entity(id, name, owner_id, package_id, bank_account_number, max_number_of_members, information, payment_info) values (1, 'moj netflix', 1, 1, '12345567890000234642', 6, 'informacja', 'placimy do konca miesiaca 10 zl');
insert into group_entity(id, name, owner_id, package_id, bank_account_number, max_number_of_members, information, payment_info) values (2, 'moje spotify', 1, 2, '12345567890000234642', 1, null, null, null);
insert into group_entity(id, name, owner_id, package_id, bank_account_number, max_number_of_members, information, payment_info) values (3, 'inny netflix', 2, 1, '19999999990000234642', 6, 'haslo podam mailem', 'placimy do 15-stego 5.50');

insert into user_in_group(user_id, group_id, is_confirmed) values (1, 1, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (1, 2, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (2, 3, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (3, 3, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (3, 1, true);
insert into user_in_group(user_id, group_id, is_confirmed) values (4, 1, true);


