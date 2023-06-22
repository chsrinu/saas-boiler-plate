CREATE TABLE IF NOT EXISTS login_details (
  `user_name` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_name`));

CREATE TABLE IF NOT EXISTS roles (
  `role_id` INT NOT NULL,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`, `role_name`));

CREATE TABLE `users_roles` (
  `user_name` VARCHAR(255) NOT NULL,
  `role_id` INT NOT NULL,
  FOREIGN KEY (user_name) references login_details(user_name),
  FOREIGN KEY (role_id) references roles(role_id),
  PRIMARY KEY (user_name, role_id)
);

insert into roles (role_id, role_name) values (1, 'IT-ADMIN');
insert into roles (role_id, role_name) values (2, 'HR-ADMIN');
insert into roles (role_id, role_name) values (3, 'PAYROLL-ADMIN');
insert into roles (role_id, role_name) values (4, 'IT-DEPT');
insert into roles (role_id, role_name) values (5, 'HR-DEPT');
insert into roles (role_id, role_name) values (6, 'PAYROLL-DEPT');
insert into roles (role_id, role_name) values (7, 'EMPLOYEE');
 -- BY DEFAULT ADMIN WILL BE MAPPED TO ALL THE 7 ROLES




