import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../services/authService";

export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);

  const navigate = useNavigate();

  async function cadastrar(event) {
    event.preventDefault();

    setErro("");
    setCarregando(true);

    try {
      await register(username, password);

      alert("Usuário cadastrado com sucesso!");

      navigate("/");
    } catch (error) {
      console.error("Erro no cadastro:", error);

      setErro("Não foi possível cadastrar o usuário.");
    } finally {
      setCarregando(false);
    }
  }

  return (
    <div className="register">
      <h1>Criar conta</h1>

      <form onSubmit={cadastrar}>
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
          {carregando ? "Cadastrando..." : "Cadastrar"}
        </button>
      </form>

      <button
        type="button"
        onClick={() => navigate("/")}
      >
        Voltar para login
      </button>
    </div>
  );
}