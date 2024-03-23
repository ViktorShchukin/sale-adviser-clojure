create table if not exists stock_balance(
    id UUID not null,
    product_id UUID not null,
    quantity integer not null,
    sb_timestamp timestamp not null,

    constraint stock_balance_pk primary key (id),
    constraint stock_balance_fk foreign key (product_id) references product(id),
    constraint stock_balance_unique unique (product_id, sb_timestamp)
    );