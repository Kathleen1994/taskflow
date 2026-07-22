import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();

  function sair() {
    localStorage.removeItem("token");
    navigate("/");
  }

  return (
    <div className="dashboard">
      <h1>Dashboard</h1>

      <p>Login realizado com sucesso.</p>

      <button type="button" onClick={sair}>
        Sair
      </button>
    </div>
  );
}