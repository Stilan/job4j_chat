create table person (
    id serial primary key not null,
    name varchar(2000),
    password varchar(2000),
    role_id integer not null references roles(id)
);

create table roles (
    id serial primary key not null,
    name varchar(2000)
);

create table message (
    id serial primary key not null,
    name varchar(2000),
    person_id integer not null references person(id)
);

create table rooms (
    id serial primary key not null,
    message varchar(2000)
);

create table person_rooms (
   id serial primary key not null,
   person_id int not null references person(id),
   rooms_id int not null references rooms(id)
);

drop table message;
select * from rooms;

insert into person (name, password, role_id)
values ('Petr', 'password', 1);

insert into roles (name)
values ('A');
insert into roles (name)
values ('B');

insert into rooms (message)
values ('Hello word');

insert into message (name, person_id)
values ('tttt', 1);

insert into person_rooms (person_id, rooms_id)
values (1, 1);