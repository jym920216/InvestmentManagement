DROP TABLE IF EXISTS instruction;
DROP TABLE IF EXISTS current_strategy;

CREATE TABLE instruction
(
	id BIGINT NOT NULL auto_increment,
	instruction_id BIGINT NOT NULL,
	trader_id BIGINT,
	PRIMARY KEY (id)
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
