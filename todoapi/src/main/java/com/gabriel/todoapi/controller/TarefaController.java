package com.gabriel.todoapi.controller;

import com.gabriel.todoapi.model.Tarefa;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Long proximoID = 1L;

    @PostMapping
    public Tarefa criarTarefa(@RequestBody @Valid Tarefa tarefa) {
        tarefa.setId(proximoID++);
        tarefa.setConcluida(false);
        listaTarefas.add(tarefa);
        return tarefa;
    }

    @GetMapping
    public List<Tarefa> listarTarefas() {
        return listaTarefas;
    }

    @GetMapping("/{id}")
    public Tarefa buscarPorId(@PathVariable Long id) {
        return listaTarefas.stream()
                .filter(tarefa -> tarefa.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
    }

    @PutMapping("/{id}")
    public Tarefa atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        Tarefa tarefaExistente = listaTarefas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa nao encontrada"));
        if (tarefaAtualizada.getDescricao() !=null && !tarefaAtualizada.getDescricao().isBlank()) {
            tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
        }

        tarefaExistente.setConcluida(tarefaAtualizada.isConcluida());

        return tarefaExistente;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarTarefa(@PathVariable Long id) {
        boolean removida = listaTarefas.removeIf(t -> t.getId().equals(id));
        if (!removida) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa nao encontrada");
        }
    }

}
