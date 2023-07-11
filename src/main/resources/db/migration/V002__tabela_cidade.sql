create table cidade(
    id bigint not null auto_increment,
    nome_cidade varchar(80) not null,
    nome_estado varchar(80) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

insert into cidade (nome_cidade, nome_estado) values ('Uberlandia', 'Minas Gerais');
insert into cidade (nome_cidade, nome_estado) values ('Belo Horizonte', 'Minas Gerais');
insert into cidade (nome_cidade, nome_estado) values ('Sao Paulo', 'Sao Paulo');
insert into cidade (nome_cidade, nome_estado) values ('Valinhos', 'Sao Paulo');
insert into cidade (nome_cidade, nome_estado) values ('Rio de Janeiro', 'Rio de Janeiro');
insert into cidade (nome_cidade, nome_estado) values ('Paraty', 'Rio de Janeiro');