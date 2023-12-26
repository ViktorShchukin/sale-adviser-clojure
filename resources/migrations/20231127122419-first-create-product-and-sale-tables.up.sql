CREATE TABLE IF NOT EXISTS product (
    id UUID NOT NULL,
    name TEXT NOT NULL,

    CONSTRAINT product_pk PRIMARY KEY (id),
    CONSTRAINT product_unique UNIQUE (name)
    );
--;;
CREATE TABLE IF NOT EXISTS sale (
    id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    total_sum REAL NOT NULL DEFAULT 0,
    sale_date TIMESTAMP NOT NULL,

    CONSTRAINT sale_pk PRIMARY KEY (id),
    CONSTRAINT sale_product_fk FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT sale_product_id_sale_date_unique UNIQUE (product_id, sale_date)
    );