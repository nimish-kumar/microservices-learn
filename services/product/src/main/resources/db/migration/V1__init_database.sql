create sequence if not exists category_seq increment by 1;
create sequence if not exists product_seq increment by 1;

create table if not exists category (
    id bigint primary key default nextval('category_seq'),
    name varchar(255),
    description varchar(255)
);

create table if not exists product (
    id bigint primary key default nextval('product_seq'),
    name varchar(255),
    description varchar(255),
    available_qty double precision not null,
    price numeric(38,2),
    category_id bigint
    constraint fk_catergory references category
);
