import { useEffect, useState } from "react";
import api from "../services/api";

export default function Dashboard() {
  const [tarefas, setTarefas] = useState([]);
  const [erro, setErro] = useState("");

  useEffect(() => {
    async function carregarTarefas() {
      try {
        const response = await api.get("/tasks");
        setTarefas(response.data);
      } catch {
        setErro("Não foi possível carregar as tarefas.");
      }
    }

    carregarTarefas();
  }, []);

  function sair() {
    localStorage.removeItem("token");
    window.location.href = "/";
  }

  return (
    <main className="dashboard-page">
      <header className="topbar">
        <div>
          <h1>TaskFlow</h1>
          <p>Gestão simples e eficiente das suas tarefas.</p>
        </div>

        <button className="logout-button" onClick={sair}>
          Sair
        </button>
      </header>

      <section className="dashboard-content">
        <div className="section-heading">
          <div>
            <span className="eyebrow">Painel</span>
            <h2>Minhas tarefas</h2>
          </div>

          <button className="primary-action">Nova tarefa</button>
        </div>

        {erro && <p className="error-message">{erro}</p>}

        <div className="task-grid">
          {tarefas.map((tarefa) => (
            <article className="task-card" key={tarefa.id}>
              <div className="task-card-header">
                <span
                  className={
                    tarefa.concluida
                      ? "status status-done"
                      : "status status-pending"
                  }
                >
                  {tarefa.concluida ? "Concluída" : "Pendente"}
                </span>

                <span className="task-date">
                  {tarefa.dataCriacao || "Sem data"}
                </span>
              </div>

              <h3>{tarefa.titulo}</h3>
              <p>{tarefa.descricao}</p>
            </article>
          ))}
        </div>
      </section>
    </main>
  );
}
