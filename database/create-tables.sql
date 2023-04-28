CREATE TABLE customer
(
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
    customer_username                character varying(255) NOT NULL,
    owned_registrations_registration character varying(255) NOT NULL
);

CREATE TABLE customer_wishlisted_listings
(
    customer_username                             character varying(255) NOT NULL,
    wishlisted_listings_registration_registration character varying(255) NOT NULL
);

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (username);

ALTER TABLE ONLY listing
    ADD CONSTRAINT listing_pkey PRIMARY KEY (registration_registration);

ALTER TABLE ONLY registration
    ADD CONSTRAINT registration_pkey PRIMARY KEY (registration);

ALTER TABLE ONLY customer_owned_registrations
    ADD CONSTRAINT customer_owned_registrations_customer_fkey FOREIGN KEY (customer_username) REFERENCES customer (username);

ALTER TABLE ONLY customer_owned_registrations
    ADD CONSTRAINT customer_owned_registrations_registration_fkey FOREIGN KEY (owned_registrations_registration) REFERENCES registration (registration);

ALTER TABLE ONLY customer_wishlisted_listings
    ADD CONSTRAINT customer_wishlisted_listings_customer_fkey FOREIGN KEY (customer_username) REFERENCES customer (username);

ALTER TABLE ONLY customer_wishlisted_listings
    ADD CONSTRAINT customer_wishlisted_listings_listing_fkey FOREIGN KEY (wishlisted_listings_registration_registration) REFERENCES listing (registration_registration);

ALTER TABLE ONLY listing
    ADD CONSTRAINT listing_registration_fkey FOREIGN KEY (registration_registration) REFERENCES registration (registration);