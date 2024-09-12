--liquibase formatted sql

--changeset 0ntant:9
INSERT INTO residence_place(title, timezone, active) VALUES

('Биробиджан', 'Asia/Vladivostok', true),
('Южно-Сахалинск', 'Asia/Sakhalin', true),
('Якутск', 'Asia/Yakutsk', true),
('Чита', 'Asia/Dhaka', true),
('Петропавловск-Камчатский', 'Asia/Kamchatka', true),
('Магадан', 'Asia/Magadan', true);