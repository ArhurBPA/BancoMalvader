-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema banco_malvader
-- -----------------------------------------------------
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`TB_USUARIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_USUARIO` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_USUARIO` (
  `ID_USUARIO` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do usuário.',
  `NO_USUARIO` VARCHAR(100) NOT NULL COMMENT 'Nome completo do usuário.',
  `NR_CPF_USUARIO` CHAR(11) NOT NULL COMMENT 'CPF do usuário.',
  `DT_NASCIMENTO` DATE NOT NULL COMMENT 'Data de nascimento.',
  `NR_TELEFONE` VARCHAR(15) NOT NULL COMMENT 'Telefone de contato.',
  `SENHA` VARCHAR(50) NOT NULL COMMENT 'Senha para acesso ao sistema.',
  `TP_USUARIO` ENUM('FUNCIONARIO', 'CLIENTE') NOT NULL COMMENT 'Define se o usuário é funcionário ou cliente.',
  PRIMARY KEY (`ID_USUARIO`),
  UNIQUE INDEX `CPF_USUARIO_UNIQUE` (`NR_CPF_USUARIO` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = 'Armazena dados comuns para usuários do sistema, tanto funcionários quanto clientes.';


-- -----------------------------------------------------
-- Table `mydb`.`TB_FUNCIONARIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_FUNCIONARIO` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_FUNCIONARIO` (
  `ID_FUNCIONARIO` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do funcionário.',
  `CD_FUNCIONARIO` VARCHAR(20) NOT NULL COMMENT 'Código interno do funcionário.',
  `NO_CARGO` VARCHAR(50) NOT NULL COMMENT 'Cargo do funcionário.',
  `ID_USUARIO` INT NOT NULL COMMENT 'Relacionamento com a tabela usuario.',
  PRIMARY KEY (`ID_FUNCIONARIO`),
  UNIQUE INDEX `CD_FUNCIONARIO_UNIQUE` (`CD_FUNCIONARIO` ASC) VISIBLE,
  INDEX `fk_TB_FUNCIONARIO_TB_USUARIO1_idx` (`ID_USUARIO` ASC) VISIBLE,
  CONSTRAINT `fk_TB_FUNCIONARIO_TB_USUARIO1`
    FOREIGN KEY (`ID_USUARIO`)
    REFERENCES `mydb`.`TB_USUARIO` (`ID_USUARIO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Armazena dados específicos dos funcionários.';


-- -----------------------------------------------------
-- Table `mydb`.`TB_CLIENTE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_CLIENTE` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_CLIENTE` (
  `ID_CLIENTE` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do cliente.',
  `ID_USUARIO` INT NOT NULL COMMENT 'Relacionamento com a tabela usuario.',
  PRIMARY KEY (`ID_CLIENTE`),
  INDEX `fk_TB_CLIENTE_TB_USUARIO1_idx` (`ID_USUARIO` ASC) VISIBLE,
  CONSTRAINT `fk_TB_CLIENTE_TB_USUARIO1`
    FOREIGN KEY (`ID_USUARIO`)
    REFERENCES `mydb`.`TB_USUARIO` (`ID_USUARIO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Armazena dados específicos dos clientes.';


-- -----------------------------------------------------
-- Table `mydb`.`TB_ENDERECO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_ENDERECO` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_ENDERECO` (
  `ID_ENDERECO` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do endereço.',
  `NR_CEP` CHAR(10) NOT NULL COMMENT 'Código postal.',
  `NO_LOCAL` VARCHAR(100) NOT NULL COMMENT 'Logradouro.',
  `NR_CASA` INT NOT NULL COMMENT 'Número da residência.',
  `NO_BAIRRO` VARCHAR(50) NOT NULL COMMENT 'Bairro.',
  `NO_CIDADE` VARCHAR(50) NOT NULL COMMENT 'Cidade.',
  `SG_ESTADO` CHAR(2) NOT NULL COMMENT 'Estado (sigla).',
  `ID_USUARIO` INT NOT NULL COMMENT 'Relacionamento com a tabela usuario.',
  PRIMARY KEY (`ID_ENDERECO`, `ID_USUARIO`),
  INDEX `fk_TB_ENDERECO_TB_USUARIO1_idx` (`ID_USUARIO` ASC) VISIBLE,
  CONSTRAINT `fk_TB_ENDERECO_TB_USUARIO1`
    FOREIGN KEY (`ID_USUARIO`)
    REFERENCES `mydb`.`TB_USUARIO` (`ID_USUARIO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Armazena os endereços de usuários, vinculando clientes e funcionários aos respectivos endereços';


-- -----------------------------------------------------
-- Table `mydb`.`TB_CONTA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_CONTA` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_CONTA` (
  `ID_CONTA` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único da conta.',
  `NR_CONTA` VARCHAR(20) NOT NULL COMMENT 'Número da conta.',
  `NO_AGENCIA` VARCHAR(10) NOT NULL COMMENT 'Agência onde a conta foi criada.',
  `VL_SALDO` DECIMAL(15,2) NOT NULL COMMENT 'Saldo atual da conta.',
  `TP_CONTA` ENUM('POUPANCA', 'CORRENTE') NOT NULL COMMENT 'Tipo da conta bancária.',
  `ID_CLIENTE` INT NOT NULL COMMENT 'Relacionamento com a tabela cliente.',
  PRIMARY KEY (`ID_CONTA`),
  INDEX `fk_TB_CONTA_TB_CLIENTE1_idx` (`ID_CLIENTE` ASC) VISIBLE,
  CONSTRAINT `fk_TB_CONTA_TB_CLIENTE1`
    FOREIGN KEY (`ID_CLIENTE`)
    REFERENCES `mydb`.`TB_CLIENTE` (`ID_CLIENTE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Tabela base para contas bancárias, tanto para poupança quanto para conta corrente.';


-- -----------------------------------------------------
-- Table `mydb`.`TB_CONTA_CORRENTE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_CONTA_CORRENTE` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_CONTA_CORRENTE` (
  `ID_CONTA_CORRENTE` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador da conta corrente.',
  `VL_LIMITE` DECIMAL(15,2) NOT NULL COMMENT 'Limite de crédito da conta corrente',
  `DT_VENCIMENTO` DATE NOT NULL COMMENT 'Data de vencimento do limite.',
  `ID_CONTA` INT NOT NULL COMMENT 'Relacionamento com a tabela conta.',
  PRIMARY KEY (`ID_CONTA_CORRENTE`),
  INDEX `fk_TB_CONTA_CORRENTE_TB_CONTA1_idx` (`ID_CONTA` ASC) VISIBLE,
  CONSTRAINT `fk_TB_CONTA_CORRENTE_TB_CONTA1`
    FOREIGN KEY (`ID_CONTA`)
    REFERENCES `mydb`.`TB_CONTA` (`ID_CONTA`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Armazena dados específicos de contas correntes.';


-- -----------------------------------------------------
-- Table `mydb`.`TB_CONTA_POUPANCA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_CONTA_POUPANCA` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_CONTA_POUPANCA` (
  `ID_CONTA_POUPANCA` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador da conta poupança.',
  `VL_TAXA_RENDIMENTO` DECIMAL(5,2) NOT NULL COMMENT 'Taxa de rendimento da poupança.',
  `ID_CONTA` INT NOT NULL COMMENT 'Relacionamento com a tabela conta.',
  PRIMARY KEY (`ID_CONTA_POUPANCA`),
  INDEX `fk_TB_CONTA_POUPANCA_TB_CONTA1_idx` (`ID_CONTA` ASC) VISIBLE,
  CONSTRAINT `fk_TB_CONTA_POUPANCA_TB_CONTA1`
    FOREIGN KEY (`ID_CONTA`)
    REFERENCES `mydb`.`TB_CONTA` (`ID_CONTA`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Armazena dados específicos de contas poupança.';


-- -----------------------------------------------------
-- Table `mydb`.`TB_TRANSACAO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_TRANSACAO` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_TRANSACAO` (
  `ID_TRANSACAO` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único da transação.',
  `TP_TRANSACAO` ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA') NOT NULL COMMENT 'Tipo de transação realizada.',
  `VL_TRANSACAO` DECIMAL(15,2) NOT NULL COMMENT 'Valor da transação.',
  `DH_TRANSACAO` TIMESTAMP NOT NULL COMMENT 'Data e hora da transação.',
  `ID_CONTA` INT NOT NULL COMMENT 'Relacionamento com a conta em que a transação foi realizada.',
  PRIMARY KEY (`ID_TRANSACAO`),
  INDEX `fk_TB_TRANSACAO_TB_CONTA1_idx` (`ID_CONTA` ASC) VISIBLE,
  CONSTRAINT `fk_TB_TRANSACAO_TB_CONTA1`
    FOREIGN KEY (`ID_CONTA`)
    REFERENCES `mydb`.`TB_CONTA` (`ID_CONTA`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Armazena todas as transações realizadas nas contas, como saques, depósitos e extratos.';


-- -----------------------------------------------------
-- Table `mydb`.`TB_RELATORIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`TB_RELATORIO` ;

CREATE TABLE IF NOT EXISTS `mydb`.`TB_RELATORIO` (
  `ID_RELATORIO` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do relatório.',
  `TP_RELATORIO` VARCHAR(50) NOT NULL COMMENT 'Tipo de relatório gerado.',
  `DH_GERACAO` TIMESTAMP NOT NULL COMMENT 'Data e hora da geração do relatório.',
  `TX_CONTEUDO` TEXT NOT NULL COMMENT 'Conteúdo do relatório.',
  `ID_FUNCIONARIO` INT NOT NULL COMMENT 'Relacionamento com a tabela funcionario.',
  PRIMARY KEY (`ID_RELATORIO`),
  INDEX `fk_TB_RELATORIO_TB_FUNCIONARIO1_idx` (`ID_FUNCIONARIO` ASC) VISIBLE,
  CONSTRAINT `fk_TB_RELATORIO_TB_FUNCIONARIO1`
    FOREIGN KEY (`ID_FUNCIONARIO`)
    REFERENCES `mydb`.`TB_FUNCIONARIO` (`ID_FUNCIONARIO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Armazena relatórios gerados pelos funcionários.';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
