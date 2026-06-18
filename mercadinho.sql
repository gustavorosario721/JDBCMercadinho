create database deposito_bebidas;
use deposito_bebidas;

create table produto(
  id int auto_increment,
  descricao varchar(100),
  preco decimal(6, 2),
	quantidade int not null,
  primary key (id)
);

create table funcionario(
	id int auto_increment,
  nome varchar(75) not null,
  salario decimal(7, 2),
  cargo varchar(30),
  primary key (id)
);

create table venda(
	cod_venda int auto_increment,
  id_func int,
  id_prod int,
  quantidade_vendida int,
  data_venda date,
  foreign key (id_func) references funcionario(id) on delete cascade,
  foreign key (id_prod) references produto(id) on delete cascade,
  primary key (cod_venda)
);
