const API_URL = "http://localhost:8080/tarefas";

async function carregarTarefas() {
    const resposta = await fetch(API_URL);
    const tarefas = await resposta.json();

    const lista = document.getElementById("listaTarefas");
    lista.innerHTML = "";

    tarefas.forEach(tarefa => {
        const item = document.createElement("li");

        item.className = tarefa.concluida ? "concluida" : "";

        item.innerHTML = `
            <span>${tarefa.descricao}</span>
            <div class="botoes">
                <button onclick="marcarConcluida(${tarefa.id})">✅</button>
                <button onclick="deletarTarefa(${tarefa.id})">🗑️</button>
            </div>
        `;

        lista.appendChild(item);
    });
}

async function adicionarTarefa() {
    const input = document.getElementById("descricao");
    const descricao = input.value.trim();

    if (descricao === "") return;

    const resposta = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ descricao })
    });

    if (resposta.ok) {
        input.value = "";
        carregarTarefas();
    }
}

async function deletarTarefa(id) {
    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });
    carregarTarefas();
}

async function marcarConcluida(id) {
    const resposta = await fetch(`${API_URL}/${id}`);
    const tarefa = await resposta.json();

    await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            descricao: tarefa.descricao,
            concluida: !tarefa.concluida
        })
    });

    carregarTarefas();
}

carregarTarefas();