CREATE TABLE customer
(
    id       SERIAL PRIMARY KEY,
    username character varying(255) NOT NULL
);

CREATE TABLE registration
(
    registration character varying(255) NOT NULL,
    style        character varying(255)
);

CREATE TABLE listing
(
    price_pence  integer                NOT NULL,
    date_listed  date                   NOT NULL,
    registration character varying(255) NOT NULL
);

CREATE TABLE customer_owned_registrations
(
    customer_id  integer,
    registration character varying(255) NOT NULL
);

CREATE TABLE customer_wishlisted_listings
(
    customer_id  integer,
    registration character varying(255) NOT NULL
);

ALTER TABLE ONLY listing
    ADD CONSTRAINT listing_pkey PRIMARY KEY (registration);

ALTER TABLE ONLY registration
    ADD CONSTRAINT registration_pkey PRIMARY KEY (registration);

ALTER TABLE ONLY customer_owned_registrations
    ADD CONSTRAINT customer_owned_registrations_customer_fkey FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE ONLY customer_owned_registrations
    ADD CONSTRAINT customer_owned_registrations_registration_fkey FOREIGN KEY (registration) REFERENCES registration (registration);

ALTER TABLE ONLY customer_wishlisted_listings
    ADD CONSTRAINT customer_wishlisted_listings_customer_fkey FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE ONLY customer_wishlisted_listings
    ADD CONSTRAINT customer_wishlisted_listings_listing_fkey FOREIGN KEY (registration) REFERENCES listing (registration);

ALTER TABLE ONLY listing
    ADD CONSTRAINT listing_registration_fkey FOREIGN KEY (registration) REFERENCES registration (registration);