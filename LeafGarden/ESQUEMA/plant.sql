CREATE TABLE IF NOT EXISTS `regiao` (
	`idRegiao`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`nome`	TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS `usuario` (
	`idUsuario`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`nome`	TEXT NOT NULL,
	`email`	TEXT NOT NULL,
	`senha`	TEXT NOT NULL,
	`idade`	INTEGER NOT NULL,
	`idRegiao`	INTEGER NOT NULL,
	FOREIGN KEY(`idRegiao`) REFERENCES `regiao`(`idRegiao`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `planta` (
	`idPlanta`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`nome`	TEXT NOT NULL,
	`descricao`	TEXT NOT NULL,
	`localAdequado`	TEXT NOT NULL,
	`temperatura`	REAL NOT NULL,
	`litroPorDia`	REAL NOT NULL
);
CREATE TABLE IF NOT EXISTS `sensorSolo` (
	`idSensor`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`nome`	TEXT NOT NULL,
	`canal`	TEXT NOT NULL,
	`timeStamp`	REAL NOT NULL,
	`temperatura`	REAL NOT NULL,
	`umidade`	REAL NOT NULL,
	`idPlanta`	INTEGER NOT NULL,
	FOREIGN KEY(`idPlanta`) REFERENCES `planta`(`idPlanta`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS `sensorLuz` (
	`idSensor`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`nome`	TEXT NOT NULL,
	`canal`	TEXT NOT NULL,
	`timeStamp`	REAL NOT NULL,
	`luminosidade`	REAL NOT NULL,
	`idPlanta`	INTEGER NOT NULL,
	FOREIGN KEY(`idPlanta`) REFERENCES `planta`(`idPlanta`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS `sensorAmbiente` (
	`idSensor`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`canal`	TEXT NOT NULL,
	`nome`	TEXT NOT NULL,
	`temperatura`	REAL NOT NULL,
	`umidade`	REAL NOT NULL,
	`timestamp`	REAL NOT NULL,
	`idPlanta`	INTEGER NOT NULL,
	FOREIGN KEY(`idPlanta`) REFERENCES `planta`(`idPlanta`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS `jardim` (
	`idJardim`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`idUsuario`	INTEGER NOT NULL,
	`idPlanta`	INTEGER NOT NULL,
	FOREIGN KEY(`idPlanta`) REFERENCES `planta`(`idPlanta`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`idUsuario`) REFERENCES `usuario`(`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS `acompanhamento` (
	`idAcompanhamento`	INTEGER NOT NULL PRIMARY KEY auto_increment,
	`idJardim`	INTEGER NOT NULL,
	`idSensorAmbiente`	INTEGER NOT NULL,
	`idSensorLuz`	INTEGER NOT NULL,
	`idSensorSolo`	INTEGER NOT NULL,
	FOREIGN KEY(`idSensorAmbiente`) REFERENCES `sensorAmbiente`(`idSensor`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`idJardim`) REFERENCES `jardim`(`idJardim`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`idSensorSolo`) REFERENCES `sensorSolo`(`idSensor`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`idSensorLuz`) REFERENCES `sensorLuz`(`idSensor`) ON DELETE CASCADE ON UPDATE CASCADE
);
