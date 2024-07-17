--liquibase formatted sql

--changeset 0ntant:8
INSERT INTO residence_place(title, timezone, active) VALUES

('Владивосток', 'Asia/Vladivostok', true),
('Хабаровск', 'Asia/Vladivostok', true),
('Комсомольск-на-Амуре', 'Asia/Vladivostok', true);
