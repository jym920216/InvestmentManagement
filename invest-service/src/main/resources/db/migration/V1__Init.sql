DROP TABLE IF EXISTS instruction_message;
DROP TABLE IF EXISTS instruction;

CREATE TABLE instruction
(
	id BIGINT NOT NULL auto_increment,
	invest_manager_id BIGINT,
	portfolio_id BIGINT,
	amount DOUBLE PRECISION,
	cost_price DOUBLE PRECISION,
	currency VARCHAR(255),
	invest_service VARCHAR(255),
	invest_direction VARCHAR(255),
	quantity DOUBLE PRECISION,
	security_id BIGINT,
	volume_type VARCHAR(255),	
	execution_status VARCHAR(255),
	create_date DATE,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE instruction_message
(
	id BIGINT NOT NULL auto_increment,
	instruction_id BIGINT,
	field_name VARCHAR(255),
	message_type VARCHAR(255),
	message_code VARCHAR(255),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

ALTER TABLE instruction_message ADD CONSTRAINT fk_instruction FOREIGN KEY (instruction_id) REFERENCES instruction (id);