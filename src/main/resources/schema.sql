create table if not exists enjoytrip.sido
(
    sido_code int         not null
        primary key,
    sido_name varchar(30) null
)
    charset = utf8;

create table if not exists enjoytrip.gugun
(
    gugun_code int         not null,
    gugun_name varchar(30) null,
    sido_code  int         not null,
    primary key (gugun_code, sido_code),
    constraint gugun_to_sido_sido_code_fk
        foreign key (sido_code) references enjoytrip.sido (sido_code)
)
    charset = utf8;

create table if not exists enjoytrip.attraction_info
(
    content_id      int             not null
        primary key,
    content_type_id int             null,
    title           varchar(100)    null,
    address1        varchar(100)    null,
    address2        varchar(50)     null,
    zipcode         varchar(50)     null,
    tel             varchar(50)     null,
    first_image     varchar(200)    null,
    first_image2    varchar(200)    null,
    read_count      int             null,
    sido_code       int             null,
    gugun_code      int             null,
    latitude        decimal(20, 17) null,
    longitude       decimal(20, 17) null,
    m_level         varchar(2)      null,
    constraint attraction_to_gugun_code_fk
        foreign key (gugun_code) references enjoytrip.gugun (gugun_code),
    constraint attraction_to_sido_code_fk
        foreign key (sido_code) references enjoytrip.sido (sido_code)
)
    charset = utf8;