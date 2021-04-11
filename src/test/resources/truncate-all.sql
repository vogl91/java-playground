truncate t_product cascade;
truncate t_text cascade;
truncate t_text_usage cascade;

-- DO $$ DECLARE
--     r RECORD;
-- BEGIN
--     FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
--             EXECUTE 'TRUNCATE TABLE ' || quote_ident(r.tablename) || '';
--         END LOOP;
-- END $$;

-- DO
-- $func$
--     BEGIN
--         EXECUTE
--             (SELECT 'TRUNCATE TABLE ' || string_agg(oid::regclass::text, ', ') || ' CASCADE'
--              FROM   pg_class
--              WHERE  relkind = 'r'  -- only tables
--                AND    relnamespace = 'public'::regnamespace
--             );
--     END
-- $func$;
