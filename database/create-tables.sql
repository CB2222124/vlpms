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
    price_pence               integer                NOT NULL,
    registration_registration character varying(255) NOT NULL
);

CREATE TABLE customer_owned_registrations
(
    customer_id                      integer,
    owned_registrations_registration character varying(255) NOT NULL
);

CREATE TABLE customer_wishlisted_listings
(
    customer_id                                   integer,
    wishlisted_listings_registration_registration character varying(255) NOT NULL
);

ALTER TABLE ONLY listing
    ADD CONSTRAINT listing_pkey PRIMARY KEY (registration_registration);

ALTER TABLE ONLY registration
    ADD CONSTRAINT registration_pkey PRIMARY KEY (registration);

ALTER TABLE ONLY customer_owned_registrations
    ADD CONSTRAINT customer_owned_registrations_customer_fkey FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE ONLY customer_owned_registrations
    ADD CONSTRAINT customer_owned_registrations_registration_fkey FOREIGN KEY (owned_registrations_registration) REFERENCES registration (registration);

ALTER TABLE ONLY customer_wishlisted_listings
    ADD CONSTRAINT customer_wishlisted_listings_customer_fkey FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE ONLY customer_wishlisted_listings
    ADD CONSTRAINT customer_wishlisted_listings_listing_fkey FOREIGN KEY (wishlisted_listings_registration_registration) REFERENCES listing (registration_registration);

ALTER TABLE ONLY listing
    ADD CONSTRAINT listing_registration_fkey FOREIGN KEY (registration_registration) REFERENCES registration (registration);