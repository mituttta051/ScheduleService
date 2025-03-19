CREATE TABLE IF NOT EXISTS week_template
(
    id       INT         NOT NULL,
    space_id INT         NOT NULL,
    name     VARCHAR(32) NOT NULL,
    data     jsonb,
    PRIMARY KEY (id, space_id),
    UNIQUE (space_id, name)
);

CREATE TABLE IF NOT EXISTS week
(
    space_id   INT  NOT NULL,
    start_date DATE NOT NULL DEFAULT CURRENT_DATE,
    data       jsonb,
    UNIQUE (space_id, start_date)
);

CREATE TABLE IF NOT EXISTS last_week_template_id
(
    space_id INT NOT NULL PRIMARY KEY,
    last_id  INT NOT NULL DEFAULT 0
);

-- Create a function to increment and return the last_id
CREATE OR REPLACE FUNCTION get_next_id(space_id INT, table_name TEXT) RETURNS INT AS
$$
DECLARE
    new_id INT;
BEGIN
    EXECUTE format('UPDATE %I SET last_id = last_id + 1 WHERE space_id = $1 RETURNING last_id',
                   table_name) INTO new_id USING space_id;
    IF new_id IS NULL THEN
        EXECUTE format('INSERT INTO %I (space_id, last_id) VALUES ($1, 1) RETURNING last_id',
                       table_name) INTO new_id USING space_id;
    END IF;
    RETURN new_id;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger function to set the default id
CREATE OR REPLACE FUNCTION set_default_id() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.id IS NULL THEN
        NEW.id := get_next_id(NEW.space_id, TG_ARGV[0]);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER set_week_template_id
    BEFORE INSERT
    ON week_template
    FOR EACH ROW
EXECUTE FUNCTION set_default_id('last_week_template_id');