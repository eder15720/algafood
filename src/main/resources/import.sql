insert into tab_cozinhas (nom_cozinha) values ('Iailandesa');
insert into tab_cozinhas (nom_cozinha) values ('Indiana');

insert into tab_estado(nome) values('São Paulo');

insert into tab_cidade(nome, estado_id)values("Caçapava", 1);

insert into tab_forma_pagamento(descricao) values ('Cartão de crédito');
insert into tab_forma_pagamento(descricao) values ('débito');
insert into tab_forma_pagamento(descricao) values ('dinheiro');

insert into tab_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('tudo nosso', 156, 2, utc_timestamp, utc_timestamp);
insert into tab_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('nada consta', 200, 1, utc_timestamp, utc_timestamp);
insert into tab_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('sete a um', 300, 1, utc_timestamp, utc_timestamp);
insert into tab_restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_completo, endereco_cep, endereco_numero, endereco_logradouro, data_cadastro, data_atualizacao) values ('Frans Leste', 200, 1, 1, 'parque sírio', '03562523', '362', 'Rua Jose maturatto', utc_timestamp, utc_timestamp);

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id)values(1,1);
insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id)values(1,2);