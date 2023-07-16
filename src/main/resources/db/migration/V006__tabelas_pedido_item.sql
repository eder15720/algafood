create table pedido (
    id bigint not null auto_increment, 
    bairro varchar(255), 
    cep varchar(255), 
    complemento varchar(255), 
    data_cancelamento datetime(6), 
    data_confirmacao datetime(6), 
    data_criacao datetime(6), 
    data_entrega datetime(6), 
    endereco_bairro varchar(255), 
    endereco_cep varchar(255), 
    endereco_complemento varchar(255), 
    endereco_logradouro varchar(255), 
    endereco_numero varchar(255), 
    logradouro varchar(255), 
    numero varchar(255), 
    status_pedido varchar(50), 
    subtotal decimal(19,2), 
    taxa_frete decimal(19,2), 
    valor_total decimal(19,2), 
    endereco_cidade_id bigint, 
    forma_pagamento_id bigint not null, 
    restaurante_id bigint not null, 
    usuario_id bigint not null, 
    primary key (id)
) engine=InnoDB;


alter table pedido add constraint pedido_endereco_cidade_fk foreign key (endereco_cidade_id) references cidade (id);
alter table pedido add constraint pedido_forma_pagamento_fk foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table pedido add constraint pedido_restaurante_fk foreign key (restaurante_id) references restaurante (id);
alter table pedido add constraint pedido_usuario_pedido_fk foreign key (usuario_id) references usuario (id);


create table item_pedido (
    pedido_id bigint not null, 
    forma_pagamento_id bigint not null
) engine=InnoDB;

alter table item_pedido add constraint itemped_forma_pagto_fk foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table item_pedido add constraint itemped_pedido_fk foreign key (pedido_id) references pedido (id);