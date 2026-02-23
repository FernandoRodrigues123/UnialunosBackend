create table likes(
 id int not null AUTO_INCREMENT primary key,
 publicacao_id int,
 estudante_id int,
  foreign key(estudante_id) references estudantes(
    id),
     foreign key(publicacao_id) references publicacao(
       id)
)