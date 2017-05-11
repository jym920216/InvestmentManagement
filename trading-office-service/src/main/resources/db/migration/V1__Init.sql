DROP TABLE IF EXISTS instruction;
DROP TABLE IF EXISTS trader;
DROP TABLE IF EXISTS current_strategy;

--交易员
CREATE TABLE trader
(
	id BIGINT NOT NULL auto_increment,
	trader_id BIGINT NOT NULL,
	status VARCHAR(64), 
	PRIMARY KEY (id),
	UNIQUE KEY (trader_id) 
)CHARACTER SET = utf8;

CREATE TABLE instruction
(
	id BIGINT NOT NULL auto_increment,
	instruction_id BIGINT NOT NULL,
	trader_id BIGINT,
	allotted_time TIMESTAMP,
	PRIMARY KEY (id),
	UNIQUE KEY (instruction_id) 
)CHARACTER SET = utf8;

--当前策略
CREATE TABLE current_strategy
(
	id BIGINT NOT NULL auto_increment,
	type VARCHAR(64),
	current_strategy_name VARCHAR(64),
	update_time TIMESTAMP,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

INSERT INTO current_strategy(type, current_strategy_name)  
VALUES('ORG','BalancedAllocationStrategy');
