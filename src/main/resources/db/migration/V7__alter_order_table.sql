ALTER TABLE orders
ADD COLUMN creator_id UUID,
ADD CONSTRAINT fk_creator FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE RESTRICT,
ADD COLUMN updated_by_id UUID,
ADD CONSTRAINT fk_updated_by FOREIGN KEY (updated_by_id) REFERENCES users(id) ON DELETE RESTRICT;