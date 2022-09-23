INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(1, 'Ivan', 'Ivanov','2000-01-01', 'patient1@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'PATIENT');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(2, 'Petro', 'Petrov','2000-02-02', 'patient2@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'PATIENT');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(3, 'Anton', 'Antonov','2000-03-03', 'patient3@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'PATIENT');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(4, 'Mariya', 'Makarova', '2000-03-03', 'nurse1@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'NURSE');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(5, 'Anastasiya', 'Stepanova', '2000-01-03', 'nurse2@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'NURSE');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(6, 'Olha', 'Havriliuk', '2000-11-03', 'nurse3@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'NURSE');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(7, 'Olexiy', 'Komarov','2000-03-03', 'doctor1@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'DOCTOR');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(8, 'Oleksandr', 'Voloshyn','2000-11-03', 'doctor2@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'DOCTOR');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(9, 'Evhen', 'Morozov','2000-10-03', 'doctor3@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'DOCTOR');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(10, 'Admin', 'Admin','2000-03-03', 'admin@gmail.com','$2a$10$ohJjTGKP/qIh0Z2Dp56mEu8BLo9q8esu7Ib4y577t7Az/ihdpQapi', 'ADMIN');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(11, 'Unset', 'doctor', '2000-11-03', 'nurse4@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'NURSE');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(12, 'Oleg', 'Nepyyvoda', '2002-11-03', 'patient4@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'PATIENT');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(13, 'Maxym', 'Tkachuk', '2001-11-14', 'patient5@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'PATIENT');
INSERT INTO users(id, name, surname, birthday, email, password, role) VALUES(14, 'Volodymyr', 'Stepanyuk', '2003-07-28', 'patient6@gmail.com','$2a$10$s2/q/B/abCZKG4pfjySa7.8eqJKWb/Xawt4u8iRPbxHnp0w9.tWfe', 'PATIENT');

INSERT INTO doctors_types(id, type) VALUES(1,'Nurse'), (2, 'ENT'), (3, 'Surgeon'), (4, 'Traumatologist');

INSERT INTO doctors(id, user_id, doctors_type_id) VALUES(1,7,2);
INSERT INTO doctors(id, user_id, doctors_type_id) VALUES(2,8,3);
INSERT INTO doctors(id, user_id, doctors_type_id) VALUES(3,9,4);
INSERT INTO doctors(id, user_id, doctors_type_id) VALUES(4,4,1);
INSERT INTO doctors(id, user_id, doctors_type_id) VALUES(5,5,1);
INSERT INTO doctors(id, user_id, doctors_type_id) VALUES(6,6,1);
INSERT INTO doctors(id, user_id, doctors_type_id) VALUES(7,11,1);

INSERT INTO assignments_types(id, type) VALUES(1,'Procedure'), (2, 'Operation'), (3, 'Medicines'), (4, 'Unset assignment type');

INSERT INTO assignments(id, executor_id, prescriber_id, description, assignments_type_id) VALUES(1, 1, 1, 'Procedure for Ivan Ivanov', 1);
INSERT INTO assignments(id, executor_id, prescriber_id, description, assignments_type_id) VALUES(2, 2, 2, 'Operation for Petro Petrov', 2);
INSERT INTO assignments(id, executor_id, prescriber_id, description, assignments_type_id) VALUES(3, 3, 3, 'Antibiotics for Anton Antonov', 3);
INSERT INTO assignments(id, executor_id, prescriber_id, description, assignments_type_id) VALUES(4, 7, 7, 'Unset assignment', 4);
INSERT INTO assignments(id, executor_id, prescriber_id, description, assignments_type_id) VALUES(5, 4, 1, 'Procedure for Oleg Nepyyvoda', 1);
INSERT INTO assignments(id, executor_id, prescriber_id, description, assignments_type_id) VALUES(6, 2, 3, 'Operation for Maxym Tkachuk', 2);
INSERT INTO assignments(id, executor_id, prescriber_id, description, assignments_type_id) VALUES(7, 5, 1, 'Medicines for Volodymyr Stepanyuk', 3);

INSERT INTO diagnosis(id, type) VALUES(1, 'Unset diagnosis'), (2,'End dislocation'), (3, 'Limb fracture'), (4, 'Otitis'), (5, 'Laryngitis');

INSERT INTO treatments(id, assignment_id, diagnosis_id) VALUES(1, 1, 5);
INSERT INTO treatments(id, assignment_id, diagnosis_id) VALUES(2, 2, 3);
INSERT INTO treatments(id, assignment_id, diagnosis_id) VALUES(3, 3, 2);
INSERT INTO treatments(id, assignment_id, diagnosis_id) VALUES(4, 4, 1);
INSERT INTO treatments(id, assignment_id, diagnosis_id) VALUES(5, 5, 4);
INSERT INTO treatments(id, assignment_id, diagnosis_id) VALUES(6, 6, 3);
INSERT INTO treatments(id, assignment_id, diagnosis_id) VALUES(7, 7, 5);

INSERT INTO patients(id, user_id, doctor_id, treatment_id) VALUES(1, 1, 1, 1);
INSERT INTO patients(id, user_id, doctor_id, treatment_id) VALUES(2, 2, 2, 2);
INSERT INTO patients(id, user_id, doctor_id, treatment_id) VALUES(3, 3, 3, 3);
INSERT INTO patients(id, user_id, doctor_id, treatment_id) VALUES(4, 12, 1, 5);
INSERT INTO patients(id, user_id, doctor_id, treatment_id) VALUES(5, 13, 3, 6);
INSERT INTO patients(id, user_id, doctor_id, treatment_id) VALUES(6, 14, 1, 7);

ALTER SEQUENCE users_id_seq RESTART WITH 15;
ALTER SEQUENCE doctors_types_id_seq RESTART WITH 5;
ALTER SEQUENCE doctors_id_seq RESTART WITH 8;
ALTER SEQUENCE assignments_types_id_seq RESTART WITH 5;
ALTER SEQUENCE assignments_id_seq RESTART WITH 8;
ALTER SEQUENCE diagnosis_id_seq RESTART WITH 6;
ALTER SEQUENCE treatments_id_seq RESTART WITH 8;
ALTER SEQUENCE patients_id_seq RESTART WITH 7;