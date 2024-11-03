-- Adiciona a coluna `address` à tabela `announcement` com o tipo de dado correto
ALTER TABLE announcement
    ADD COLUMN address INT UNSIGNED;  -- Corrigido para INT UNSIGNED, igual a `address.id`

-- Define a chave estrangeira, referenciando `address.id`
ALTER TABLE announcement
    ADD CONSTRAINT fk_announcement_address
        FOREIGN KEY (address) REFERENCES address(id);
