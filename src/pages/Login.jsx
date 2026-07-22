import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);

  const navigate = useNavigate();

  async function entrar(event) {
    event.preventDefault();

    setErro("");
    setCarregando(true);

    try {
      const resposta = await login(username, password);

      localStorage.setItem("token", resposta.token);

      alert("Login realizado");

      navigate("/dashboard");
    } catch (error) {
      console.error("Erro no login:", error);

      setErro("Usuário ou senha inválidos.");
    } finally {
      setCarregando(false);
    }
  }

  return (
    <div className="login">
      <h1>TaskFlow</h1>

      <form onSubmit={entrar}>
        <input
          type="text"
          placeholder="Usuário"
          value={username}
          onChange={(event) => setUsername(event.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
        />

        {erro && <p>{erro}</p>}

        <button type="submit" disabled={carregando}>
          {carregando ? "Entrando..." : "Entrar"}
        </button>
      </form>

      <button
        type="button"
        onClick={() => navigate("/register")}
      >
        Criar conta
      </button>
    </div>
  );
}