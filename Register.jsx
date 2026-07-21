import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../services/authService";

export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [mensagem, setMensagem] = useState("");
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);

  const navigate = useNavigate();

  async function cadastrar(event) {
    event.preventDefault();

    setMensagem("");
    setErro("");
    setCarregando(true);

    try {
      await register(username, password);

      setMensagem("Usuário cadastrado com sucesso!");

      setTimeout(() => {
        navigate("/");
      }, 1200);
    } catch (error) {
      console.error("Erro no cadastro:", error);

      const mensagemApi =
        error.response?.data?.error ||
        error.response?.data?.message ||
        "Não foi possível cadastrar.";

      setErro(mensagemApi);
    } finally {
      setCarregando(false);
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card">
        <h1>Criar conta</h1>

        <form onSubmit={cadastrar}>
          <label htmlFor="username">Usuário</label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
            required
          />

          <label htmlFor="password">Senha</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />

          {mensagem && <p>{mensagem}</p>}
          {erro && <p className="error-message">{erro}</p>}

          <button type="submit" disabled={carregando}>
            {carregando ? "Cadastrando..." : "Cadastrar"}
          </button>
        </form>

        <button type="button" onClick={() => navigate("/")}>
          Voltar para o login
        </button>
      </section>
    </main>
  );
}
