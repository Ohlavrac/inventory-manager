CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE orders (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY UNIQUE NOT NULL,
    order_name VARCHAR(200) NOT NULL,
    quant_order INT NOT NULL,
    order_description VARCHAR(200) NOT NULL,

    product_id UUID NOT NULL,

    CONSTRAINT fk_product_order FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)