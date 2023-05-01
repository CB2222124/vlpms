CREATE OR REPLACE FUNCTION random_current_registration() RETURNS VARCHAR AS
$$
DECLARE
    result VARCHAR;
BEGIN
    result := chr(65 + floor(random() * 26)::int) ||
              chr(65 + floor(random() * 26)::int) ||
              floor(random() * 10)::text ||
              floor(random() * 10)::text ||
              chr(65 + floor(random() * 26)::int) ||
              chr(65 + floor(random() * 26)::int) ||
              chr(65 + floor(random() * 26)::int);
    RETURN result;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION random_prefix_registration() RETURNS VARCHAR AS
$$
DECLARE
    result VARCHAR;
BEGIN
    result := chr(65 + floor(random() * 26)::int) ||
              CASE
                  WHEN floor(random() * 2)::int = 0 THEN floor(random() * 10)::text
                  ELSE floor(random() * 1000)::text END ||
              chr(65 + floor(random() * 26)::int) ||
              chr(65 + floor(random() * 26)::int) ||
              chr(65 + floor(random() * 26)::int);
    RETURN result;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION random_suffix_registration() RETURNS VARCHAR AS
$$
DECLARE
    result VARCHAR;
BEGIN
    result := chr(65 + floor(random() * 26)::int) ||
              chr(65 + floor(random() * 26)::int) ||
              chr(65 + floor(random() * 26)::int) ||
              CASE
                  WHEN floor(random() * 2)::int = 0 THEN floor(random() * 10)::text
                  ELSE floor(random() * 1000)::text END ||
              chr(65 + floor(random() * 26)::int);
    RETURN result;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION list_random_current_registration()
    RETURNS VOID AS
$$
DECLARE
    new_registration VARCHAR;
    new_price_pence  INTEGER;
BEGIN
    new_registration := random_current_registration();
    new_price_pence := floor(random() * (1000)) * 100::int;
    INSERT INTO registration (registration, style)
    VALUES (new_registration, 'Current')
    ON CONFLICT DO NOTHING;
    INSERT INTO listing (registration, price_pence)
    VALUES (new_registration, new_price_pence)
    ON CONFLICT DO NOTHING;
END;
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION list_random_prefix_registration()
    RETURNS VOID AS
$$
DECLARE
    new_registration VARCHAR;
    new_price_pence  INTEGER;
BEGIN
    new_registration := random_prefix_registration();
    new_price_pence := floor(random() * 10 + 1)::int;
    INSERT INTO registration (registration, style)
    VALUES (new_registration, 'Prefix')
    ON CONFLICT DO NOTHING;
    INSERT INTO listing (registration, price_pence)
    VALUES (new_registration, new_price_pence)
    ON CONFLICT DO NOTHING;
END;
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION list_random_suffix_registration()
    RETURNS VOID AS
$$
DECLARE
    new_registration VARCHAR;
    new_price_pence  INTEGER;
BEGIN
    new_registration := random_suffix_registration();
    new_price_pence := floor(random() * 10 + 1)::int;
    INSERT INTO registration (registration, style)
    VALUES (new_registration, 'Suffix')
    ON CONFLICT DO NOTHING;
    INSERT INTO listing (registration, price_pence)
    VALUES (new_registration, new_price_pence)
    ON CONFLICT DO NOTHING;
END;
$$
    LANGUAGE plpgsql;

