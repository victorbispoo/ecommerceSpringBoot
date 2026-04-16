alter table categorias add ativo tinyint;
update categorias set ativo=1;
alter table categorias modify ativo tinyint not null;