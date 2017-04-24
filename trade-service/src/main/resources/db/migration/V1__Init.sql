DROP TABLE IF EXISTS done;
DROP TABLE IF EXISTS entrust_message;
DROP TABLE IF EXISTS entrust;
DROP TABLE IF EXISTS virtual_done;

--委托
CREATE TABLE entrust
(
	id BIGINT NOT NULL auto_increment,
	instruction_id BIGINT NOT NULL,
	security_id BIGINT NOT NULL,
	trade_service VARCHAR(64),
	trade_type VARCHAR(64),
	brokerage_firm_id BIGINT,
	price_type VARCHAR(64), 
	currency VARCHAR(4),
	entrust_price DOUBLE PRECISION,
	entrust_quantity BIGINT,
	status VARCHAR(64),
	entrust_time TIMESTAMP,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--成交
CREATE TABLE done
(
	id BIGINT NOT NULL auto_increment,
	entrust_id BIGINT NOT NULL,
	done_price DOUBLE PRECISION,
	done_quantity BIGINT,
	status VARCHAR(64),
	done_time TIMESTAMP,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--虚拟成交
CREATE TABLE virtual_done
(
	id BIGINT NOT NULL auto_increment,
	instruction_id BIGINT,
	portfolio_id BIGINT,
	security_id BIGINT,
	invest_service VARCHAR(64),
	invest_type VARCHAR(64),
	currency VARCHAR(4),
	trade_service VARCHAR(64),
	external_capital_account_id BIGINT,
	applied_capital DOUBLE PRECISION,
	external_trade_account_id BIGINT,
	applied_position BIGINT,
	instruction_basket_id BIGINT,
	done_time TIMESTAMP,
	operator_sequence BIGINT,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE entrust_message
(
	id BIGINT NOT NULL auto_increment,
	entrust_id BIGINT,
	field_name VARCHAR(255),
	message_type VARCHAR(255),
	message_code VARCHAR(255),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

ALTER TABLE entrust_message ADD CONSTRAINT fk_entrust FOREIGN KEY (entrust_id) REFERENCES entrust (id);