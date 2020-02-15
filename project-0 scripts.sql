
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


CREATE TYPE account_type_enum AS ENUM ('Checking', 'Savings')
ALTER TABLE account DROP COLUMN account_type
DROP TYPE account_type_enum
ALTER TABLE account ADD COLUMN account_type 

-- account_type "account_type_enum" ,

CREATE TABLE account(
	account_id serial PRIMARY KEY,
	balance NUMERIC DEFAULT(0.0),
	interest REAL NOT NULL,
	account_type account_type_enum NOT NULL
);
ALTER TABLE account RENAME COLUMN acount_id TO account_id;

ALTER TABLE account RENAME COLUMN account_id TO id;
ALTER TABLE users RENAME COLUMN user_id TO id;

ALTER TABLE users ADD COLUMN savingsAccount integer REFERENCES account(account_id)
ALTER TABLE users ADD COLUMN checkingAccount integer REFERENCES account(account_id)
ALTER TABLE users_account ADD COLUMN account_owner boolean

ALTER TABLE account ADD COLUMN account_type account_type_enum
ALTER TABLE users DROP COLUMN savingsAccount 
ALTER TABLE users DROP COLUMN checkingAccount

ALTER TABLE account DROP COLUMN account_owner
ALTER TABLE account DROP COLUMN account_type
ALTER TABLE account DROP COLUMN dividend

CREATE SEQUENCE my_sequence
	START 300000;

SELECT nextval('my_sequence')
ALTER SEQUENCE account_account_id_seq RESTART WITH 300000

CREATE TABLE users_account (
	id serial PRIMARY KEY,
	user_id integer REFERENCES users(id),
	account_id integer REFERENCES account(id)
);

insert into account (balance, interest, dividend, account_type) values (1362.13, 0.04, 1416.6152, 'Checking Account');


insert into users_account (user_id, account_id) values (119, 300049);
DROP TABLE account CASCADE

SELECT id, balance, interest, account_type FROM account
	WHERE id = 300000;

CREATE ROLE jdbc_bot LOGIN PASSWORD 'c4shm0ney!2';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO jdbc_bot;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO jdbc_bot;

DROP ROLE jdbc_bot
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public from jdbc_bot
REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public from jdbc_bot

SELECT account_id FROM users_account
	WHERE user_id = 120;

SELECT user_id, count(*) FROM users_account GROUP BY user_id;

SELECT 
	users_account.user_id,
	account.id, 
	account.account_type 
	FROM 
	users_account 
	LEFT JOIN  account ON users_account.account_id = account.id
	WHERE user_id = 82;

SELECT users_account.user_id, account.id, account.balance, account.interst, account.account_type FROM users_account 
	LEFT JOIN account ON users_account.account_id = account.id
	WHERE user_id = 82;

SELECT username FROM users 
	WHERE username = 'chris';
	
SELECT username, user_pass FROM users
	WHERE username = 'chris' AND user_pass = 'collins';

SELECT id, username, user_pass, first_name, last_name, pin, phonenumber FROM users
	WHERE username = 'chris';
	
update users
	set user_pass = 'thisistheone'
	where id = 120;
	
update account
	set balance = balance + 200
	where id = 300000;

SELECT users_account.user_id, account.id, account.balance FROM users_account 
	LEFT JOIN account ON users_account.account_id = account.id
	WHERE account_id = 300008;
	
insert into users_account (user_id, account_id) values (120, 300200);
insert into users_account (user_id, account_id) values (120, 300201);

SELECT id FROM users_account WHERE account_id = 300200 AND user_id = 83
DELETE FROM users_account WHERE id = 207									-- remove joint user

insert into users_account (user_id, account_id) values (83, 300200);		-- add joint user

DELETE FROM account WHERE id = 300202	--close account

SELECT id FROM users_account WHERE account_id = 300194

DELETE FROM users_account WHERE id = 208;

UPDATE users_account
SET user_id=122, account_id=300203, account_owner=true
WHERE id=206;

SELECT id FROM users_account WHERE user_id = 120 AND account_id = 300200 AND account_owner = true;

SELECT account_id FROM users_account WHERE user_id = 120;



