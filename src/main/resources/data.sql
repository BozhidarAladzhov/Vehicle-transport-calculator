INSERT INTO roles VALUES (1, 'USER'), (2, 'ADMIN');

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`, `uuid`)
VALUES
    (1,'bojidar@test.com','Bojidar','Aladjov','test','test');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
    (1, 1),
    (1, 2);