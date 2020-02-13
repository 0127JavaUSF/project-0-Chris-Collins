
CREATE TABLE users(
	user_id serial PRIMARY KEY,
	username varchar(20) UNIQUE NOT NULL,
	user_pass varchar(18)NOT NULL ,
	first_name varchar(40) NOT NULL ,
	last_name varchar(40) NOT NULL ,
	pin SMALLINT NOT NULL ,
	phonenumber varchar(12) UNIQUE NOT NULL,
	CHECK (character_length(username) > 8),
	CHECK (character_length(user_pass) > 6),
	CHECK (character_length(phonenumber) = 12),
	CHECK (pin > 999 AND pin < 10000)
	
);
ALTER TABLE users ADD CONSTRAINT users_user_pass_check CHECK (
   character_length(user_pass) > 5
);
ALTER TABLE users DROP CONSTRAINT "users_user_pass_check"

ALTER TABLE users ADD CONSTRAINT users_username_check CHECK (
   character_length(username) > 3
);
ALTER TABLE users DROP CONSTRAINT "users_username_check"


CREATE TYPE account_type_enum AS ENUM ('Checking Account', 'Savings Account')
-- account_type "account_type_enum" ,

CREATE TABLE account(
	acount_id serial PRIMARY KEY,
	balance NUMERIC DEFAULT(0.0),
	interest REAL NOT NULL,
	dividend NUMERIC DEFAULT(0.0)
);
ALTER TABLE account RENAME COLUMN acount_id TO account_id;

ALTER TABLE users ADD COLUMN savingsAccount integer REFERENCES account(account_id)
ALTER TABLE users ADD COLUMN checkingAccount integer REFERENCES account(account_id)

ALTER TABLE users DROP COLUMN savingsAccount 
ALTER TABLE users DROP COLUMN checkingAccount

ALTER TABLE account DROP COLUMN account_owner
ALTER TABLE account DROP COLUMN account_type

CREATE SEQUENCE my_sequence
	START 300000;

SELECT nextval('my_sequence')
ALTER SEQUENCE account_acount_id_seq RESTART WITH 300000

