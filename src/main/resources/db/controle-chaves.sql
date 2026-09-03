-- Migracao idempotente para o contrato definitivo de Controle de Chaves.
-- Execute no banco online como owner da tabela. Nao apaga tabela nem dados.
BEGIN;

ALTER TABLE requisicoes_de_chaves
    ADD COLUMN IF NOT EXISTS dt_recebimento TIMESTAMP NULL,
    ADD COLUMN IF NOT EXISTS id_user_retirada INTEGER,
    ADD COLUMN IF NOT EXISTS id_user_recebimento INTEGER NULL,
    ADD COLUMN IF NOT EXISTS st_requisicao VARCHAR(20) NOT NULL DEFAULT 'ABERTO';

ALTER TABLE requisicoes_de_chaves
    ALTER COLUMN id_user_retirada SET NOT NULL;

-- Reutiliza a sequence criada pelo SERIAL. MAX apenas a sincroniza durante a
-- migracao; a aplicacao usa nextval e permanece segura sob concorrencia.
DO $$
DECLARE
    sequence_name TEXT := pg_get_serial_sequence('requisicoes_de_chaves', 'id_requisicao');
    ultimo_id BIGINT;
BEGIN
    IF sequence_name IS NULL THEN
        RAISE EXCEPTION 'id_requisicao precisa ter uma sequence ligada ao SERIAL';
    END IF;
    SELECT GREATEST(COALESCE(MAX(id_requisicao), 0), 10000)
      INTO ultimo_id
      FROM requisicoes_de_chaves;
    PERFORM setval(sequence_name, ultimo_id, true);
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_req_chaves_apartamento' AND conrelid = 'requisicoes_de_chaves'::regclass) THEN
        ALTER TABLE requisicoes_de_chaves ADD CONSTRAINT fk_req_chaves_apartamento FOREIGN KEY (id_apartamento_vistoria) REFERENCES apartamento_vistoria(id_apartamento_vistoria);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_req_chaves_retirante' AND conrelid = 'requisicoes_de_chaves'::regclass) THEN
        ALTER TABLE requisicoes_de_chaves ADD CONSTRAINT fk_req_chaves_retirante FOREIGN KEY (id_user_retirada) REFERENCES users(id_user);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_req_chaves_liberador' AND conrelid = 'requisicoes_de_chaves'::regclass) THEN
        ALTER TABLE requisicoes_de_chaves ADD CONSTRAINT fk_req_chaves_liberador FOREIGN KEY (id_user_liberacao) REFERENCES users(id_user);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_req_chaves_recebedor' AND conrelid = 'requisicoes_de_chaves'::regclass) THEN
        ALTER TABLE requisicoes_de_chaves ADD CONSTRAINT fk_req_chaves_recebedor FOREIGN KEY (id_user_recebimento) REFERENCES users(id_user);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'ck_req_chaves_status' AND conrelid = 'requisicoes_de_chaves'::regclass) THEN
        ALTER TABLE requisicoes_de_chaves ADD CONSTRAINT ck_req_chaves_status CHECK (st_requisicao IN ('ABERTO', 'RECEBIDO'));
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'ck_req_chaves_datas' AND conrelid = 'requisicoes_de_chaves'::regclass) THEN
        ALTER TABLE requisicoes_de_chaves ADD CONSTRAINT ck_req_chaves_datas CHECK ((st_requisicao = 'ABERTO' AND dt_recebimento IS NULL AND id_user_recebimento IS NULL) OR (st_requisicao = 'RECEBIDO' AND dt_recebimento IS NOT NULL));
    END IF;
END $$;

CREATE UNIQUE INDEX IF NOT EXISTS ux_requisicoes_de_chaves_apartamento_aberto
    ON requisicoes_de_chaves (id_apartamento_vistoria) WHERE st_requisicao = 'ABERTO';
CREATE INDEX IF NOT EXISTS ix_requisicoes_de_chaves_data
    ON requisicoes_de_chaves (dt_retirada DESC);
CREATE INDEX IF NOT EXISTS ix_requisicoes_de_chaves_status
    ON requisicoes_de_chaves (st_requisicao);

COMMIT;
