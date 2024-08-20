set search_path to payment_service;

ALTER SEQUENCE account_id_seq RESTART WITH 1;
ALTER SEQUENCE transaction_id_seq RESTART WITH 1;
ALTER SEQUENCE merchant_id_seq RESTART WITH 1;

delete from transaction;
delete from account;
delete from merchant;
delete from mcc;

insert into account (balance_cash, balance_food, balance_meal)
values (1000.00, 500.00, 250.00),
       (2000.00, 1000.00, 500.00),
       (30000.00, 1500.00, 750.00);

insert into mcc (id, code, balance_type)
values (1, '5811', 'MEAL'),
       (2, '5812', 'MEAL'),
       (3, '5411', 'FOOD'),
       (4, '5412', 'FOOD'),
       (5, '9999', 'CASH');

insert into merchant (name, mcc_id)
values ('UBER TRIP                   SAO PAULO BR', 5),
       ('UBER EATS                   SAO PAULO BR', 3),
       ('PAG*JoseDaSilva          RIO DE JANEI BR', 2),
       ('PICPAY*BILHETEUNICO           GOIANIA BR', 5);
