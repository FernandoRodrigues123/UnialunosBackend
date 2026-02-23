create table publicacao(
    id int not null AUTO_INCREMENT primary key,
    titulo varchar(100),
    corpo longtext,
    referencias VARCHAR(255),
    estudantes_id int,
    foreign key(estudantes_id) references estudantes(
   id)
)