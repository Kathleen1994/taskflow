import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  async function entrar(event) {
    event.preventDefault();
    setErro("");

    try {
      const response = await api.post("/auth/login", null, {
        params: {
          username,
          password,
        },
      });

      localStorage.setItem("token", response.data.token);
      navigate("/dashboard");
    } catch {
      setErro("Utilizador ou palavra-passe inválidos.");
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card">
        <div className="brand-mark">TF</div>

        <h1>TaskFlow</h1>
        <p className="subtitle">Organize tarefas e mantenha o foco.</p>

        <form onSubmit={entrar}>
          <label htmlFor="username">Utilizador</label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
            required
          />

          <label htmlFor="password">Palavra-passe</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />

          {erro && <p className="error-message">{erro}</p>}

          <button type="submit">Entrar</button>
        </form>
      </section>
    </main>
  );
}
