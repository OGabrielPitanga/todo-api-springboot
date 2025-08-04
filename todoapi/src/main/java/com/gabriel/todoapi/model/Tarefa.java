package com.gabriel.todoapi.model;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class Tarefa {
    private Long id;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    private boolean concluida;
}
