package com.gabriel.todoapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity // informa que a classe é uma tabela no banco
@Table(name = "tarefas") // nome da tabela
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automatico
    private Long id;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    private boolean concluida;
}
