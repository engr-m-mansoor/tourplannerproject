create table if not exists tours
(
    id             integer not null
    primary key,
    name           text    not null,
    description    text,
    "from"         text,
    "to"           text,
    transport_type text,
    distance       text,
    estimated_time text,
    route_info     text,
    ratings        integer
);

alter table tours
    owner to postgres;

create table if not exists tours_logs
(
    id         serial
    constraint tours_logs_pk
    primary key,
    tour_id    integer not null,
    date       text,
    comment    text,
    difficulty text,
    total_time text,
    rating     text
);

alter table tours_logs
    owner to postgres;

