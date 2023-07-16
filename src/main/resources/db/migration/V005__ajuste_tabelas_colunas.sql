alter table cozinha add column observacao varchar(255);

alter table produto add column produto_restaurante_id bigint;

alter table produto add constraint produto_restaurante_id_fk
foreign key (produto_restaurante_id) references restaurante (id);