INSERT INTO roles VALUES (1, 'USER'), (2, 'ADMIN');

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`, `uuid`)
VALUES
    (1,'bojidar@mail.com','Bojidar','Aladjov','9f142e06f7f6d085ae1bf9f2cc4b62df3b56d6dc11c43c6e8b8e1d60a93688ea72c474b0408eb53c','42aecf70-f998-4cb0-ad99-8fb66dcacae6');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
    (1, 1),
    (1, 2);